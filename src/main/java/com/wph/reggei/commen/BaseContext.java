package com.wph.reggei.commen;

public class BaseContext {
/**
 *  @author: WPH
 *  @Date: 2023/5/7 15:36
 *  TODO
 *  @Description: 基于ThreadLocal封装工具类，用于保存和获取当前登录用户id
 */
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
