package com.hyzc.bee.client.layout.record;

import android.content.Intent;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hyzc.bee.client.util.DateUtil.DATEMM;

public class SaleRecordActivity extends AppCompatActivity {

    public static final String SELECT_DATE_TIME = "SELECTED_TIME";
    @BindView(R.id.go_back)
    MaterialIconView goBack;

    @BindView(R.id.list_product_rank)
    RecyclerView listProductRank;

    //支持下拉刷新的ViewGroup
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    List<HashMap<String, Object>> myList = new ArrayList<>();

    RecyclerAdapterWithHF mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_record);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Date date = (Date) intent.getSerializableExtra(SELECT_DATE_TIME);

        myList = generateData();
        listProductRank.setLayoutManager(new LinearLayoutManager(this));
        SaleRecordAdapter adapter = new SaleRecordAdapter(this, myList);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        listProductRank.setAdapter(mAdapter);

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mPtrFrame.autoRefresh();
                mPtrFrame.refreshComplete();
                mPtrFrame.setLoadMoreEnable(true);
                // BeeUtil.showToast(SaleRecordActivity.this, getResources().getString(R.string.product_last_update_time) + DateUtil.getCurrentTime());
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
                        BeeUtil.showToast(SaleRecordActivity.this, getResources().getString(R.string.product_last_update_time) + DateUtil.getCurrentTime());
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
                        combimeSingle(14);
                        combimeSingle(15);
                        combimeSingle(16);
                        combimeSingle(17);
                        combimeSingle(18);
                        combimeSingle(19);
                        combimeSingle(20);
                        combimeSingle(21);
                        combimeSingle(22);
                        combimeSingle(23);
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(false);
                    }
                }, 1000);
            }
        });
    }


    private List<HashMap<String, Object>> generateData() {
        //生成动态数组，并且转载数据
        for (int i = 1; i < 15; i++) {
            combimeSingle(i);
        }
        return myList;
    }

    private void combimeSingle(int rank) {

        Random random = new Random();

        for (int i = 0; i < random.nextInt(10); i++) {
            DecimalFormat df = new DecimalFormat(".##");
            double d = 1252.2563;
            String st = df.format(d);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rank", rank + 1);
            map.put("productName", "芒果产品" + random.nextInt(30));
            map.put("saleTime", new Date());
            Date date = new Date(System.currentTimeMillis() - DATEMM * 1000L * (rank)-(i*1032*4));
            String dateTimeStr = DateUtil.getDateTimeStr(date);
            String[] split = dateTimeStr.split(" ");
            map.put("saleTime", split[1]);
            map.put("saleDate", split[0]);
            map.put("unit", "Kg");
            map.put("saleAmount", df.format((float) (Math.random() * 1000) + 10000));
            map.put("saleWeight", df.format((float) (Math.random() * 100) + 1000));
            myList.add(map);
        }
    }

    @OnClick({R.id.go_back, R.id.list_product_rank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                finish();
                break;
            case R.id.list_product_rank:

                break;
        }
    }
}
