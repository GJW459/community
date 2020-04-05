/**
 * FileName: Notification
 * Author:   郭经伟
 * Date:     2020/3/24 21:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Notification implements Serializable {

    private Integer id;
    //评论人
    private Integer notifier;
    //接收通知的人
    private Integer receiver;

    private Integer outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
