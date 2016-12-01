package com.hyzc.bee.client.layout.data;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.BeeUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/11/29.
 */
public class DataFragment extends Fragment {
    private View dataFrag;

    private List<String> dataList = new ArrayList<>();
    private Context context;


    @BindView(R.id.data_buttons)
    LinearLayout dataButtons;
    @BindView(R.id.button_store)
    MaterialIconView buttonStore;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.store_area)
    LinearLayout storeArea;


    @BindView(R.id.button_share)
    MaterialIconView shareBtn;
    @BindView(R.id.button_toggle)
    MaterialIconView toggleBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFrag = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, dataFrag);
        storeName.setTypeface(BeeUtil.typefaceLatoRegular);
        initView();
        showData();
        context = dataFrag.getContext();
        return dataFrag;
    }

    private void initView() {

    }


    @OnClick({R.id.button_toggle, R.id.button_share, R.id.store_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_toggle:
                Toast.makeText(getActivity(),"button_toggle",Toast.LENGTH_LONG).show();
                break;
            case R.id.button_share:
                Toast.makeText(getActivity(),"button_share",Toast.LENGTH_LONG).show();
                break;
            case R.id.store_area:
                Toast.makeText(getActivity(),"store_area",Toast.LENGTH_LONG).show();
                break;
        }
    }


    private void showData() {

        for (int i = 0; i < 20; i++) {
            dataList.add("内容"+i);
        }
        DataAdapter adapter = new DataAdapter(dataFrag.getContext(), dataList);
    }
}
