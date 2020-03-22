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

import com.gjw.codecommunity.community.DTO.CommentDTO;
import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
import com.gjw.codecommunity.community.enums.CommentTypeEnum;
import com.gjw.codecommunity.community.mapper.CommentMapper;
import com.gjw.codecommunity.community.mapper.QuestionMapper;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.Comment;
import com.gjw.codecommunity.community.model.Question;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

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
            Comment dbComment=commentMapper.selectById(comment.getId());
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

    //获取
    public List<CommentDTO> findByParentId(Integer parentId) {

        //查询
        List<Comment> comments=commentMapper.findByParentId(parentId);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        List<CommentDTO> commentDTOS=new ArrayList<>();
        for (Comment comment : comments) {
            User user=userMapper.findById(comment.getCommentator());
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;


    }
}
