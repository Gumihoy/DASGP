package com.gumihoy.dasgp.pigeon.common.api;

import java.lang.reflect.Method;

/**
 * @author kongtong.ouyang on 2018/9/28.
 */
public interface Invocation {

    Object[] getArguments();

    Method getMethod();

}
