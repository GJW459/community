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


import com.gjw.codecommunity.community.DTO.QuestionDto;
import com.gjw.codecommunity.community.cache.TagCache;
import com.gjw.codecommunity.community.model.Question;
import com.gjw.codecommunity.community.model.User;
import com.gjw.codecommunity.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    //通过点击编辑跳转到publish.html 并且显示问题的所有信息
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model){
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("title",questionDto.getTitle());
        model.addAttribute("description",questionDto.getDescription());
        model.addAttribute("tag",questionDto.getTag());
        model.addAttribute("id",questionDto.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model) {

        //如果有些没有填就把它从request域中返回到publish.html
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || "".equals(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || "".equals(description)) {
            model.addAttribute("error", "问题不能为空");
            return "publish";
        }
        if (tag == null || "".equals(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户没有登录");
            return "publish";
        }
        //发布疑问的时候存储在数据库中或者更新
        Question question = new Question();
        question.setId(id);
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        //判断是create还是update
        questionService.createorupdate(question);
        //重新定向到首页
        return "redirect:/";

    }
}
