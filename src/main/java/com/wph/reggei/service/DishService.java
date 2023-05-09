package com.wph.reggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wph.reggei.dto.DishDto;
import com.wph.reggei.entity.Dish;

public interface DishService extends IService<Dish> {
    // 新增菜品时同时插入对应的口味数据，需要操作两张表：dish, dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    // 根据ID查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    // 更新菜品信息同时更新口味信息
    public void updateWithFlavor(DishDto dishDto);
}
