package com.hyzc.bee.client.layout.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyzc.bee.client.R;

import java.util.List;

/**
 * Created by lxm on 2016/8/24.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MsgViewHolder> {

    private Context context;
    private List<String> dataList;


    public DataAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        holder.content.setText(dataList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        public MsgViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.msg_content);
        }
    }
}
