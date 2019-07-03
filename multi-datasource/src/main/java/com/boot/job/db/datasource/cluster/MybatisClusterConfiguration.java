package com.boot.job.db.datasource.cluster;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.boot.job.db.datasource.DruidProperties;
import lombok.Data;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Xingyu Sun
 * @date 2019/6/18 8:35
 */
@Configuration
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class MybatisClusterConfiguration {

    @Autowired
    private PaginationInterceptor interceptor;
    @Autowired
    private PerformanceInterceptor performanceInterceptor;

    @Data
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.quartz")
    @Configuration
    public static class ClusterDatasource {
        private String url;
        private String driverClassName;
        private String username;
        private String password;

        @Bean("quartz")
        public DataSource quartz(DruidProperties druidProperties) {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
            dataSource.setValidationQuery(druidProperties.getValidationQuery());
            dataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
            dataSource.setInitialSize(druidProperties.getInitialSize());
            dataSource.setMinIdle(druidProperties.getMinIdle());
            dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
            dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
            dataSource.setTestOnReturn(druidProperties.isTestOnReturn());
            dataSource.setMaxActive(druidProperties.getMaxActive());
            dataSource.setMaxWait(druidProperties.getMaxWait());
            dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());

            try {
                dataSource.setFilters(druidProperties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dataSource.setConnectionProperties(druidProperties.getConnectionProperties());
            return dataSource;
        }
    }

    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager(@Qualifier("quartz") DataSource quartz) {
        return new DataSourceTransactionManager(quartz);
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("quartz") DataSource quartz)
            throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(quartz);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setTypeAliasesPackage(ClusterDataSourceConfig.TYPE_ALIASES_PACKAGE);

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        dbConfig.setFieldStrategy(FieldStrategy.NOT_NULL);
        dbConfig.setCapitalMode(true);
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        sessionFactory.setGlobalConfig(globalConfig);

        sessionFactory.setConfiguration(configuration);
        sessionFactory.setPlugins(new Interceptor[]{
                interceptor,performanceInterceptor
        });
        return sessionFactory.getObject();
    }
}
