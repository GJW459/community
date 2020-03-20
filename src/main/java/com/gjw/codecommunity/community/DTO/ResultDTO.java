/**
 * FileName: ResultDTO
 * Author:   郭经伟
 * Date:     2020/3/20 12:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * 返回给前台的数据
 */
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    //返回一个错误的对象给前端
    public static ResultDTO errorof(Integer code, String message) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setMessage(message);
        resultDTO.setCode(code);
        return resultDTO;
    }
    //错误信息：传入一个
    public static ResultDTO errorof(CustomizeErrorCode errorCode){

        return ResultDTO.errorof(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO okof() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorof(CustomizeException e) {

        return ResultDTO.errorof(e.getCode(),e.getMessage());
    }
}
