package com.smart.springbootcrud;

import com.smart.springbootcrud.entity.User;
import com.smart.springbootcrud.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringbootCrudApplicationTests {
    @Resource
    UserService userService;

    @Test
    void contextLoads() {
       
    }

}
