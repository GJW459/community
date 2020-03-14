/**
 * FileName: QuestionService
 * Author:   郭经伟
 * Date:     2020/3/14 14:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.service;

import com.gjw.codecommunity.community.DTO.QuestionDto;
import com.gjw.codecommunity.community.mapper.QuestionMapper;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.Question;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    //需要 UserMapper and QuestionMapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDto> list(){

        List<Question> list = questionMapper.list();
        //定义QuestionDao list
        List<QuestionDto> questionDtoList=new ArrayList<>();
        //遍历list 获取发布问题的User的信息
        for (Question question:list){

            User user = userMapper.findById(question.getId());
            QuestionDto questionDto=new QuestionDto();
            questionDto.setUser(user);
            //通过spring的BeanUtils
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);

        }

        return questionDtoList;

    }
}
