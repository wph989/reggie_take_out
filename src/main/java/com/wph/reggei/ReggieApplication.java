package com.wph.reggei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {
    /**
     *  @author: WPH
     *  @Date: 2023/5/6 20:29
     *  TODO
     *  @Description:
     */
    public static void main(String[] args) {
        SpringApplication.run((ReggieApplication.class));
        log.info("项目启动成功");
}
}
