package com.smart.springbootcrud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.springbootcrud.entity.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
