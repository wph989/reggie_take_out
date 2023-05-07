package com.wph.reggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wph.reggei.commen.CustomException;
import com.wph.reggei.entity.Category;
import com.wph.reggei.entity.Dish;
import com.wph.reggei.entity.Setmeal;
import com.wph.reggei.mapper.CategoryMapper;
import com.wph.reggei.service.CategoryService;
import com.wph.reggei.service.DishService;
import com.wph.reggei.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     *  @author: WPH
     *  @Date: 2023/5/7 15:53
     *  TODO
     *  @Description:
     */
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据id分类查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        // 判断是否有关联菜品
        if(count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        // 判断是否有关联套餐
        if(count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        super.removeById(id);

    }
}
