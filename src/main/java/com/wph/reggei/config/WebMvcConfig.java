package com.wph.reggei.config;

import com.wph.reggei.commen.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     *  @author: WPH
     *  @Date: 2023/5/6 20:39
     *  TODO
     *  @Description: 设置静态资源映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        /**
        * @Author:  Adminis
        * @Date:    2023/5/7 14:48
        * @Param:   [converters]
        * @Return:  void
        * @Exception:
        * @Description: 扩展MVC框架的消息转换器
        *
        */
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置转换器对象，底层使用Jackson将java对象转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 把自己设置的消息转换器对象追加到mvc框架的转换器的集合中
        converters.add(0, messageConverter);
    }
}
