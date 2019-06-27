package com.xqd.myapplication.ui;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.xqd.myapplication.R;
import com.xqd.myapplication.upnp.*;
import com.xqd.myapplication.util.AllCode;
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

    /**
     * 连接设备状态: 播放状态
     */
    public static final int PLAY_ACTION = 0xa1;
    /**
     * 连接设备状态: 暂停状态
     */
    public static final int PAUSE_ACTION = 0xa2;
    /**
     * 连接设备状态: 停止状态
     */
    public static final int STOP_ACTION = 0xa3;
    /**
     * 连接设备状态: 转菊花状态
     */
    public static final int TRANSITIONING_ACTION = 0xa4;
    /**
     * 获取进度
     */
    public static final int GET_POSITION_INFO_ACTION = 0xa5;
    /**
     * 投放失败
     */
    public static final int ERROR_ACTION = 0xa5;

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
    private Context mContext;
    private boolean seek;//是否只为能够在调整进度过程中
    /**
     * 投屏控制器
     */
    private ClingPlayControl mClingPlayControl = new ClingPlayControl();
    private BroadcastReceiver mTransportStateBroadcastReceiver;

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
        mContext = this;
        initView();
        initData();
        registerReceivers();
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

        sbVolume.setMax(100);
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
                mClingPlayControl.setMute(isChecked, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "setMute success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "setMute fail");
                    }
                });
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

    private void registerReceivers() {
        //Register play status broadcast
        mTransportStateBroadcastReceiver = new TransportStateBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AllCode.ACTION_PLAYING);
        filter.addAction(AllCode.ACTION_PAUSED_PLAYBACK);
        filter.addAction(AllCode.ACTION_STOPPED);
        filter.addAction(AllCode.ACTION_TRANSITIONING);
        registerReceiver(mTransportStateBroadcastReceiver, filter);
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
                if (ClingManager.getInstance().getSelectedDevice() == null) {
                    showTaost("请先选择设备");
                    return;
                }
                play();
                break;
            case R.id.bt_pause:
                pause();
                break;
            case R.id.bt_stop:
                stop();
                break;
            default:
                break;
        }

    }

    /**
     * 播放视频
     */
    private void play() {
        @DLANPlayState.DLANPlayStates int currentState = mClingPlayControl.getCurrentState();

        if (currentState == DLANPlayState.STOP) {
            mClingPlayControl.playNew(AllCode.TEST_URL, new ControlCallback() {

                @Override
                public void success(IResponse response) {
                    mClingPlayControl.setCurrentState(DLANPlayState.PLAY);
                    ClingManager.getInstance().registerAVTransport(mContext);
                    ClingManager.getInstance().registerRenderingControl(mContext);
                    btStop.post(new Runnable() {
                        @Override
                        public void run() {
                            getPositionInfo();
                        }
                    });
                }

                @Override
                public void fail(IResponse response) {
                }
            });
        } else {
            mClingPlayControl.play(new ControlCallback() {
                @Override
                public void success(IResponse response) {
                    mClingPlayControl.setCurrentState(DLANPlayState.PLAY);
                    btStop.post(new Runnable() {
                        @Override
                        public void run() {
                            getPositionInfo();
                        }
                    });
                }

                @Override
                public void fail(IResponse response) {
                }
            });
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        mClingPlayControl.pause(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                mClingPlayControl.setCurrentState(DLANPlayState.PAUSE);
            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "pause fail");
            }
        });
    }

    /**
     * 停止
     */
    private void stop() {
        mClingPlayControl.stop(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                mClingPlayControl.setCurrentState(DLANPlayState.STOP);
            }

            @Override
            public void fail(IResponse response) {
                Log.e(TAG, "stop fail");
            }
        });
    }

    /**
     * 获取播放进度
     */
    public void getPositionInfo() {
        if (mClingPlayControl.getCurrentState() == DLANPlayState.PLAY) {
            mClingPlayControl.getPositionInfo(new ControlReceiveCallback() {
                @Override
                public void receive(IResponse response) {
                    final ClingPositionResponse clingPositionResponse = (ClingPositionResponse) response;
                    Log.e(TAG, "百分比" + clingPositionResponse.getResponse().getElapsedPercent());
                    if (!seek && response != null && clingPositionResponse != null) {
                        sbProgress.setMax((int) clingPositionResponse.getResponse().getTrackDurationSeconds());
                        sbProgress.setProgress((int) clingPositionResponse.getResponse().getTrackElapsedSeconds());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvSelected.setText(clingPositionResponse.getResponse().getRelTime() + " / " + clingPositionResponse.getResponse().getTrackDuration());

                            }
                        });
                        if (clingPositionResponse.getResponse().getElapsedPercent() >= 99) {
                            stop();
                        }
                    }
                }

                @Override
                public void success(IResponse response) {
                    Log.e(TAG, "success");
                    btStop.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getPositionInfo();
                        }
                    }, 1000);
                }

                @Override
                public void fail(IResponse response) {

                }
            });
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e(TAG, "onProgressChanged");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStartTrackingTouch");
        seek = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "onStopTrackingTouch");
        seek = false;
        int id = seekBar.getId();
        switch (id) {
            case R.id.sb_progress:
                // 进度
                int currentProgress = seekBar.getProgress() * 1000;
                mClingPlayControl.seek(currentProgress, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "seek success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "seek fail");
                    }
                });
                break;
            case R.id.sb_volume:
                // 音量
                int currentVolume = seekBar.getProgress();
                mClingPlayControl.setVolume(currentVolume, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        Log.e(TAG, "volume success");
                    }

                    @Override
                    public void fail(IResponse response) {
                        Log.e(TAG, "volume fail");
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 接收状态改变信息
     */
    private class TransportStateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "Receive playback intent:" + action);
            if (AllCode.ACTION_PLAYING.equals(action)) {
                mClingPlayControl.setCurrentState(DLANPlayState.PLAY);
            } else if (AllCode.ACTION_PAUSED_PLAYBACK.equals(action)) {
                mClingPlayControl.setCurrentState(DLANPlayState.PAUSE);

            } else if (AllCode.ACTION_STOPPED.equals(action)) {
                mClingPlayControl.setCurrentState(DLANPlayState.STOP);

            } else if (AllCode.ACTION_TRANSITIONING.equals(action)) {
                showTaost("正在连接");
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mUpnpServiceConnection);
        unregisterReceiver(mTransportStateBroadcastReceiver);

        ClingManager.getInstance().destroy();
        ClingDeviceList.getInstance().destroy();
    }

    private void showTaost(String tip) {
        Toast.makeText(ProjectionActivity.this, tip, Toast.LENGTH_SHORT).show();
    }
}
