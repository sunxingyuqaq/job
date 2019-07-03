package com.boot.job.datasource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.job.datasource.entity.UserInfoEntity;
import com.boot.job.datasource.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xingyu Sun
 * @date 2019/6/12 11:24
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private UserInfoService service;

    @GetMapping("list")
    @Cacheable(cacheNames = "user",cacheManager = "cacheManager",condition = "#age>20")
    public Object test(@RequestParam("age")Integer age){
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.ge("age",age);
        return service.list(wrapper);
    }

    @GetMapping("page")
    public Object page(@RequestParam("pageNumber")Integer pageNumber,@RequestParam("pageSize")Integer pageSize){
        return service.test(pageNumber,pageSize);
    }
}
