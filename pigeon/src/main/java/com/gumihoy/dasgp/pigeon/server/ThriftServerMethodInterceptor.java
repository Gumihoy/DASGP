package com.gumihoy.dasgp.pigeon.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kongtong.ouyang on 2018/9/27.
 */
public class ThriftServerMethodInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftServerMethodInterceptor.class);


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOGGER.info("ThriftServerMethodInterceptor invoke....");
        Object result = invocation.proceed();
        return result;
    }


}
