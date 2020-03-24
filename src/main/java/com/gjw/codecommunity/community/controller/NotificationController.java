/**
 * FileName: NotificationController
 * Author:   郭经伟
 * Date:     2020/3/25 0:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import com.gjw.codecommunity.community.DTO.NotificationDTO;
import com.gjw.codecommunity.community.enums.NotificationTypeEnum;
import com.gjw.codecommunity.community.model.User;
import com.gjw.codecommunity.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id")Integer id,
                          HttpServletRequest request){

        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||
        NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
