package com.xqd.myapplication.upnp;

import android.support.annotation.Nullable;
import android.util.Log;
import com.xqd.myapplication.util.CommomUtils;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.avtransport.callback.*;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.PositionInfo;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.VideoItem;
import org.fourthline.cling.support.renderingcontrol.callback.GetVolume;
import org.fourthline.cling.support.renderingcontrol.callback.SetMute;
import org.fourthline.cling.support.renderingcontrol.callback.SetVolume;
import org.seamless.util.MimeType;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Cling 实现的控制方法
 */
public class ClingPlayControl implements IPlayControl {

    private static final String TAG = ClingPlayControl.class.getSimpleName();
    /**
     * 每次接收 500ms 延迟
     */
    private static final int RECEIVE_DELAY = 500;
    /**
     * 上次设置音量时间戳, 防抖动
     */
    private long mVolumeLastTime;
    /**
     * 当前状态
     */
    private @DLANPlayState.DLANPlayStates
    int mCurrentState = DLANPlayState.STOP;
    private static final String DIDL_LITE_FOOTER = "</DIDL-Lite>";
    private static final String DIDL_LITE_HEADER = "<?xml version=\"1.0\"?>" + "<DIDL-Lite " + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" " +
            "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" " +
            "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">";

    /**
     * 播放一个新片源
     * @param url   片源地址
     * @param callback
     */
    @Override
    public void playNew(final String url, final ControlCallback callback) {
        // 1、 停止当前播放视频
        stop(new ControlCallback() {
            @Override
            public void success(IResponse response) {
                // 2、设置 url
                setAVTransportURI(url, new ControlCallback() {
                    @Override
                    public void success(IResponse response) {
                        // 3、播放视频
                        play(callback);
                    }

                    @Override
                    public void fail(IResponse response) {
                        if (CommomUtils.isNotNull(callback)) {
                            callback.fail(response);
                        }
                    }
                });
            }

            @Override
            public void fail(IResponse response) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(response);
                }
            }
        });
    }

    /**
     * 播放
     * @param callback
     */
    @Override
    public void play(final ControlCallback callback) {
        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(new Play(avtService) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    /**
     * 暂停
     * @param callback
     */
    @Override
    public void pause(final ControlCallback callback) {
        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(new Pause(avtService) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    /**
     * 停止
     * @param callback
     */
    @Override
    public void stop(final ControlCallback callback) {
        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(new Stop(avtService) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    /**
     * 快进
     * @param pos   seek到的位置(单位:毫秒)
     * @param callback
     */
    @Override
    public void seek(int pos, final ControlCallback callback) {
        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        String time = CommomUtils.getStringTime(pos);
        controlPointImpl.execute(new Seek(avtService, time) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    /**
     * 设置音量
     * @param pos   音量值，最大为 100，最小为 0
     * @param callback
     */
    @Override
    public void setVolume(int pos, @Nullable final ControlCallback callback) {
        final Service rcService = ClingUtils.findServiceFromSelectedDevice(ClingManager.RENDERING_CONTROL_SERVICE);
        if (CommomUtils.isNull(rcService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > mVolumeLastTime + RECEIVE_DELAY) {
            controlPointImpl.execute(new SetVolume(rcService, pos) {

                @Override
                public void success(ActionInvocation invocation) {
                    if (CommomUtils.isNotNull(callback)) {
                        callback.success(new ClingResponse(invocation));
                    }
                }

                @Override
                public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                    if (CommomUtils.isNotNull(callback)) {
                        callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                    }
                }
            });
        }
        mVolumeLastTime = currentTimeMillis;
    }

    /**
     * 设置是否静音
     * @param desiredMute   是否静音
     * @param callback
     */
    @Override
    public void setMute(boolean desiredMute, @Nullable final ControlCallback callback) {
        final Service rcService = ClingUtils.findServiceFromSelectedDevice(ClingManager.RENDERING_CONTROL_SERVICE);
        if (CommomUtils.isNull(rcService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(new SetMute(rcService, desiredMute) {

            @Override
            public void success(ActionInvocation invocation) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }


    /**
     * 获取播放进度
     * @param callback
     */
    @Override
    public void getPositionInfo(final ControlReceiveCallback callback) {

        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        GetPositionInfo getPositionInfo = new GetPositionInfo(avtService) {
            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingPositionResponse(invocation, operation, defaultMsg));
                }
            }

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingPositionResponse(invocation));
                }
            }

            @Override
            public void received(ActionInvocation invocation, PositionInfo info) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.receive(new ClingPositionResponse(invocation, info));
                }
            }
        };

        ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(getPositionInfo);
    }

    /**
     * 获取音量
     * @param callback
     */
    @Override
    public void getVolume(final ControlReceiveCallback callback) {
        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.RENDERING_CONTROL_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }
        GetVolume getVolume = new GetVolume(avtService) {
            @Override
            public void received(ActionInvocation actionInvocation, int currentVolume) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.receive(new ClingVolumeResponse(actionInvocation, currentVolume));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
                    callback.fail(new ClingVolumeResponse(invocation, operation, defaultMsg));
                }
            }
        };

        ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(getVolume);
    }

    /**
     * 获取播放状态
     *
     * @return
     */
    public @DLANPlayState.DLANPlayStates
    int getCurrentState() {
        return mCurrentState;
    }

    /**
     * 设置播放状态
     *
     * @param currentState
     */
    public void setCurrentState(@DLANPlayState.DLANPlayStates int currentState) {
        if (this.mCurrentState != currentState) {
            this.mCurrentState = currentState;
        }
    }

    /**
     * 设置片源，用于首次播放
     *
     * @param url      片源地址
     * @param callback 回调
     */
    private void setAVTransportURI(String url, final ControlCallback callback) {
        if (CommomUtils.isNull(url)) {
            return;
        }

        String metadata = pushMediaToRender(url, "id", "name", "0");

        final Service avtService = ClingUtils.findServiceFromSelectedDevice(ClingManager.AV_TRANSPORT_SERVICE);
        if (CommomUtils.isNull(avtService)) {
            return;
        }

        final ControlPoint controlPointImpl = ClingUtils.getControlPoint();
        if (CommomUtils.isNull(controlPointImpl)) {
            return;
        }

        controlPointImpl.execute(new SetAVTransportURI(avtService, url, metadata) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (CommomUtils.isNotNull(callback)) {
                    callback.success(new ClingResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (CommomUtils.isNotNull(callback)) {
//                    callback.fail(new ClingResponse(invocation, operation, defaultMsg));
                    callback.success(new ClingResponse(invocation));
                }
            }
        });
    }


    private String pushMediaToRender(String url, String id, String name, String duration) {
        long size = 0;
        long bitrate = 0;
        Res res = new Res(new MimeType(ProtocolInfo.WILDCARD, ProtocolInfo.WILDCARD), size, url);

        String creator = "unknow";
        String resolution = "unknow";
        VideoItem videoItem = new VideoItem(id, "0", name, creator, res);

        String metadata = createItemMetadata(videoItem);
        Log.e(TAG, "metadata: " + metadata);
        return metadata;
    }

    private String createItemMetadata(DIDLObject item) {
        StringBuilder metadata = new StringBuilder();
        metadata.append(DIDL_LITE_HEADER);

        metadata.append(String.format("<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item.getId(), item.getParentID(), item.isRestricted() ? "1" : "0"));

        metadata.append(String.format("<dc:title>%s</dc:title>", item.getTitle()));
        String creator = item.getCreator();
        if (creator != null) {
            creator = creator.replaceAll("<", "_");
            creator = creator.replaceAll(">", "_");
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator));

        metadata.append(String.format("<upnp:class>%s</upnp:class>", item.getClazz().getValue()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        metadata.append(String.format("<dc:date>%s</dc:date>", time));

        // metadata.append(String.format("<upnp:album>%s</upnp:album>",
        // item.get);

        // <res protocolInfo="http-get:*:audio/mpeg:*"
        // resolution="640x478">http://192.168.1.104:8088/Music/07.我醒著做夢.mp3</res>

        Res res = item.getFirstResource();
        if (res != null) {
            // protocol info
            String protocolinfo = "";
            ProtocolInfo pi = res.getProtocolInfo();
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"", pi.getProtocol(), pi.getNetwork(), pi.getContentFormatMimeType(), pi
                        .getAdditionalInfo());
            }
            Log.e(TAG, "protocolinfo: " + protocolinfo);

            // resolution, extra info, not adding yet
            String resolution = "";
            if (res.getResolution() != null && res.getResolution().length() > 0) {
                resolution = String.format("resolution=\"%s\"", res.getResolution());
            }

            // duration
            String duration = "";
            if (res.getDuration() != null && res.getDuration().length() > 0) {
                duration = String.format("duration=\"%s\"", res.getDuration());
            }

            // res begin
            //            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration));

            // url
            String url = res.getValue();
            metadata.append(url);

            // res end
            metadata.append("</res>");
        }
        metadata.append("</item>");

        metadata.append(DIDL_LITE_FOOTER);

        return metadata.toString();
    }
}
