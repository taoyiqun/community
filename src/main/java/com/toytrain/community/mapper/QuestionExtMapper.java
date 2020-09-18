package com.toytrain.community.mapper;

import com.toytrain.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incViewCount(Question question);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}
