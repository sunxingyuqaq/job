package com.boot.job.datasource;

import com.boot.job.datasource.config.DruidConfiguration;
import com.boot.job.datasource.entity.UserInfoEntity;
import com.boot.job.datasource.service.UserInfoService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/6/11 17:22
 */
@EnableCaching
@MapperScan(basePackages = {"com.boot.job.datasource.dao"})
@EnableConfigurationProperties(DruidConfiguration.class)
@EnableTransactionManagement
@SpringBootApplication
public class DataSourceApplication {
    private final Logger log = LoggerFactory.getLogger(DataSourceApplication.class);
    @Resource
    private UserInfoService service;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DataSourceApplication.class);
        builder.web(WebApplicationType.SERVLET);
        builder.run(args);
    }

    @Bean
    public CommandLineRunner runner(){
        return (args -> {
            List<UserInfoEntity> list = service.list();
            list.forEach(System.out::println);
            List<UserInfoEntity> userInfoEntities = service.selectUserInfoByGtFraction(66);
            userInfoEntities.forEach(System.out::println);
        });
    }
}
