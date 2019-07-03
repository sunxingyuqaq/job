package com.boot.job.db.job.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.job.db.job.entity.Job;

import java.util.List;

/**
 * @author MrBird
 */
public interface JobMapper extends BaseMapper<Job> {
	
	List<Job> queryList();
}