/**
 * FileName: NotificationDTO
 * Author:   郭经伟
 * Date:     2020/3/24 22:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import com.gjw.codecommunity.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String notifierName;
    private String outerTitle;//回复了什么问题什么评论
    private Integer outerId;
    private String typeName;
    private Integer type;
}
