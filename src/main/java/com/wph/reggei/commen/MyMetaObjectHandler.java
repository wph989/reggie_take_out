package com.wph.reggei.commen;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
 *  @author: WPH
 *  @Date: 2023/5/7 15:21
 *  TODO
 *  @Description: 公共字段自动填充
 */
    @Override
    public void insertFill(MetaObject metaObject) {
        /**
        * @Author:  Adminis
        * @Date:    2023/5/7 15:24
        * @Param:   [metaObject]
        * @Return:  void
        * @Exception:
        * @Description: 插入操作自动填充
        *
        */
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", new Long(BaseContext.getCurrentId()));
        metaObject.setValue("updateUser", new Long(BaseContext.getCurrentId()));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        /**
        * @Author:  Adminis
        * @Date:    2023/5/7 15:25
        * @Param:   [metaObject]
        * @Return:  void
        * @Exception:
        * @Description: 更新操作自动填充
        *
        */
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", new Long(BaseContext.getCurrentId()));
    }
}
