package com.gumihoy.dasgp.pigeon.common.util;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author kongtong.ouyang on 2018/10/18.
 */
public final class ProxyFactoryUtils {

    public static Object getInterfaceProxy(Class serviceInterface, Advice... advices) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(serviceInterface);
        proxyFactory.setProxyTargetClass(true);
        for (Advice advice : advices) {
            proxyFactory.addAdvice(advice);
        }
        return proxyFactory.getProxy();
    }

    public static Object getTargetClassProxy(Object target, Advice... advices) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.setProxyTargetClass(true);
        for (Advice advice : advices) {
            proxyFactory.addAdvice(advice);
        }
        return proxyFactory.getProxy();
    }


}
