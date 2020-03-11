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

import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){

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
        return "index";
    }
}
