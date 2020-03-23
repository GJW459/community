/**
 * FileName: TagDTO
 * Author:   郭经伟
 * Date:     2020/3/23 20:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {

    private String categoryName;

    private List<String> tags;
}
