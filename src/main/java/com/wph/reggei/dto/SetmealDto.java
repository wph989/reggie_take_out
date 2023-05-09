package com.wph.reggei.dto;

import com.wph.reggei.entity.Setmeal;
import com.wph.reggei.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {
/**
 *  @author: WPH
 *  @Date: 2023/5/9 19:19
 *  TODO
 *  @Description:
 */
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
