package com.hyzc.bee.client.layout.analysis;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyzc.bee.client.R;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 定义ListView适配器MainListViewAdapter
 */
class MainListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, Object>> listDatas;


    public MainListViewAdapter(Context context, ArrayList<HashMap<String, Object>> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    /**
     * 返回item的个数
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listDatas.size();
    }

    /**
     * 返回item的内容
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listDatas.get(position);
    }

    /**
     * 返回item的id
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * 返回item的视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView;
        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.li_product_rank, null);

            // 实例化一个封装类ListItemView，并实例化它的两个域
            listItemView = new ListItemView();
            listItemView.rank_bg_icon = (MaterialIconView) convertView
                    .findViewById(R.id.rank_bg_icon);
            listItemView.amount_sales_tv = (TextView) convertView
                    .findViewById(R.id.amount_sales_tv);
            listItemView.amount_weight_tv = (TextView) convertView
                    .findViewById(R.id.amount_weight_tv);
            listItemView.product_name_tv = (TextView) convertView
                    .findViewById(R.id.product_name_tv);
            listItemView.product_rank_tv = (TextView) convertView
                    .findViewById(R.id.product_rank_tv);

            // 将ListItemView对象传递给convertView
            convertView.setTag(listItemView);
        } else {
            // 从converView中获取ListItemView对象
            listItemView = (ListItemView) convertView.getTag();
        }
        // 获取到mList中指定索引位置的资源
        HashMap<String, Object> objectHashMap = listDatas.get(position);

        // 将资源传递给ListItemView的两个域对象
        if (position == 0) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_first));
        } else if (position == 1) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_second));
        } else if (position == 2) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_third));
        } else if (position == 3||position == 4) {
            listItemView.rank_bg_icon.setColor(ContextCompat.getColor(context, R.color.product_rank_normal));
        }else{
            listItemView.rank_bg_icon.setVisibility(View.INVISIBLE);
        }
        listItemView.amount_sales_tv.setText(objectHashMap.get("saleAmount").toString());
        listItemView.amount_weight_tv.setText(objectHashMap.get("saleWeight").toString());
        listItemView.product_name_tv.setText(objectHashMap.get("productName").toString());
        listItemView.product_rank_tv.setText(objectHashMap.get("rank").toString());
        // 返回convertView对象
        return convertView;
    }

}
