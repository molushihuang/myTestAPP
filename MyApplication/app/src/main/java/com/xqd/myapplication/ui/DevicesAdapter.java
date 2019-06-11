package com.xqd.myapplication.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xqd.myapplication.R;
import com.xqd.myapplication.upnp.ClingDevice;

import java.util.List;

/**
 * Created by 谢邱东 on 2019/6/11 11:14.
 * NO bug
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {
    private Context mContext;
    private List<ClingDevice> mClingDevices;
    private OnItemClickListener mOnItemClicklistener;

    public OnItemClickListener getOnItemClicklistener() {
        return mOnItemClicklistener;
    }

    public void setOnItemClicklistener(OnItemClickListener onItemClicklistener) {
        mOnItemClicklistener = onItemClicklistener;
    }

    public DevicesAdapter(Context mContext, List<ClingDevice> mClingDevices) {
        this.mContext = mContext;
        this.mClingDevices = mClingDevices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setView(position, mClingDevices.get(position));
    }

    @Override
    public int getItemCount() {
        return mClingDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }

        public void setView(int position, final ClingDevice clingDevice) {
            tvName.setText(clingDevice.getDevice().getDetails().getFriendlyName());
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClicklistener != null) {
                        mOnItemClicklistener.click(clingDevice);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void click(ClingDevice clingDevice);
    }
}
