package com.boot.job.datasource.server;

import com.boot.job.datasource.rpc.IRpcService;

import java.io.IOException;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:23
 */
public interface Server {

    int port = 8080;

    void start() throws IOException;

    void stop();

    /**
     * 服务注册
     * -- serviceInterface 对外暴露接口
     * -- 内部实现类
     */
    void register(Class<? extends IRpcService> serviceInterface, Class<? extends IRpcService> impl);

}
