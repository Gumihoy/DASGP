package com.gumihoy.dasgp.pigeon.client;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author kongtong.ouyang on 2018/9/27.
 */
public class ThriftClientMethodInterceptor implements MethodInterceptor {

    private ThriftClientProxy clientProxy;

    public ThriftClientMethodInterceptor(ThriftClientProxy clientProxy) {
        this.clientProxy = clientProxy;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        String methodName = methodInvocation.getMethod().getName();



        return null;
    }




}
