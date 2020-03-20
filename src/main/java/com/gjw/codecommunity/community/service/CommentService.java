/**
 * FileName: CommentService
 * Author:   郭经伟
 * Date:     2020/3/20 13:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.service;

import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
import com.gjw.codecommunity.community.enums.CommentTypeEnum;
import com.gjw.codecommunity.community.mapper.CommentMapper;
import com.gjw.codecommunity.community.mapper.QuestionMapper;
import com.gjw.codecommunity.community.model.Comment;
import com.gjw.codecommunity.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment){
        //如果parentId为空的话
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment=commentMapper.selectByParentId(comment.getParentId());
            if (dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question=questionMapper.findById(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incComment(question);

        }

    }
}
