/**
 * FileName: MyWebMvcConfiger
 * Author:   郭经伟
 * Date:     2020/3/26 12:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//扩展spring mvc组件
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //获取当前操作系统
        String osName=System.getProperty("os.name");
        //如果是windows
        if (osName.toLowerCase().startsWith("win")){
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:D:/community/upload/");
        }else {
            //linux或者mac
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:/resources/community/upload/");
        }
    }
}
