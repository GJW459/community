/**
 * FileName: ProfileController
 * Author:   郭经伟
 * Date:     2020/3/16 20:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import com.gjw.codecommunity.community.DTO.PaginationDTO;
import com.gjw.codecommunity.community.model.User;
import com.gjw.codecommunity.community.service.NotificationService;
import com.gjw.codecommunity.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/***
 * 个人问题的controller
 */
@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request) {

        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {

            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            //需要查询指定用户的所有问题 通过用户id查询 因为 User表和Question表相互关联
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("paginationDTO",paginationDTO);
        }else if ("replies".equals(action)){

            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回答");
            PaginationDTO paginationDTO=notificationService.list(user.getId(),page,size);
            Integer unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("paginationDTO",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
        }

        return "profile";
    }
}
