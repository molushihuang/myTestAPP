package com.xqd.myapplication.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import com.xqd.myapplication.R;
import com.xqd.myapplication.upnp.*;
import com.xqd.myapplication.util.CommomUtils;
import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 投屏页面
 */
public class ProjectionActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = ProjectionActivity.class.getSimpleName();
    private RecyclerView rvDevice;//设别列表
    private TextView tvSelected;//选中设备名字
    private Switch swMute;//静音开关
    private SeekBar sbProgress;//进度条
    private SeekBar sbVolume;//音量条
    private Button btRefrsh;//刷新设备
    private Button btPlay;//播放
    private Button btPause;//暂停
    private Button btStop;//停止

    private DevicesAdapter mDevicesAdapter;
    private List<ClingDevice> mClingDevices = new ArrayList<>();

    /**
     * 用于监听发现设备
     */
    private BrowseRegistryListener mBrowseRegistryListener = new BrowseRegistryListener();
    private ServiceConnection mUpnpServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ClingUpnpService.LocalBinder binder = (ClingUpnpService.LocalBinder) service;
            ClingUpnpService beyondUpnpService = binder.getService();

            ClingManager clingUpnpServiceManager = ClingManager.getInstance();
            clingUpnpServiceManager.setUpnpService(beyondUpnpService);
            clingUpnpServiceManager.setDeviceManager(new DeviceManager());

            clingUpnpServiceManager.getRegistry().addListener(mBrowseRegistryListener);
            //Search on service created.
            clingUpnpServiceManager.searchDevices();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            ClingManager.getInstance().setUpnpService(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projection);
        initView();
        initData();
    }

    private void initView() {
        rvDevice = findViewById(R.id.rv_device);
        tvSelected = findViewById(R.id.tv_selected);
        swMute = findViewById(R.id.sw_mute);
        sbProgress = findViewById(R.id.sb_progress);
        sbVolume = findViewById(R.id.sb_volume);
        btRefrsh = findViewById(R.id.bt_refresh);
        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btStop = findViewById(R.id.bt_stop);

        btRefrsh.setOnClickListener(this);
        btPlay.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btStop.setOnClickListener(this);
        sbProgress.setOnSeekBarChangeListener(this);
        sbVolume.setOnSeekBarChangeListener(this);

    }

    private void initData() {
        mDevicesAdapter = new DevicesAdapter(this, mClingDevices);
        rvDevice.setLayoutManager(new LinearLayoutManager(this));
        rvDevice.setAdapter(mDevicesAdapter);
        mDevicesAdapter.setOnItemClicklistener(new DevicesAdapter.OnItemClickListener() {
            @Override
            public void click(ClingDevice clingDevice) {
                ClingManager.getInstance().setSelectedDevice(clingDevice);
                Device device = clingDevice.getDevice();
                if (CommomUtils.isNull(device)) {
                    return;
                }
                tvSelected.setText(clingDevice.getDevice().getDetails().getFriendlyName());
            }
        });

        swMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mBrowseRegistryListener.setOnDeviceListChangedListener(new BrowseRegistryListener.DeviceListChangedListener() {
            @Override
            public void onDeviceAdded(final IDevice device) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mClingDevices.add((ClingDevice) device);
                        mDevicesAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onDeviceRemoved(final IDevice device) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mClingDevices.remove((ClingDevice) device);
                        mDevicesAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        Intent serviceIntent = new Intent(ProjectionActivity.this, ClingUpnpService.class);
        bindService(serviceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_refresh:
                Collection<ClingDevice> devices = ClingManager.getInstance().getDmrDevices();
                ClingDeviceList.getInstance().setClingDeviceList(devices);
                if (devices != null) {
                    mClingDevices.clear();
                    mClingDevices.addAll(devices);
                    mDevicesAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.bt_play:
                break;
            case R.id.bt_pause:
                break;
            case R.id.bt_stop:
                break;
            default:
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacksAndMessages(null);
        // Unbind UPnP service
        unbindService(mUpnpServiceConnection);
        // Unbind System service
        //        unbindService(mSystemServiceConnection);
        // UnRegister Receiver
//        unregisterReceiver(mTransportStateBroadcastReceiver);

        ClingManager.getInstance().destroy();
        ClingDeviceList.getInstance().destroy();
    }
}
