package com.gjw.codecommunity.community.Exception;

//定义枚举

/**
 * 错误响应给前台一个错误状态码和错误信息
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找到问题不在了，要不要换个试试？"),
    NOT_LOGIN(2002,"你还没有登录，请先登录再进行操作"),
    COMMENT_IS_EMPTY(2003,"评论为空请先评论"),
    TARGET_PARAM_NOT_FOUND(2004,"没有选中就评论"),
    SYS_ERROR(2005,"系统错误"),
    TYPE_PARAM_WRONG(2006,"评论类型不存在"),
    COMMENT_NOT_FOUND(2007,"回复的评论不存在");
    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    //构造函数
    CustomizeErrorCode(Integer code,String message) {
        this.code=code;
        this.message = message;
    }
}
