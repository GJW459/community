/**
 * FileName: FileController
 * Author:   郭经伟
 * Date:     2020/3/25 20:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.gjw.codecommunity.community.controller;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class FileController {

    @ResponseBody
    @PostMapping("/upload")
    public JSONObject upLoad(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file
    ) {
        JSONObject object = new JSONObject();
        try {
            //先创建文件夹
            //先获取在当前系统下的文件夹路径
            String osName = System.getProperty("os.name");
            String realPath;
            if (osName.toLowerCase().startsWith("win")) {
                realPath = "D:/community/upload/";
            } else {
                realPath = "/resources/community/upload/";
            }
            File myFlie = new File(realPath);
            if (!myFlie.exists()) {
                myFlie.mkdirs();
            }
            //获取上传文件的文件名
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileName = uuid + "_" + fileName;
            file.transferTo(new File(realPath, fileName));
            object.put("success", 1);
            object.put("message", "上传成功");
            object.put("url", "/upload/" + fileName);
        } catch (Exception e) {

            object.put("success", 0);
            object.put("message", "上传失败");
        }

        return object;

    }


}
