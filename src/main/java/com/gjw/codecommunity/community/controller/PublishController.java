/**
 * FileName: PublishController
 * Author:   郭经伟
 * Date:     2020/3/12 17:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;


import com.gjw.codecommunity.community.mapper.QuestionMapper;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.Question;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model){

        //如果有些没有填就把它从request域中返回到publish.html
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||"".equals(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||"".equals(description)){
            model.addAttribute("error","问题不能为空");
            return "publish";
        }
        if (tag==null||"".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Cookie[] cookies = request.getCookies();
        User user=null;
        for (Cookie cookie : cookies) {

            //key 为token的Cookie
            if (cookie.getName().equals("token")){

                String token=cookie.getValue();
                user=userMapper.findByToken(token);
                if (user!=null){
                    //向服务端传session对象
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        //
        if (user==null){
            model.addAttribute("error","用户没有登录");
            return "publish";
        }
        //发布疑问的时候存储在数据库中
        Question question=new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        //重新定向到首页
        return "redirect:/";

    }
}
