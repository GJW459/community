/**
 * FileName: CustomizeErrorController
 * Author:   郭经伟
 * Date:     2020/3/19 12:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*这个类主要是处理 HandlerException处理不了的异常 我们可以新建error文件夹下面新建4xx.html和5xx.html 来处理异常
在这里我希望所有异常处理都渲染到同一个html页面上，所有不用上面的方法，通过实现ErrorController，使SpringBoot自定义的
类失效 ,自定义我们自己的*/
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("message","你这个请求错了，试试换换!!!");
        }
        if (status.is5xxServerError()){
            model.addAttribute("message","服务器冒烟了，要不然你稍后再试试!!!");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
