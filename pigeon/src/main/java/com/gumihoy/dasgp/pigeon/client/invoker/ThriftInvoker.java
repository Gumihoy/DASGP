package com.gumihoy.dasgp.pigeon.client.invoker;

import com.gumihoy.dasgp.pigeon.common.api.Invocation;
import com.gumihoy.dasgp.pigeon.common.api.ServerConn;

/**
 * @author kongtong.ouyang on 2018/10/17.
 */
public class ThriftInvoker extends AbstractInvoker {

    protected ServerConn serverConn;



    @Override
    public Object syncInvoke(Invocation invocation) throws Exception {
        return null;
    }

    @Override
    public Object asyncInvoke(Invocation invocation) throws Exception {
        return null;
    }

}
