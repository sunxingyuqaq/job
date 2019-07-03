package com.boot.job.datasource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.job.datasource.entity.UserInfoEntity;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/6/11 17:25
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    /**
     * selectUserInfoByGtFraction
     *
     * @param fraction f
     * @return l
     */
    List<UserInfoEntity> selectUserInfoByGtFraction(long fraction);

    /**
     * test
     * @param page p
     * @param limit l
     * @return l
     */
    IPage<UserInfoEntity> test(int page, int limit);
}
