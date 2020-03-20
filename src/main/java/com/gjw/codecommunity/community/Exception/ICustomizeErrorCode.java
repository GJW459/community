/**
 * FileName: ICustomizeErrorCode
 * Author:   郭经伟
 * Date:     2020/3/19 12:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.Exception;

//接口
//两个方法：获取消息和获取状态码
public interface ICustomizeErrorCode {

    String getMessage();
    Integer getCode();
}
