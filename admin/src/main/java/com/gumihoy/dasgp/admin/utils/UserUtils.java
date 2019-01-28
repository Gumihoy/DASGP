package com.gumihoy.dasgp.admin.utils;

import com.gumihoy.dasgp.admin.common.User;

/**
 * @author kongtong.ouyang on 2018/10/10.
 */
public final class UserUtils {

    private static final ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(User user) {
        THREAD_LOCAL.set(user);
    }

    public static User getUser() {
        return THREAD_LOCAL.get();
    }

    public static void removeUser() {
        THREAD_LOCAL.remove();
    }
}
