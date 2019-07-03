package com.boot.job.db.generator.mapper;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.job.db.generator.entity.Column;
import com.boot.job.db.generator.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author MrBird
 */
public interface GeneratorMapper {

    List<String> getDatabases(@Param("databaseType") String databaseType);

    IPage<Table> getTables(Page page, @Param("tableName") String tableName, @Param("databaseType") String databaseType, @Param("schemaName") String schemaName);

    List<Column> getColumns(@Param("databaseType") String databaseType, @Param("schemaName") String schemaName, @Param("tableName") String tableName);
}
