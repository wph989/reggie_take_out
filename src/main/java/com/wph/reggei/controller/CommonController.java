package com.wph.reggei.controller;

import com.wph.reggei.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
/**
 *  @author: WPH
 *  @Date: 2023/5/7 20:02
 *  TODO
 *  @Description:
 */
    @Value("${reggie.path}")
    private String bashPath;

    // 上传文件
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        // file 是一个临时文件，需要转存到指定位置
        // 原始文件名
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 使用UUid重新生产文件名
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个文件夹对象
        File dir = new File(bashPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        try{
            file.transferTo(new File(bashPath, fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.success(fileName);
    }
    // 下载文件
    @GetMapping("/download")
    public R<String> download(String name, HttpServletResponse response){
        try{
            // 输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(bashPath + name));
            // 输出流，通过输出流将文件写回浏览器
            ServletOutputStream fileOutputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
             int len = 0;
             byte[] bytes = new byte[1024];
             while ((len = fileInputStream.read(bytes)) != -1){
                 fileOutputStream.write(bytes,0,len);
                 fileOutputStream.flush();
             }
             fileOutputStream.close();
             fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
