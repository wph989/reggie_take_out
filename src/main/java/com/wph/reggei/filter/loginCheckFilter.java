package com.wph.reggei.filter;

import com.alibaba.fastjson.JSON;
import com.wph.reggei.commen.BaseContext;
import com.wph.reggei.commen.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  @author: WPH
 *  @Date: 2023/5/7 9:09
 *  TODO
 *  @Description: 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class loginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 1.获取本次请求的uri
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);
        // 定义不需要拦截的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        // 2.判断本次请求的路径是否需要处理
        boolean check = check(urls, requestURI);
        // 3.如果不需要处理，则直接放行
        if(check){
            log.info("本次请求：{} 不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        // 4.如果需要处理，判断登录状态
        Long userId = (Long) request.getSession().getAttribute("employee");
        if(userId != null){
            log.info("用户已登录，用户id为：{}",userId);
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        };
        // 5.如果没有登录，则返回登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");
        return;

    }

    public boolean check(String[] urls, String requestRUI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestRUI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
