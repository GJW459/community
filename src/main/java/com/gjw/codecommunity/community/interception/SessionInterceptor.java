/**
 * FileName: SessionIntercepter
 * Author:   郭经伟
 * Date:     2020/3/16 21:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.interception;

import com.gjw.codecommunity.community.enums.NotificationStatusEnum;
import com.gjw.codecommunity.community.enums.NotificationTypeEnum;
import com.gjw.codecommunity.community.mapper.NotificationMapper;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义拦截器 并且注入spring ioc容器
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    // 拦截所有请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        //获取所有的Cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                //key 为token的Cookie
                if (cookie.getName().equals("token")) {

                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //向服务端传session对象
                        request.getSession().setAttribute("user", user);
                        Integer unreadCount = notificationMapper.countByUserId(user.getId(), NotificationStatusEnum.UNREAD.getStatus());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
