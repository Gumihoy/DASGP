package com.gumihoy.dasgp.pigeon.client.invoker;

import com.gumihoy.dasgp.pigeon.common.api.Invocation;

/**
 * @author kongtong.ouyang on 2018/10/17.
 */
public abstract class AbstractInvoker implements Invoker {

    protected boolean async = false;

    @Override
    public Object invoke(Invocation invocation) throws Exception {
        return async ? asyncInvoke(invocation) : syncInvoke(invocation);
    }

    public abstract Object syncInvoke(Invocation invocation) throws Exception;

    public abstract Object asyncInvoke(Invocation invocation) throws Exception;


    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public void setAsync(boolean async) {
        this.async = async;
    }
}
