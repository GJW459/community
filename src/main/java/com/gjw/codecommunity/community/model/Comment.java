/**
 * FileName: Comment
 * Author:   郭经伟
 * Date:     2020/3/20 10:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.model;

import lombok.Data;

@Data
public class Comment {

    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
}
