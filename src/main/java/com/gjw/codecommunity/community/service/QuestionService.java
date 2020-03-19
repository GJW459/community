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
import com.gjw.codecommunity.community.Exception.CustomizeErrorCode;
import com.gjw.codecommunity.community.Exception.CustomizeException;
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

    //根据用户查询数据
    public PaginationDTO list(Integer id, Integer page, Integer size) {

        //先获取offset 好传入sql语句中
        Integer offset=(page-1)*size;
        //获取当前页的数据
        List<Question> questions = questionMapper.listByUserId(id, offset, size);
        //传过去的问题数据还包含User数据
        List<QuestionDto> questionDtoList=new ArrayList<>();
        //paginationDTO这里面包含questionDtoList和分页的一些信息
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions){

            //通过Id获取用户信息
            User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            questionDto.setUser(user);
            //Spring的工具类
            BeanUtils.copyProperties(question,questionDto);
            questionDtoList.add(questionDto);
        }
        paginationDTO.setQuestionDtoList(questionDtoList);
        Integer totalCount=questionMapper.countByUserId(id);

        //调用DTO中设置分页信息的一些属性
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;

    }
    public QuestionDto findById(Integer id) {
        Question question=questionMapper.findById(id);
        //这里question可能没有找到Question 空指针异常
        if (question==null){
            //抛出异常 给异常处理类处理
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user=userMapper.findById(question.getCreator());
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createorupdate(Question question) {

        if (question.getId()==null){
            //id为空的话 那就是create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //update
            //在更新的时候也能出错
            question.setGmtModified(System.currentTimeMillis());
            Integer update = questionMapper.update(question);
            //update 为0的话 证明在其他操作的时候已经删除了问题 所有要抛出问题没有找到的异常
            if (update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
