package com.boot.job.db.datasource.primary;

import org.springframework.context.annotation.Configuration;

/**
 * @author Xingyu Sun
 * @date 2019/6/18 8:42
 */
@Configuration
public class PrimaryDataSourceConfig {

    public static final String PACKAGE = "com.boot.job.db.generator.mapper,com.boot.job.db.system.mapper,com.boot.job.db.job.mapper,com.boot.job.db.monitor.mapper,com.boot.job.db.others.mapper";
    public static final String MAPPER_LOCATION = "classpath:mapper/*/*.xml";
    public static final String TYPE_ALIASES_PACKAGE = "com.boot.job.db.generator.entity,com.boot.job.db.system.entity,com.boot.job.db.job.entity,com.boot.job.db.monitor.entity,com.boot.job.db.others.entity";

}
