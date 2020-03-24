/**
 * FileName: NotificationMapper
 * Author:   郭经伟
 * Date:     2020/3/24 21:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.mapper;

import com.gjw.codecommunity.community.model.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outer_id,type,gmt_create,status,notifier_name,outer_title) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("select * from notification where receiver=#{receiver} limit #{offset},#{size}")
    List<Notification> listByUserId(@Param("receiver") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from notification where receiver=#{id} and status=#{status}")
    Integer countByUserId(@Param("id") Integer id,@Param("status") Integer status);
    @Select("select count(1) from notification where receiver=#{id}")
    Integer countByUserId2(Integer id);

    @Select("select * from notification where id=#{id}")
    Notification findById(Integer id);
    @Update("update notification set status=#{status} where id=#{id}")
    void update(Notification notification);

}
