/**
 * FileName: QuestionDto
 * Author:   郭经伟
 * Date:     2020/3/14 14:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import com.gjw.codecommunity.community.model.User;
import lombok.Data;

//数据传输对象
// 在index页面我们需要展示list用户的发布的问题
// 问题：question+User.avatarUrl
@Data
public class QuestionDto {

    private Integer id;

    private String title;

    private String description;

    private String tag;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
