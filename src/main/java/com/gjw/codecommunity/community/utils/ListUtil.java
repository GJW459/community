/**
 * FileName: ListUtil
 * Author:   郭经伟
 * Date:     2020/4/5 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.utils;

import com.gjw.codecommunity.community.DTO.PaginationDTO;
import com.gjw.codecommunity.community.DTO.QuestionDto;
import com.gjw.codecommunity.community.mapper.UserMapper;
import com.gjw.codecommunity.community.model.Question;
import com.gjw.codecommunity.community.model.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class ListUtil {


    public static PaginationDTO setQuestionDtoList(UserMapper userMapper,List<Question> list){
        //定义QuestionDao list
        List<QuestionDto> questionDtoList = new ArrayList<>();
        //实例化PaginationDTO=QuestionDto+分页的一些信息
        PaginationDTO paginationDTO = new PaginationDTO();
        //遍历list 获取发布问题的User的信息
        for (Question question : list) {

            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUser(user);
            //通过spring的BeanUtils
            BeanUtils.copyProperties(question, questionDto);
            questionDtoList.add(questionDto);

        }
        paginationDTO.setData(questionDtoList);
        return paginationDTO;
    }
}
