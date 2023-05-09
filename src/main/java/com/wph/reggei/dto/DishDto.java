package com.wph.reggei.dto;

import com.wph.reggei.entity.Dish;
import com.wph.reggei.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
/**
 *  @author: WPH
 *  @Date: 2023/5/9 19:15
 *  TODO
 *  @Description:
 */
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
