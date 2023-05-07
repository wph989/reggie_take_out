package com.wph.reggei.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wph.reggei.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
