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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController {

    //注入Service
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "search", required = false,defaultValue = "") String search) {


        PaginationDTO paginationDTO = questionService.list(search, page, size);
        Set<String> hotTags = questionService.getHotTags();
        model.addAttribute("paginationDTO", paginationDTO);
        model.addAttribute("search",search);
        model.addAttribute("hotTags",hotTags);
        return "index";
    }
}
