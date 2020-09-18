package com.toytrain.community.controller;

import com.toytrain.community.dto.CommentCreateDTO;
import com.toytrain.community.dto.CommentDTO;
import com.toytrain.community.dto.QuestionDTO;
import com.toytrain.community.dto.ResultDTO;
import com.toytrain.community.exception.CustomizeErrorCode;
import com.toytrain.community.model.Comment;
import com.toytrain.community.model.Question;
import com.toytrain.community.model.User;
import com.toytrain.community.service.CommentService;
import com.toytrain.community.service.QuestionService;
import enums.CommentTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping(value = "/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest httpServletRequest){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO ==null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0);
        commentService.insert(comment);

        return ResultDTO.okOf();
    }

    @GetMapping(value = "/comment/{id}")
    public String comments(@PathVariable(name = "id") long id, Model model){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.TYPE_COMMENT);
        CommentDTO parComment = commentService.getById(id);
        model.addAttribute("replies",commentDTOS);
        model.addAttribute("comment",parComment);
        return "question::reply-fragment";
    }
}
