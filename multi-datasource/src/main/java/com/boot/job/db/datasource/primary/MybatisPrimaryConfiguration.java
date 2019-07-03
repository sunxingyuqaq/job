package com.boot.job.db.datasource.primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.boot.job.db.common.configure.MybatisPlusConfigure;
import com.boot.job.db.datasource.DruidProperties;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Xingyu Sun
 * @date 2019/6/18 8:33
 */
@Configuration
@AutoConfigureAfter(MybatisPlusConfigure.class)
public class MybatisPrimaryConfiguration {

    @Data
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.base")
    @Configuration
    public static class PrimaryDatasource {
        private String url;
        private String driverClassName;
        private String username;
        private String password;

        @Bean
        @Primary
        public DataSource base(DruidProperties druidProperties) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setInitialSize(druidProperties.getInitialSize());
            dataSource.setMinIdle(druidProperties.getMinIdle());
            dataSource.setMaxActive(druidProperties.getMaxActive());
            dataSource.setMaxWait(druidProperties.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
            dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
            dataSource.setValidationQuery(druidProperties.getValidationQuery());
            dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
            dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
            dataSource.setTestOnReturn(druidProperties.isTestOnReturn());
            dataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
            try {
                dataSource.setFilters(druidProperties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.setConnectionProperties(druidProperties.getConnectionProperties());
            return dataSource;
        }
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("base") DataSource base) {
        return new DataSourceTransactionManager(base);
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("base") DataSource base)
            throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(base);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(PrimaryDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(PrimaryDataSourceConfig.TYPE_ALIASES_PACKAGE);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        dbConfig.setFieldStrategy(FieldStrategy.NOT_NULL);
        dbConfig.setCapitalMode(true);
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        sessionFactory.setGlobalConfig(globalConfig);

        sessionFactory.setConfiguration(configuration);

        sessionFactory.setPlugins(new Interceptor[]{
                interceptor(),performanceInterceptor()
        });
        return sessionFactory.getObject();
    }

    @Bean(name = "primaryScan")
    @Primary
    public MapperScannerConfigurer primaryScan() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(PrimaryDataSourceConfig.PACKAGE);
        configurer.setSqlSessionFactoryBeanName("primarySqlSessionFactory");
        return configurer;
    }

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev","test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    @Bean("interceptor")
    public PaginationInterceptor interceptor() {
        return new PaginationInterceptor();
    }

}
