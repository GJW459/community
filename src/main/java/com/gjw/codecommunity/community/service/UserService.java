/**
 * FileName: UserService
 * Author:   郭经伟
 * Date:     2020/3/17 21:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.service;

import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    //判断数据库中是否已经存在User了,存在就不插入 更新access_token
    public void createorupdate(User user){
        //先查询数据库中有没有这个User,根据User中AccountId查询
        User myUser=userMapper.findByAccountId(user.getAccountId());
        if (myUser!=null){
            //更新操作
            myUser.setGmtModified(System.currentTimeMillis());
            myUser.setAvatarUrl(user.getAvatarUrl());
            myUser.setName(user.getName());
            myUser.setToken(user.getToken());
            userMapper.update(myUser);
        }else {
            //insert操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }

    }
}
