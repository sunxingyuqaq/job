package com.boot.job.db.others.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.job.db.common.entity.QueryRequest;
import com.boot.job.db.others.entity.Eximport;

import java.util.List;

/**
 * @author MrBird
 */
public interface IEximportService extends IService<Eximport> {
    /**
     * 查询（分页）
     *
     * @param request  QueryRequest
     * @param eximport eximport
     * @return IPage<Eximport>
     */
    IPage<Eximport> findEximports(QueryRequest request, Eximport eximport);


    /**
     * 批量插入
     *
     * @param list List<Eximport>
     */
    void batchInsert(List<Eximport> list);

}
