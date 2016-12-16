package com.hyzc.bee.client.layout.store;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.BeeUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.StoreViewHolder> {
    private LayoutInflater mInflater;
    private List<Store> storeList;
    private Store selectStore;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private StoreViewHolder viewHolder;
    private boolean isFirstEnter=true;
    private int layoutPosition;
    public StoreRecyclerAdapter(Context context, List<Store> storeList,Store selectStore) {
        this.mInflater = LayoutInflater.from(context);
        this.storeList = storeList;
        this.selectStore=selectStore;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_store_layout, parent, false);
        //view.setBackgroundColor(Color.RED);
        viewHolder = new StoreViewHolder(view);
        return viewHolder;
    }

    public Object getItem(int position) {
        return storeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final StoreViewHolder holder, int position) {
        holder.storeNameTv.setText(storeList.get(position).getName());
        holder.itemView.setTag(storeList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                isFirstEnter=false;
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), layoutPosition);
            }
        });

        //更改状态
        if(isFirstEnter){
            if(selectStore!=null&&storeList.get(position).getCode().equals(selectStore.getCode())){
                holder.storeNameTv.setBackgroundResource(R.drawable.store_bg_select);
                holder.storeNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.theme_color));
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), position);
                isFirstEnter=false;
            }
           // BeeUtil.showToast(mInflater.getContext(),isFirstEnter+"==="+selectStore);
        }else{
            if (position == layoutPosition) {
                holder.storeNameTv.setBackgroundResource(R.drawable.store_bg_select);
                holder.storeNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.theme_color));
            } else {
                holder.storeNameTv.setBackgroundResource(R.drawable.store_bg_unselect);
                holder.storeNameTv.setTextColor( mInflater.getContext().getResources().getColor(R.color.text_color_gray_9));
            }
        }
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        public TextView storeNameTv;

        public StoreViewHolder(View view) {
            super(view);
            storeNameTv = (TextView) view.findViewById(R.id.store_device_name);
        }
    }

}
