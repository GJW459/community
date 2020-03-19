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


import com.gjw.codecommunity.community.Exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//对所有异常进行拦截
@ControllerAdvice
public class CustomizeExceptionHandler {

    //异常处理
    @ExceptionHandler(CustomizeException.class)
    public ModelAndView handlerException(Model model,
                                        Throwable e){

        //判断异常类型
        if (e instanceof CustomizeException){

            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务器冒烟了，要不然你稍后再试试");
        }
        return new ModelAndView("error");
    }


}
