package com.boot.job.db.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.job.db.system.entity.Dept;

/**
 * @author MrBird
 */
public interface DeptMapper extends BaseMapper<Dept> {
    /**
     * 递归删除部门
     *
     * @param deptId deptId
     */
    void deleteDepts(String deptId);
}
