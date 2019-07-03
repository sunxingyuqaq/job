package com.boot.job.db.generator.servie.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.job.db.common.entity.FebsConstant;
import com.boot.job.db.common.entity.QueryRequest;
import com.boot.job.db.common.utils.SortUtil;
import com.boot.job.db.generator.entity.Column;
import com.boot.job.db.generator.entity.Table;
import com.boot.job.db.generator.mapper.GeneratorMapper;
import com.boot.job.db.generator.servie.IGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MrBird
 */
@Service
public class GeneratorServiceImpl implements IGeneratorService {
    @Autowired
    private GeneratorMapper generatorMapper;

    @Override
    public List<String> getDatabases(String databaseType) {
        return generatorMapper.getDatabases(databaseType);
    }

    @Override
    public IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName) {
        Page<Table> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_ASC, false);
        return generatorMapper.getTables(page, tableName, databaseType, schemaName);
    }

    @Override
    public List<Column> getColumns(String databaseType, String schemaName, String tableName) {
        return generatorMapper.getColumns(databaseType, schemaName, tableName);
    }
}
