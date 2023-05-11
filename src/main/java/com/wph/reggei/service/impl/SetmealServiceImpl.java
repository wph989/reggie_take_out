package com.wph.reggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wph.reggei.commen.CustomException;
import com.wph.reggei.dto.SetmealDto;
import com.wph.reggei.entity.Setmeal;
import com.wph.reggei.entity.SetmealDish;
import com.wph.reggei.mapper.SetmealMapper;
import com.wph.reggei.service.SetmealDishService;
import com.wph.reggei.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
/**
 *  @author: WPH
 *  @Date: 2023/5/7 19:23
 *  TODO
 *  @Description:
 */
    @Autowired
    private SetmealDishService setmealDishService;

    // 保存套餐基本信息和套餐关联的菜品信息
    @Override
    @Transactional  // 因为需要操作两张表，所以引入事务
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(iterm -> {
            // 写入套餐id到套餐菜品中
            iterm.setSetmealId(setmealDto.getId());
            return iterm;
        }).collect(Collectors.toList());

        // 保存套餐和菜品关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        // 获取当前可删除的个数
        int count = this.count(queryWrapper);
        if(count > 0){
            // 如果不能删除，则抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        this.removeByIds(ids);

        // 查询套餐关联的菜品
        LambdaQueryWrapper<SetmealDish> queryWrapperDish = new LambdaQueryWrapper<>();
        queryWrapperDish.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapperDish);
    }
}
