package com.boot.job.datasource.service;

import com.boot.job.datasource.rpc.IRpcService;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:20
 */
public interface IHelloService extends IRpcService {

    String sayHi(String name, String message);

}
