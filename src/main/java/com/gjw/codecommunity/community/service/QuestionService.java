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

import com.gjw.codecommunity.community.DTO.PaginationDTO;
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

    //传入一个page 页数 和 一个 size页面大小
    public PaginationDTO list(Integer page, Integer size){


        //size*(page-1)=offset
        Integer offset=size*(page-1);
        //查询当前页的数据
        List<Question> list = questionMapper.list(offset,size);
        //定义QuestionDao list
        List<QuestionDto> questionDtoList=new ArrayList<>();
        //实例化PaginationDTO=QuestionDto+分页的一些信息
        PaginationDTO paginationDTO=new PaginationDTO();
        //遍历list 获取发布问题的User的信息
        for (Question question:list){

            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            questionDto.setUser(user);
            //通过spring的BeanUtils
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);

        }
        paginationDTO.setQuestionDtoList(questionDtoList);
        Integer totalCount=questionMapper.count();
        //调用DTO中设置分页信息的一些属性
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;

    }
}