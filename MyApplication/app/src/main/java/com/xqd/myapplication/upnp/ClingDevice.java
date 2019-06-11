package com.xqd.myapplication.upnp;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by 谢邱东 on 2019/6/11 13:53.
 * 设备实体
 * NO bug
 */
public class ClingDevice implements IDevice<Device> {
    private Device mDevice;

    public ClingDevice(Device device) {
        this.mDevice = device;

    }

    @Override
    public Device getDevice() {
        return mDevice;
    }


}
