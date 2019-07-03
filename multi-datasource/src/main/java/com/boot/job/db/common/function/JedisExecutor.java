package com.boot.job.db.common.function;


import com.boot.job.db.common.exception.RedisConnectException;

/**
 * @author MrBird
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    /**
     * execute
     *
     * @param t t
     * @return r r
     * @throws RedisConnectException r
     */
    R execute(T t) throws RedisConnectException;
}
