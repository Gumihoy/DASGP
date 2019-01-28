package com.gumihoy.dasgp.pigeon.client.loadbalance;

import com.gumihoy.dasgp.pigeon.client.invoker.Invoker;
import com.gumihoy.dasgp.pigeon.common.api.Invocation;

import java.util.List;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public abstract class AbstractLoadBalance implements ILoadBalance {



    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, Invocation invocation) throws Exception {
        if (invokers == null
                || invokers.size() == 0) {
            throw new IllegalArgumentException("invoker is null.");
        }
        if (invokers.size() == 1) {
            return invokers.get(0);
        }
        return doSelect(invokers, invocation);
    }

    protected abstract <T> Invoker<T> doSelect(List<Invoker<T>> invokers, Invocation invocation);

}
