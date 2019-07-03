package com.boot.job.db.job.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.job.db.common.entity.QueryRequest;
import com.boot.job.db.job.entity.JobLog;

/**
 * @author MrBird
 */
public interface IJobLogService extends IService<JobLog> {

    IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog);

    void saveJobLog(JobLog log);

    void deleteJobLogs(String[] jobLogIds);
}
