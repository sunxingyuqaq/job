package com.boot.job.admin.service;


import com.boot.job.admin.core.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.Date;
import java.util.Map;

/**
 * core job action for xxl-job
 * 
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface XxlJobService {

	/**
	 * page list
	 *
	 * @param start
	 * @param length
	 * @param jobGroup
	 * @param jobDesc
	 * @param executorHandler
	 * @param filterTime
	 * @return
	 */
	Map<String, Object> pageList(int start, int length, int jobGroup, String jobDesc, String executorHandler, String filterTime);

	/**
	 * add job, default quartz stop
	 *
	 * @param jobInfo
	 * @return
	 */
	ReturnT<String> add(XxlJobInfo jobInfo);

	/**
	 * update job, update quartz-cron if started
	 *
	 * @param jobInfo
	 * @return
	 */
	ReturnT<String> update(XxlJobInfo jobInfo);

	/**
	 * remove job, unbind quartz
	 *
	 * @param id
	 * @return
	 */
	ReturnT<String> remove(int id);

	/**
	 * start job, bind quartz
	 *
	 * @param id
	 * @return
	 */
	ReturnT<String> start(int id);

	/**
	 * stop job, unbind quartz
	 *
	 * @param id
	 * @return
	 */
	ReturnT<String> stop(int id);

	/**
	 * dashboard info
	 *
	 * @return
	 */
	Map<String,Object> dashboardInfo();

	/**
	 * chart info
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	ReturnT<Map<String,Object>> chartInfo(Date startDate, Date endDate);

}
