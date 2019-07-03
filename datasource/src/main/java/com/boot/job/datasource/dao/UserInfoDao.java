package com.boot.job.datasource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.job.datasource.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/6/11 17:24
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    /**
     * selectUserInfoByGtFraction
     * @param fraction f
     * @return l
     */
    List<UserInfoEntity> selectUserInfoByGtFraction(long fraction);

    /**
     * findFirstById
     * @param id i
     * @return u
     */
    UserInfoEntity findFirstById(long id);

    /**
     * countById
     * @param id i
     * @return i
     */
    int countById(long id);
}
