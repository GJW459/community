/**
 * FileName: CustomizeExceptionHandler
 * Author:   郭经伟
 * Date:     2020/3/19 12:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.advice;


import com.alibaba.fastjson.JSON;
import com.gjw.codecommunity.community.DTO.ResultDTO;
import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//对所有异常进行拦截
@ControllerAdvice
public class CustomizeExceptionHandler {

    //异常处理
    @ExceptionHandler(CustomizeException.class)
    public Object handlerException(Model model,
                                   Throwable e,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        String contentType = request.getContentType();
        //如果请求头是application/json
        if ("application/json".equals(contentType)) {
            //返回JSON
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorof((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorof(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;

        } else {
            //跳转到error页面
            //如果

            //判断异常类型
            if (e instanceof CustomizeException) {

                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");

        }
    }


}
