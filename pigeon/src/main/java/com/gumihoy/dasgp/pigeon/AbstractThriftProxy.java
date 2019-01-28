package com.gumihoy.dasgp.pigeon;

import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Constructor;

/**
 * @author kongtong.ouyang on 2018/10/18.
 */
public abstract class AbstractThriftProxy implements InitializingBean  {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractThriftProxy.class);

    protected Class<?>[] classes;
    protected int classesSize;
    protected Class<?> serviceInterface;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (serviceInterface == null) {
            throw new IllegalArgumentException("serviceInterface is null.");
        }

        this.classes = serviceInterface.getClasses();
        classesSize = this.classes.length;


    }

    public Class<?> getIfaceInterface(boolean async) {
        return async ? getAsyncIfaceInterface() : getSyncIfaceInterface();
    }

    public Class<?> getSyncIfaceInterface() {
        for (int i = 0; i < classesSize; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && c.isInterface()
                    && c.getSimpleName().equals("Iface")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Interface of Iface");
    }

    public Class<?> getAsyncIfaceInterface() {
        for (int i = 0; i < classesSize; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && c.isInterface()
                    && c.getSimpleName().equals("AsyncIface")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Interface of AsyncIface");
    }




    public Class getSynClientClass() {
        for (int i = 0; i < classesSize; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && !c.isInterface()
                    && c.getSimpleName().equals("Client")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Class of Client");
    }

    public Class<TProcessor> getAsyncClientClass(Class<?> serviceInterface) {
        for (int i = 0; i < classesSize; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && !c.isInterface()
                    && c.getSimpleName().equals("AsyncClient")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Class of AsyncClient");
    }



    public Class<TProcessor> getProcessorClass(boolean async) {
        return async ? getAsyncProcessorClass() : getSyncProcessorClass();
    }


    public Class<TProcessor> getSyncProcessorClass() {
        int len = classes.length;
        for (int i = 0; i < len; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && !c.isInterface()
                    && c.getSimpleName().equals("Processor")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Class of Processor");
    }

    public Class<TProcessor> getAsyncProcessorClass() {
        Class<?>[] classes = serviceInterface.getClasses();
        int len = classes.length;
        for (int i = 0; i < len; ++i) {
            Class c = classes[i];
            if (c.isMemberClass()
                    && !c.isInterface()
                    && c.getSimpleName().equals("AsyncProcessor")) {
                return c;
            }
        }
        throw new IllegalArgumentException("serviceInterface must contain Sub Class of AsyncProcessor");
    }



    public Constructor<TProcessor> getSyncProcessorConstructor() {
        try {
            return getSyncProcessorClass().getConstructor(getSyncIfaceInterface());
        } catch (Exception e) {
            throw new IllegalArgumentException("serviceInterface must contain Sub Class of Processor with Constructor(Iface.class):" + e.getMessage());
        }
    }

    public Constructor<TProcessor> getAsyncProcessorConstructor() {
        try {
            return getAsyncProcessorClass().getConstructor(getAsyncIfaceInterface());
        } catch (Exception e) {
            throw new IllegalArgumentException("serviceInterface must contain Sub Class of Processor with Constructor(Iface.class):" + e.getMessage());
        }
    }


    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
