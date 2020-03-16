/**
 * FileName: PaginationDTO
 * Author:   郭经伟
 * Date:     2020/3/14 21:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//导航到首页 需要的属性
@Data
public class PaginationDTO {

    //QuestionDto : Question and User
    private List<QuestionDto> questionDtoList;

    //是否展示前一页
    private boolean showPrevious;
    //是否展示第一页
    private boolean showFirstPage;
    //是否展示下一页
    private boolean showNext;
    //是否展示最后一页
    private boolean showEndPage;
    //当前页
    private Integer currentPage;
    //页数数组：1,2,3,4,5
    private List<Integer> pages=new ArrayList<>();
    //总页数
    private Integer totalPage;

    public void setPagination(Integer totalCount,Integer page,Integer size){

        this.currentPage=page;

        //计算总共页数
        Integer totalPage;
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        this.totalPage=totalPage;

        if (page<0){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }

        //向列表插入页号
        //推导的逻辑
        pages.add(page);
        for (int i=1;i<=3;i++){
            //如果是第二页后
            if (page-i>0){
                //插在头部
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //第一个判断什么时候有上一页
        if (page==1){
            showPrevious=false;//不显示
        }else {
            showPrevious=true;
        }
        //是否展示上一页 没有那个图标
        if (totalPage==page){
            showNext=false;
        }else {
            showNext=true;
        }

        //是否展示第一页
        if (pages.contains(1)){

            //当列表包含1时不展示
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){

            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }
}
