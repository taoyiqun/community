package com.toytrain.community.service;

import com.toytrain.community.dto.CommentDTO;
import com.toytrain.community.dto.QuestionDTO;
import com.toytrain.community.exception.CustomizeErrorCode;
import com.toytrain.community.exception.CustomizeException;
import com.toytrain.community.mapper.*;
import com.toytrain.community.model.*;
import enums.CommentTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || CommentTypeEnum.isNotExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        Question question = null;
        if(comment.getType().equals(CommentTypeEnum.TYPE_COMMENT.getType())){
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);
            question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
        }else {
            question = questionMapper.selectByPrimaryKey(comment.getParentId());
        }
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        commentMapper.insert(comment);
        question.setGmtModified(System.currentTimeMillis());
        questionMapper.updateByPrimaryKeySelective(question);
        question.setCommentCount(1);
        questionExtMapper.incCommentCount(question);

    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toList());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(commentators);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
    public CommentDTO getById(Long id) {
        Comment comment= commentMapper.selectByPrimaryKey(id);
        if(comment == null){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
        }
        CommentDTO commentDTO = new CommentDTO();
        User user = userMapper.selectByPrimaryKey(comment.getCommentator());
        commentDTO.setUser(user);
        BeanUtils.copyProperties(comment,commentDTO);
        return commentDTO;
    }

}
