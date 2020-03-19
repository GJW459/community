/**
 * FileName: CustomizeException
 * Author:   郭经伟
 * Date:     2020/3/19 12:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.Exception;

//自定义异常
public class CustomizeException extends RuntimeException {

    private String message;

    /**
     * 获取枚举类传入的错误消息
     * @param errorCode
     */
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message=errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
