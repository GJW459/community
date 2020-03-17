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

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
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
    void update(Question question);
}
