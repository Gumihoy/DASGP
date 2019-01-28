package com.gumihoy.dasgp.admin.common;

/**
 * @author kongtong.ouyang on 2018/10/9.
 */
public final class APIResult<T> {

    private static final int DEFAULT_SUCCESS_CODE = 0;
    private static final int DEFAULT_ERROR_CODE = 1;

    private static final String DEFAULT_SUCCESS_MSG = "";
    private static final String DEFAULT_ERROR_MSG = "";


    private boolean succ;
    private int code;
    private String msg;
    private T data;

    public static <T> APIResult<T> succ() {
        return succ(null);
    }

    public static <T> APIResult<T> succ(T data) {
        return of(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public static <T> APIResult<T> fail() {
        return fail(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MSG);
    }

    public static <T> APIResult<T> fail(String msg) {
        return fail(DEFAULT_ERROR_CODE, msg);
    }

    public static <T> APIResult<T> fail(int code, String msg) {
        return of(false, code, msg, null);
    }

    public static <T> APIResult<T> of(boolean succ, int code, String msg, T data) {
        return new APIResult<>(succ, code, msg, data);
    }


    public APIResult(boolean succ, int code, String msg, T data) {
        this.succ = succ;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSucc() {
        return succ;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
