package com.xqd.myapplication.upnp;


/**
 * 设备控制 返回结果
 * @param <T>
 */
public interface IResponse<T> {

    T getResponse();

    void setResponse(T response);
}
