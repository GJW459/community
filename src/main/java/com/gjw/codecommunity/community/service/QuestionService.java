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
import com.gjw.codecommunity.community.utils.ListUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    //需要 UserMapper and QuestionMapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    //传入一个page 页数 和 一个 size页面大小
    public PaginationDTO list(String search, Integer page, Integer size) {


        //size*(page-1)=offset
        Integer offset = size * (page - 1);
        //查询当前页的数据
        List<Question> list;
        if (StringUtils.isBlank(search)){
            list=questionMapper.list(offset,size);
        }else {
            list=questionMapper.selectBySearch(search,offset,size);
        }
        PaginationDTO paginationDTO = ListUtil.setQuestionDtoList(userMapper, list);
        Integer totalCount = questionMapper.count();
        //调用DTO中设置分页信息的一些属性
        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;

    }

    //根据用户查询数据
    public PaginationDTO list(Integer id, Integer page, Integer size) {

        //先获取offset 好传入sql语句中
        Integer offset = (page - 1) * size;
        //获取当前页的数据
        List<Question> questions = questionMapper.listByUserId(id, offset, size);
        PaginationDTO paginationDTO = ListUtil.setQuestionDtoList(userMapper, questions);
        Integer totalCount = questionMapper.countByUserId(id);

        //调用DTO中设置分页信息的一些属性
        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;

    }

    public QuestionDto findById(Integer id) {
        Question question = questionMapper.findById(id);
        //这里question可能没有找到Question 空指针异常
        if (question == null) {
            //抛出异常 给异常处理类处理
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createorupdate(Question question) {

        if (question.getId() == null) {
            //id为空的话 那就是create
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            //update
            //在更新的时候也能出错
            question.setGmtModified(System.currentTimeMillis());
            Integer update = questionMapper.update(question);
            //update 为0的话 证明在其他操作的时候已经删除了问题 所有要抛出问题没有找到的异常
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapper.incView(question);
    }

    /**
     * 获取相关问题
     *
     * @param question 当前question页面的QuestionDTO
     * @return
     */
    public List<QuestionDto> selectRelated(QuestionDto question) {

        if (question.getTag() == null || "".equals(question.getTag())) {
            return new ArrayList<>();
        }
        //先分离出tag来，再通过正则表达式查询相关问题
        String[] tags = question.getTag().split(",");
        //拼接字符串
        //我们需要用正则表达式查询，select * from question where tag regexp 'SpringBoot|Spring|Java' and id!=34
        String regexp = StringUtils.join(tags, "|");
        List<Question> questions = questionMapper.selectRelated(regexp, question.getId());
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question queryQuestion : questions) {
            User user = userMapper.findById(queryQuestion.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(queryQuestion, questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }

    //热门标签
    public Set<String> getHotTags(){
        List<String> tags = questionMapper.getTags();
        if (tags==null&&tags.size()==0){
            return null;
        }
        String tag = StringUtils.join(tags, ",");
        String[] hottags = tag.split(",");
        Set<String> tagSet=new HashSet<>();
        for (String hottag : hottags) {
            tagSet.add(hottag);
        }
        return tagSet;

    }
}
