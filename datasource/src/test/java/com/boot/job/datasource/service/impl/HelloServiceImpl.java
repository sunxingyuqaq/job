package com.boot.job.datasource.service.impl;

import com.boot.job.datasource.service.IHelloService;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:21
 */
public class HelloServiceImpl implements IHelloService {

    private static final long serialVersionUID = 146468468464364698L;

    @Override
    public String sayHi(String name, String message) {
        return "hi~!" + name + "," + message;
    }
}
