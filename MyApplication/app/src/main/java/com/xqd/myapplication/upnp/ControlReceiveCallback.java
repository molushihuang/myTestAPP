package com.xqd.myapplication.upnp;


/**
 * 手机端接收投屏端信息回调
 */
public interface ControlReceiveCallback extends ControlCallback{

    void receive(IResponse response);
}
