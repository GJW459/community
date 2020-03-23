/**
 * FileName: CommentMapper
 * Author:   郭经伟
 * Date:     2020/3/20 12:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.mapper;

import com.gjw.codecommunity.community.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);


    @Select("select * from comment where id=#{id}")
    Comment selectById(@Param("id") Integer id);

    @Select("select * from comment where parent_id=#{parenId} and type=#{type} ORDER BY gmt_create desc")
    List<Comment> findByParentId(@Param("parenId") Integer parenId,@Param("type") Integer type);

    @Update("update comment set comment_count=#{commentCount}+1 where id=#{id} ")
    void incComment(Comment comment);
}
