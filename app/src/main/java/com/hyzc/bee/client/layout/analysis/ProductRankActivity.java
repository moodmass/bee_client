package com.hyzc.bee.client.layout.analysis;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.BeeUtil;
import com.hyzc.bee.client.util.DateUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductRankActivity extends AppCompatActivity {

    @BindView(R.id.rank_type)
    Spinner rankTypeSpinner;
    @BindView(R.id.list_product_rank)
    RecyclerView listProductRank;
    RecyclerAdapterWithHF mAdapter;

    //支持下拉刷新的ViewGroup
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    List<HashMap<String, Object>> myList = new ArrayList<>();
    @BindView(R.id.go_back)
    MaterialIconView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_rank);
        ButterKnife.bind(this);
        myList = generateData();
        listProductRank.setLayoutManager(new LinearLayoutManager(this));
        ProductRankAdapter adapter = new ProductRankAdapter(this, myList);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        listProductRank.setAdapter(mAdapter);

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
               // mPtrFrame.autoRefresh();
                mAdapter.notifyDataSetChanged();
                mPtrFrame.setLoadMoreEnable(true);
                BeeUtil.showToast(ProductRankActivity.this, getResources().getString(R.string.product_last_update_time) + DateUtil.getCurrentTime());
            }
        }, 100);

        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                myList.clear();
                myList = generateData();
                //模拟联网 延迟更新列表
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();
                        mPtrFrame.setLoadMoreEnable(true);
                        BeeUtil.showToast(ProductRankActivity.this, getResources().getString(R.string.product_last_update_time) + DateUtil.getCurrentTime());
                    }
                }, 1000);


            }
        });
        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                //模拟联网延迟更新数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟数据
                        myList.add(combimeSingle(19));
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(false);
                    }
                }, 1000);
            }
        });

        rankTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] rankTypes = getResources().getStringArray(R.array.rank_types);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    private List<HashMap<String, Object>> generateData() {
        //生成动态数组，并且转载数据
        for (int i = 0; i < 15; i++) {
            myList.add(combimeSingle(i));
        }
        return myList;
    }

    private static HashMap<String, Object> combimeSingle(int rank) {
        DecimalFormat df = new DecimalFormat(".##");
        double d = 1252.2563;
        String st = df.format(d);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rank", rank + 1);
        map.put("productName", "芒果产品" + rank);
        map.put("saleAmount", df.format((float) (Math.random() * 1000) + 10000));
        map.put("saleWeight", df.format((float) (Math.random() * 100) + 1000));
        return map;
    }

    @OnClick(R.id.go_back)
    public void onClick() {
        finish();
    }
}
