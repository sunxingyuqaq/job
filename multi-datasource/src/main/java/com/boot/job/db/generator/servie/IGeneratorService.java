package com.boot.job.db.generator.servie;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boot.job.db.common.entity.QueryRequest;
import com.boot.job.db.generator.entity.Column;
import com.boot.job.db.generator.entity.Table;

import java.util.List;

/**
 * @author MrBird
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
