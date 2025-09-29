package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.result.Result;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传controller
 *
 * @author GH
 * @date 2022-04-17 12:52
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/image")
    public Result upload(@RequestParam("file") MultipartFile
                                 file) throws IOException {

        // 1. 获取要上传文件的文件名
        String fileName = file.getOriginalFilename();
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        String path = applicationHome.getDir().getParentFile().
                getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\upload";


        File realPath = new File(path);
        //如果目录不存在则新建
        if (!realPath.exists()) {
            realPath.mkdir();
        }
        //生成uuid随机字符串

        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        //拼接文件名
        fileName = uuid + fileName;
        String result = "";
        try {
            file.transferTo(new File(realPath + "\\" + fileName));
            result = "/upload/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok().data("filePath", result);
    }
}