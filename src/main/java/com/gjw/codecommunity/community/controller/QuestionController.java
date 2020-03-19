/**
 * FileName: QuestionController
 * Author:   郭经伟
 * Date:     2020/3/17 20:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import com.gjw.codecommunity.community.DTO.QuestionDto;
import com.gjw.codecommunity.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//问题详情页面
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model) {
        //每次点击问题详情页面时，阅读量都会增加1
        questionService.incView(id);
        //通过指定id获取QuestionDTO
        QuestionDto question =questionService.findById(id);
        model.addAttribute("question",question);
        return "question";

    }
}
