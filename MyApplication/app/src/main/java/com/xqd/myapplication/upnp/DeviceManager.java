package com.xqd.myapplication.upnp;


import android.content.Context;
import android.util.Log;
import com.xqd.myapplication.util.CommomUtils;


/**
 * 设备管理
 */
public class DeviceManager implements IDeviceManager {
    private static final String TAG = DeviceManager.class.getSimpleName();
    /**
     * 已选中的设备, 它也是 ClingDeviceList 中的一员
     */
    private ClingDevice mSelectedDevice;
    private SubscriptionControl mSubscriptionControl;

    public DeviceManager() {
        mSubscriptionControl = new SubscriptionControl();
    }

    @Override
    public IDevice getSelectedDevice() {
        return mSelectedDevice;
    }

    @Override
    public void setSelectedDevice(IDevice selectedDevice) {
//        if (selectedDevice != mSelectedDevice){
//            Intent intent = new Intent(Intents.ACTION_CHANGE_DEVICE);
//            sendBroadcast(intent);
//        }

        Log.i(TAG, "Change selected device.");
        mSelectedDevice = (ClingDevice) selectedDevice;

    }

    @Override
    public void cleanSelectedDevice() {

    }

    @Override
    public void registerAVTransport(Context context) {
        if (CommomUtils.isNull(mSelectedDevice)) {
            return;
        }

        mSubscriptionControl.registerAVTransport(mSelectedDevice, context);
    }

    @Override
    public void registerRenderingControl(Context context) {
        if (CommomUtils.isNull(mSelectedDevice)) {
            return;
        }

        mSubscriptionControl.registerRenderingControl(mSelectedDevice, context);
    }

    @Override
    public void destroy() {
        if (CommomUtils.isNotNull(mSubscriptionControl)) {
            mSubscriptionControl.destroy();
        }
    }
}
