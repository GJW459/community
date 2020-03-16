/**
 * FileName: IndexController
 * Author:   郭经伟
 * Date:     2020/3/10 21:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import com.gjw.codecommunity.community.DTO.PaginationDTO;
import com.gjw.codecommunity.community.DTO.QuestionDto;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.User;
import com.gjw.codecommunity.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    //注入Service
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size)
    {

        Cookie[] cookies = request.getCookies();
        //获取所有的Cookie
        if (cookies!=null){
            for (Cookie cookie : cookies) {

                //key 为token的Cookie
                if (cookie.getName().equals("token")){

                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if (user!=null){
                        //向服务端传session对象
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        PaginationDTO paginationDTO = questionService.list(page,size);
        System.out.println(paginationDTO.getQuestionDtoList());
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
