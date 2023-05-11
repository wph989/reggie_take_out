package com.wph.reggei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wph.reggei.commen.R;
import com.wph.reggei.dto.SetmealDto;
import com.wph.reggei.entity.Category;
import com.wph.reggei.entity.Setmeal;
import com.wph.reggei.service.CategoryService;
import com.wph.reggei.service.SetmealDishService;
import com.wph.reggei.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
/**
 *  @author: WPH
 *  @Date: 2023/5/7 19:28
 *  TODO
 *  @Description:
 */
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;


    // 新增套餐套餐接口
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
    // 删除套餐接口
    @DeleteMapping
    public R<String> remove(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }

    // 按分页查询套餐信息
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        // 查询套餐基本信息，以更新时间降序排序
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        // 拷贝对象
        BeanUtils.copyProperties(pageInfo, setmealDtoPage);
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> dtoList = records.stream().map(iterm -> {
            SetmealDto setmealDto = new SetmealDto();
            // 把套餐信息拷贝到套餐dto中
            BeanUtils.copyProperties(iterm, setmealDto);
            // 获取分类id
            Long categoryId = iterm.getCategoryId();
            // 根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                // 获取分类名称，并写入套餐Dto中
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(dtoList);

        return R.success(setmealDtoPage);
    }

    // 根据分类id查询菜品
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(setmeal.getName()), Setmeal::getName, setmeal.getName());
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());

        return R.success(setmealService.list(queryWrapper));
    }
}
