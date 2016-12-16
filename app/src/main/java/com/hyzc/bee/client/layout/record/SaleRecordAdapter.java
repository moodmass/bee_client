package com.hyzc.bee.client.layout.record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzc.bee.client.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public class SaleRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<HashMap<String, Object>> listDatas;

    public SaleRecordAdapter(SaleRecordActivity context, List<HashMap<String, Object>> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        SaleRecordListItemView listItemView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            // 实例化一个封装类ListItemView，并实例化它的两个域
            listItemView = new SaleRecordListItemView();
            listItemView.amount_sales_tv = (TextView) itemView
                    .findViewById(R.id.amount_sales_tv);
            listItemView.amount_weight_tv = (TextView) itemView
                    .findViewById(R.id.amount_weight_tv);
            listItemView.product_name_tv = (TextView) itemView
                    .findViewById(R.id.product_name_tv);
            listItemView.sale_time_tv = (TextView) itemView
                    .findViewById(R.id.sale_time_tv);
            listItemView.date_tv = (TextView) itemView
                    .findViewById(R.id.date_tv);

        }
    }



    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public SaleRecordAdapter.NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SaleRecordAdapter.NormalViewHolder(mLayoutInflater.inflate(R.layout.li_sale_record, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder recyclerViewHolder = (NormalViewHolder) holder;
            SaleRecordListItemView listItemView = recyclerViewHolder.listItemView;
            HashMap<String, Object> objectHashMap = listDatas.get(position);
            listItemView.amount_sales_tv.setText(objectHashMap.get("saleAmount").toString());
            listItemView.amount_weight_tv.setText(objectHashMap.get("saleWeight").toString());
            listItemView.product_name_tv.setText(objectHashMap.get("productName").toString());
            listItemView.sale_time_tv.setText(objectHashMap.get("saleTime").toString());
            String saleDate = objectHashMap.get("saleDate").toString();
            if (position == 0) {
                listItemView.date_tv.setVisibility(View.VISIBLE);
                listItemView.date_tv.setText(saleDate);
                recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                String lastDate = listDatas.get(position - 1).get("saleDate").toString();
                if (!TextUtils.equals(saleDate, lastDate)) {
                    listItemView.date_tv.setVisibility(View.VISIBLE);
                    listItemView.date_tv.setText(saleDate);
                    recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    listItemView.date_tv.setVisibility(View.GONE);
                    recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }
}
