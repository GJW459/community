/**
 * FileName: Question
 * Author:   郭经伟
 * Date:     2020/3/12 18:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Question implements Serializable {

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

}
