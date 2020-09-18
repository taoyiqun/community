package com.toytrain.community.mapper;

import com.toytrain.community.model.Comment;
import com.toytrain.community.model.Question;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);

}
