/**
 * FileName: CommentController
 * Author:   郭经伟
 * Date:     2020/3/20 10:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import com.gjw.codecommunity.community.DTO.CommentCreateDTO;
import com.gjw.codecommunity.community.DTO.ResultDTO;
import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.model.Comment;
import com.gjw.codecommunity.community.model.User;
import com.gjw.codecommunity.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 实现回复功能：通过ajax异步提交json 再序列化为Object对象
     * @param commentCreateDTO
     * 1.判断有没有登录，没有登录就不能回复，抛出异常
     * 2.判断回复是否为空，为空就不能回复，抛出异常
     * 3.前台传过来的回复数据通过调用service层的insert方法向数据库插入回复数据
     * 4.向前台返回一个ResultDto
     * 可能需要要处理的异常
     * 1.没有登录=>异常
     * 2.回复为空=>异常
     * 3.异常都在service层抛出
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){

//        User user=new User();
//        user.setId(12);
        //先判断当前是否登录如果没有登录的话就不可以回复
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            //返回给前台一个message为没有登录不能回复的ResultDto
            return ResultDTO.errorof(CustomizeErrorCode.NOT_LOGIN);
        }
        //如果回复传输的数据为空或者内容为空的话抛出异常
        if (commentCreateDTO ==null|| "".equals(commentCreateDTO.getContent())){
            //返回给前台一个message为回复为空的ResultDTO
            return ResultDTO.errorof(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment=new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        commentService.insert(comment);
        return ResultDTO.okof();





    }
}
