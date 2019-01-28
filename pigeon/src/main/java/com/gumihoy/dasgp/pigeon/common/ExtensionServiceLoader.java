package com.gumihoy.dasgp.pigeon.common;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.client.invoker.ThriftInvoker;
import com.gumihoy.dasgp.pigeon.client.loadbalance.ILoadBalance;
import com.gumihoy.dasgp.pigeon.client.loadbalance.WeightRandomLoadBalance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kongtong.ouyang on 2018/10/23.
 */
public class ExtensionServiceLoader<T> {

    private static final String PREFIX = "META-INF/pigeon/";
    private static final ConcurrentHashMap<Class, ExtensionServiceLoader> SERVICE_LOADER_MAP = new ConcurrentHashMap<>();

    private Class<T> service;
    private final ConcurrentHashMap<String, Object> SERVICE_MAP = new ConcurrentHashMap<>();

    public ExtensionServiceLoader(Class<T> service) throws Exception {
        this.service = service;
        this.loadService(service);
    }


    public static <T> ExtensionServiceLoader<T> getServiceLoader(Class<T> service) throws Exception {
        if (service == null) {
            throw new IllegalArgumentException("clazz is null.");
        }
        if (!service.isInterface()) {
            throw new IllegalArgumentException("clazz is not interface.");
        }

        ExtensionServiceLoader<T> serviceLoader = SERVICE_LOADER_MAP.get(service);
        if (serviceLoader == null) {
            serviceLoader = new ExtensionServiceLoader(service);
            SERVICE_LOADER_MAP.putIfAbsent(service, serviceLoader);
        }

        return serviceLoader;
    }

    public <T> T getService(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name is null.");
        }
        return (T) this.SERVICE_MAP.get(name);
    }


    private void loadService(Class<T> clazz) throws Exception {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null.");
        }

        String fileName = PREFIX + clazz.getName();

        Enumeration<URL> urls;
        ClassLoader classLoader = ExtensionServiceLoader.class.getClassLoader();
        if (classLoader != null) {
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
            classLoader = ClassLoader.getSystemClassLoader();
        }

        while (urls != null
                && urls.hasMoreElements()) {
            URL url = urls.nextElement();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    final int ci = line.indexOf('#');
                    if (ci >= 0) {
                        continue;
                    }
                    String name = null;
                    String clazzName = null;
                    int i = line.indexOf('=');
                    name = line.substring(0, i).trim();
                    clazzName = line.substring(i + 1).trim();

                    if (name.length() == 0
                            || clazzName.length() == 0) {
                        continue;
                    }

                    Class instanceClazz = Class.forName(clazzName, true, classLoader);
                    SERVICE_MAP.putIfAbsent(name, instanceClazz.newInstance());
                }
            } catch (Exception e) {

            } finally {

            }

        }


    }


    public static void main(String[] args) {
        try {

            WeightRandomLoadBalance loadBalance1 = ExtensionServiceLoader.getServiceLoader(ILoadBalance.class).getService(WeightRandomLoadBalance.NAME);
            WeightRandomLoadBalance loadBalance2 = ExtensionServiceLoader.getServiceLoader(ILoadBalance.class).getService(WeightRandomLoadBalance.NAME);

            List<Invoker<ThriftInvoker>> invokers = new ArrayList<>();
            invokers.add(new ThriftInvoker());
            invokers.add(new ThriftInvoker());
            loadBalance1.select(invokers, null);

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
