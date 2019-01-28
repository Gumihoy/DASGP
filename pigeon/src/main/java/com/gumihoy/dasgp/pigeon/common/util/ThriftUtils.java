package com.gumihoy.dasgp.pigeon.common.util;

import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public final class ThriftUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThriftUtils.class);

    public static void close(TTransport socket) {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (Exception e) {
            LOGGER.warn("close transport error. error msg: {}", e.getMessage());
        }
    }

}
