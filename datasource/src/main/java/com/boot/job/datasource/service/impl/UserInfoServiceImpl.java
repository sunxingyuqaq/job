package com.boot.job.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.job.datasource.dao.UserInfoDao;
import com.boot.job.datasource.entity.UserInfoEntity;
import com.boot.job.datasource.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/6/11 17:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Override
    public List<UserInfoEntity> selectUserInfoByGtFraction(long fraction) {
        return baseMapper.selectUserInfoByGtFraction(fraction);
    }

    @Override
    public IPage<UserInfoEntity> test(int page, int limit) {
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 12).like("skill", "java").between("age", 18, 22).orderByDesc("id");
        UserInfoEntity byId = getById(12);
        System.out.println(byId);
        return page(new Page<>(page, limit), wrapper);
    }
}
