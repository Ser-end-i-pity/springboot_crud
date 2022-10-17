package com.smart.springbootcrud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.springbootcrud.entity.User;
import com.smart.springbootcrud.mapper.UserMapper;
import com.smart.springbootcrud.req.RequestParam;
import com.smart.springbootcrud.resp.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/a")
public class IndexController {

    @Resource
    UserMapper userMapper;

    @GetMapping("/t")
    public String test(){
        return "Hello World";
    }

    @GetMapping("/user")
    public Response queryAll(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return new Response<>(true,null, userMapper.selectList(wrapper));
    }

    @PostMapping("/list")
    public Response<?> list(@RequestBody RequestParam param){
        if (param.pageNum==null || param.pageSize==null){
            return new Response<>(false,"缺少pageNum或者pageSize字段",null);
        }
        Page<User> page = new Page<>(param.pageNum, param.pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (param.keyword!=null){
            wrapper.like("name",param.keyword);
        }
        return new Response<>(true,null,userMapper.selectPage(page,wrapper));
    }

    @PostMapping("/add")
    public Response<?> add(@RequestBody User user){
        if (user.getName() == null) return new Response<>(false,"姓名不能为空!",null);
        user.setEntryDate(new Date());
        userMapper.insert(user);
        return new Response<>(true,null,"添加成功!");
    }

    @PostMapping("/modify")
    public Response<?> modify(@RequestBody User user){
        if (user.getId() == null || user.getName() == null) return new Response<>(false,"姓名不能为空!",null);
        userMapper.updateById(user);
        return new Response<>(true,null,"修改成功!");
    }

    @PostMapping("/del")
    public Response<?> del(@org.springframework.web.bind.annotation.RequestParam("id") Integer id){
        if (id == null) return new Response<>(false,"编号参数缺失!",null);
        userMapper.deleteById(id);
        return new Response<>(true,null,"删除成功!");
    }
}
