package com.wph.reggei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wph.reggei.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
