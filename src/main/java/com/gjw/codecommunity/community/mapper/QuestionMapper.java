/**
 * FileName: QuestionMapper
 * Author:   郭经伟
 * Date:     2020/3/12 18:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.mapper;

import com.gjw.codecommunity.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@CacheNamespace(flushInterval = 60000,size = 512)
public interface QuestionMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question  order by gmt_create desc limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{id} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator=#{id}")
    Integer countByUserId(@Param("id") Integer id);

    @Select("select * from question where id=#{id}")
    Question findById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    Integer update(Question question);

    @Update("update question set view_count=view_count+#{viewCount} where id=#{id}")
    void incView(Question question);

    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void incComment(Question question);

    @Select("select * from question where tag regexp #{regexp} and id!=#{id}")
    List<Question> selectRelated(@Param("regexp") String regexp,@Param("id") Integer id);
    @Select("select * from question where title COLLATE utf8_general_ci like concat('%',#{search},'%') order by gmt_create desc limit #{offset},#{size}")
    List<Question> selectBySearch(@Param("search") String search,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select tag from question")
    List<String> getTags();
}
