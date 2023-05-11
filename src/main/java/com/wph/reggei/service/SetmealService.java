package com.wph.reggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wph.reggei.dto.SetmealDto;
import com.wph.reggei.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}
