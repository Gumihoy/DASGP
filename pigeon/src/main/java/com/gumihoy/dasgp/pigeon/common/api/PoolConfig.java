package com.gumihoy.dasgp.pigeon.common.api;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.thrift.transport.TTransport;

/**
 * @author kongtong.ouyang on 2018/9/29.
 */
public class PoolConfig {

    public GenericObjectPoolConfig<TTransport> toObjectPoolConfig() {
        return new GenericObjectPoolConfig<>();
    }
}
