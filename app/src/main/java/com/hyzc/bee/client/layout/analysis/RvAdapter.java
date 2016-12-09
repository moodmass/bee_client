package com.hyzc.bee.client.layout.analysis;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzc.bee.client.R;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LXM on 2016-12-5
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<HashMap<String, Object>> listDatas;


    public RvAdapter(Context context, List<HashMap<String, Object>> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
        mLayoutInflater = LayoutInflater.from(context);

    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        ListItemView listItemView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            // 实例化一个封装类ListItemView，并实例化它的两个域
            listItemView = new ListItemView();
            listItemView.rank_bg_icon = (MaterialIconView) itemView
                    .findViewById(R.id.rank_bg_icon);
            listItemView.amount_sales_tv = (TextView) itemView
                    .findViewById(R.id.amount_sales_tv);
            listItemView.amount_weight_tv = (TextView) itemView
                    .findViewById(R.id.amount_weight_tv);
            listItemView.product_name_tv = (TextView) itemView
                    .findViewById(R.id.product_name_tv);
            listItemView.product_rank_tv = (TextView) itemView
                    .findViewById(R.id.product_rank_tv);

        }
    }

    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.li_product_rank, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemView listItemView = ((NormalViewHolder) holder).listItemView;
        // 获取到mList中指定索引位置的资源
        HashMap<String, Object> objectHashMap = listDatas.get(position);

        // 将资源传递给ListItemView的两个域对象
        int rank = Integer.parseInt(objectHashMap.get("rank").toString());
        if (rank == 1) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_first));
        } else if (rank == 2) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_second));
        } else if (rank == 3) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_third));
        } else if (rank == 4 || rank == 5) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_normal));
        } else {
            listItemView.rank_bg_icon.setVisibility(View.INVISIBLE);
        }
        listItemView.amount_sales_tv.setText(objectHashMap.get("saleAmount").toString());
        listItemView.amount_weight_tv.setText(objectHashMap.get("saleWeight").toString());
        listItemView.product_name_tv.setText(objectHashMap.get("productName").toString());
        listItemView.product_rank_tv.setText(objectHashMap.get("rank").toString());
        // 返回convertView对象
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return listDatas == null ? 0 : listDatas.size();
    }
}
