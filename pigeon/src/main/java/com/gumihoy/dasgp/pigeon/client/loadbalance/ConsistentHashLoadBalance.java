package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.common.api.Invocation;
import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;

import java.util.List;

/**
 * 一致性Hash，相同参数的请求总是发到同一提供者。
 *
 * @author kongtong.ouyang on 2018/9/27.
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, Invocation invocation) {
        return null;
    }

}
