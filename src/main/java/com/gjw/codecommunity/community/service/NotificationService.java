/**
 * FileName: NotificationService
 * Author:   郭经伟
 * Date:     2020/3/24 22:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.service;

import com.gjw.codecommunity.community.DTO.NotificationDTO;
import com.gjw.codecommunity.community.DTO.PaginationDTO;
import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
import com.gjw.codecommunity.community.enums.NotificationStatusEnum;
import com.gjw.codecommunity.community.enums.NotificationTypeEnum;
import com.gjw.codecommunity.community.mapper.NotificationMapper;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.Notification;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        //先获取offset 好传入sql语句中
        Integer offset = (page - 1) * size;
        //获取当前页的数据
        List<Notification> notifications = notificationMapper.listByUserId(id, offset, size);
        if (notifications.size()==0){{
            return new PaginationDTO();
        }}
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        //paginationDTO这里面包含questionDtoList和分页的一些信息
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        for (Notification notification : notifications) {

            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        Integer totalCount = notificationMapper.countByUserId2(id);

        //调用DTO中设置分页信息的一些属性
        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;
    }

    public Integer unreadCount(Integer id) {

        return notificationMapper.countByUserId(id, NotificationStatusEnum.UNREAD.getStatus());
    }

    public NotificationDTO read(Integer id, User user) {

        Notification notification=notificationMapper.findById(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //点击链接后设置已读
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        //更新操作
        notificationMapper.update(notification);
        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;

    }
}
