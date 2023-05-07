package com.wph.reggei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wph.reggei.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     *  @author: WPH
     *  @Date: 2023/5/7 15:50
     *  TODO
     *  @Description: 菜品分类接口
     */
}
