package com.hyzc.bee.client.layout.store;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzc.bee.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceViewHolder> {
    private LayoutInflater mInflater;
    private List<Device> deviceList=new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private DeviceViewHolder viewHolder;
    private int layoutPosition;
    private Device selectDevice;
    private boolean isFirstEnter=true;
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public DeviceRecyclerAdapter(Context context, List<Device> deviceList,Device selectDevice) {
        this.mInflater = LayoutInflater.from(context);
        Device device=new Device();
        device.setCode("-1");
        device.setName("全部设备");
        this.deviceList.add(0,device);
        this.deviceList.addAll(deviceList);
        this.selectDevice=selectDevice;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_store_layout, parent, false);
        //view.setBackgroundColor(Color.RED);
        viewHolder = new DeviceViewHolder(view);
        return viewHolder;
    }


    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, int position) {
        holder.deviceNameTv.setText(deviceList.get(position).getName());
        holder.itemView.setTag(deviceList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                isFirstEnter = false;
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), layoutPosition);
            }
        });
        //更改状态
        if(isFirstEnter) {
            if (selectDevice != null && deviceList.get(position).getCode().equals(selectDevice.getCode())) {
                holder.deviceNameTv.setBackgroundResource(R.drawable.store_bg_select);
                holder.deviceNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.theme_color));
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), position);
                isFirstEnter = false;
            }
        }else{
            //更改状态
            if (position == layoutPosition) {
                holder.deviceNameTv.setBackgroundResource(R.drawable.store_bg_select);
                holder.deviceNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.theme_color));
            } else {
                holder.deviceNameTv.setBackgroundResource(R.drawable.store_bg_unselect);
                holder.deviceNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.text_color_gray_9));
            }
        }




    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceNameTv;

        public DeviceViewHolder(View view) {
            super(view);
            deviceNameTv = (TextView) view.findViewById(R.id.store_device_name);
        }
    }

}
