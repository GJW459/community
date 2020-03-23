/**
 * FileName: CommentDTO
 * Author:   郭经伟
 * Date:     2020/3/22 17:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import com.gjw.codecommunity.community.model.User;
import lombok.Data;

/**
 * 向前台传的回复
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
