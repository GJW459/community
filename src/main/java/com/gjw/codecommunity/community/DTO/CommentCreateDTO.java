/**
 * FileName: CommentCreateDTO
 * Author:   郭经伟
 * Date:     2020/3/20 10:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import lombok.Data;

@Data
public class CommentCreateDTO {

    private Integer parentId;
    private String content;
    private Integer type;

}
