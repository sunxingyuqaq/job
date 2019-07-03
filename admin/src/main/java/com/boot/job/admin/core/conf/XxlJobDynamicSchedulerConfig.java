package com.boot.job.admin.core.conf;


import com.boot.job.admin.core.schedule.XxlJobDynamicScheduler;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @author xuxueli 2018-10-28 00:18:17
 */
@Configuration
public class XxlJobDynamicSchedulerConfig {

    @Bean("schedulerFactory")
    public SchedulerFactoryBean getSchedulerFactoryBean(DataSource dataSource){
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setStartupDelay(20);
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        return schedulerFactory;
    }

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobDynamicScheduler getXxlJobDynamicScheduler(SchedulerFactoryBean schedulerFactory){
        Scheduler scheduler = schedulerFactory.getScheduler();
        XxlJobDynamicScheduler xxlJobDynamicScheduler = new XxlJobDynamicScheduler();
        xxlJobDynamicScheduler.setScheduler(scheduler);
        return xxlJobDynamicScheduler;
    }

}
