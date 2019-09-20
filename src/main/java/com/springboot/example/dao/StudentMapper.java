package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 学生 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Component
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
