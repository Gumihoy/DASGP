package com.gumihoy.dasgp.pigeon.client.invoker;

import com.gumihoy.dasgp.pigeon.common.api.Invocation;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public interface Invoker<T> {

    Object invoke(Invocation invocation) throws Exception;

    boolean isAsync();

    void setAsync(boolean async);
}
