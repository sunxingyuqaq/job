package com.boot.job.db;

import com.boot.job.db.datasource.DruidProperties;
import com.boot.job.db.datasource.cluster.MybatisClusterConfiguration;
import com.boot.job.db.datasource.primary.MybatisPrimaryConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Xingyu Sun
 * @date 2019/6/17 15:45
 */
@SpringBootApplication
@EnableConfigurationProperties({MybatisPrimaryConfiguration.PrimaryDatasource.class,
        MybatisClusterConfiguration.ClusterDatasource.class, DruidProperties.class})
public class MultiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MultiApplication.class).run(args);
    }
}
