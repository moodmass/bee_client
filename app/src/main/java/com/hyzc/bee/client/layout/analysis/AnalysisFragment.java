package com.hyzc.bee.client.layout.analysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.DateUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/29.
 */
public class AnalysisFragment extends Fragment {
    @BindView(R.id.close_update_time_icon)
    MaterialIconView closeUpdateTimeIcon;

    @BindView(R.id.last_update_time_layout)
    RelativeLayout lastUpdateTimeLayout;

    @BindView(R.id.analysis_layout)
    LinearLayout analysisLayout;

    private View analysisFrag;

    private Context context;

    @BindView(R.id.top_panel)
    RelativeLayout layoutTitlebar;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.list_product_rank)
    ListView listProductRank;


    @BindView(R.id.last_update_time_text)
    TextView lastUpdateTimeText;

    List<View> mViewList = new ArrayList<>();//Save each of the Page View
    List<String> mTabList = new ArrayList<>();//Save each of the Tabs'Title
    View weekFrag;
    View seasonFrag;
    View monthFrag;
    LayoutInflater mLayoutInflater;
    @BindView(R.id.tab_layouts)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.view_more_btn)
    Button viewMoreBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        analysisFrag= inflater.inflate(R.layout.fragment_analysis, container, false);
        context = analysisFrag.getContext();
        ButterKnife.bind(this, analysisFrag);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    //设置刷新时动画的颜色，可以设置4个
                                                    @Override
                                                    public void onRefresh() {
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                // TODO Auto-generated method stub
                                                                lastUpdateTimeText.setText(DateUtil.getCurrentTime());
                                                                swipeRefreshLayout.setRefreshing(false);
                                                            }
                                                        }, 1000);
                                                    }
                                                }
        );
        DecimalFormat df = new DecimalFormat(".##");
        double d = 1252.2563;
        String st = df.format(d);
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rank", i + 1);
            map.put("productName", "芒果产品" + i);
            map.put("saleAmount", df.format((float) (Math.random() * 1000) + 10000));
            map.put("saleWeight", df.format((float) (Math.random() * 100) + 1000));
            mylist.add(map);
        }

        SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), mylist,//数据来源
                R.layout.li_product_rank,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[]{"rank", "productName", "saleAmount", "saleWeight"},
                //ListItem的XML文件里面的两个TextView ID
                new int[]{R.id.product_rank_tv, R.id.product_name_tv, R.id.amount_sales_tv, R.id.amount_weight_tv});
        //添加并且显示

        MainListViewAdapter adapter = new MainListViewAdapter(getActivity(), mylist);
        listProductRank.setAdapter(adapter);


        mLayoutInflater = LayoutInflater.from(getActivity());
        //Add ViewList

        weekFrag = mLayoutInflater.inflate(R.layout.fragment_week_price, null);
        monthFrag = mLayoutInflater.inflate(R.layout.fragment_month_price, null);
        seasonFrag = mLayoutInflater.inflate(R.layout.fragment_season_price, null);
        mViewList.add(weekFrag);
        mViewList.add(monthFrag);
        mViewList.add(seasonFrag);

//Add TabList

        mTabList.add("周价格走势");
        mTabList.add("月价格走势");
        mTabList.add("季度价格走势");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置标签的模式,默认系统模式

        //set the click event of Tag and change current page into the seleceted one

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());//点击哪个就跳转哪个界面
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //Add the tag elements
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTabList.get(2)));
        //Bind the adapter with the mViewPager as well as mTablayout

        CustomPagerAdapter myAdapter = new CustomPagerAdapter(mViewList, mTabList);
        mViewPager.setAdapter(myAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //  mTabLayout.setTabsFromPagerAdapter(myAdapter);
        return analysisFrag;
    }

    @OnClick({R.id.close_update_time_icon,R.id.view_more_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_update_time_icon:
                analysisLayout.removeView(lastUpdateTimeLayout);

                layoutTitlebar.setBackgroundResource(R.drawable.background_with_shadow);
                //Toast.makeText(getActivity(),"button_toggle",Toast.LENGTH_LONG).show();
                break;
            case R.id.view_more_btn:
                Intent intent = new Intent();
                //设置Intent的class属性，跳转到SecondActivity
                intent.setClass(context, ProductRankActivity.class);

                //为intent添加额外的信息
                //intent.putExtra("useName", etx.getText().toString());
                //启动Activity
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出
               // getActivity().overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                break;
        }
    }
}
