package com.xqd.myapplication.upnp;


/**
 * 设备控制操作 回调
 * @param <T>
 */
public interface ControlCallback<T> {

    void success(IResponse<T> response);

    void fail(IResponse<T> response);
}
