package com.hyzc.bee.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hy.bee.bottomnavigation.BottomNavigationBar;
import com.hy.bee.bottomnavigation.BottomNavigationItem;
import com.hyzc.bee.client.layout.analysis.ProductSelectActivity;
import com.hyzc.bee.client.layout.data.DataFragment;
import com.hyzc.bee.client.layout.personal.PersonalFragment;
import com.hyzc.bee.client.layout.store.Device;
import com.hyzc.bee.client.layout.store.Store;
import com.hyzc.bee.client.layout.store.StoreSelectActivity;
import com.hyzc.bee.client.util.BeeUtil;


import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private Context mContext;
    private DataFragment dataFragment;
    private com.hyzc.bee.client.layout.analysis.AnalysisFragment analysisFragment;
    private PersonalFragment personalFragment;


    @BindView(R.id.button_store)
    MaterialIconView buttonStore;
    @BindView(R.id.store_name)
    TextView storeName;

    @BindView(R.id.store_layout)
    LinearLayout storeLayout;


    @BindView(R.id.button_share)
    MaterialIconView shareBtn;
    @BindView(R.id.button_toggle)
    MaterialIconView toggleBtn;
    @BindView(R.id.layout_titlebar)
    RelativeLayout layoutTitleBar;

    private Store selectStore;
    private Device selectDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        storeName.setTypeface(BeeUtil.typefaceLatoRegular);
        mContext = this;

        BeeUtil.init(mContext);
        //showBackwardView(R.string.text_back, true);
        addBottomItems();
        setDefaultFragment();


        selectStore = Store.getDefaultStore();
        storeName.setText(selectStore.getName());
    }

    private void addBottomItems() {
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar.setBarBackgroundColor(R.color.nav_background);//背景颜色
        bottomNavigationBar.setInActiveColor(R.color.nav_gray);//未选中时的颜色
        bottomNavigationBar.setActiveColor(R.color.nav_active);//选中时的颜色
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "数据").setActiveColorResource(R.color.theme_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "分析").setActiveColorResource(R.color.theme_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "用户").setActiveColorResource(R.color.theme_color))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }
    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);
        dataFragment=new DataFragment();
        fragmentTransaction.add(R.id.bottom_nav_content,dataFragment);
        fragmentTransaction.show(dataFragment).commit();
    }

    /**
     * 隐藏fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (dataFragment != null) {
            transaction.hide(dataFragment);
        }
        if (analysisFragment != null) {
            transaction.hide(analysisFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (position){
            case 0:
                layoutTitleBar.setVisibility(LinearLayout.VISIBLE);
                if (dataFragment==null){
                    dataFragment=new DataFragment();
                    fragmentTransaction.add(R.id.bottom_nav_content,dataFragment);
                }else {
                    fragmentTransaction.show(dataFragment);
                }

                break;
            case 1:
                layoutTitleBar.setVisibility(LinearLayout.VISIBLE);
                if (analysisFragment==null){
                    analysisFragment=new com.hyzc.bee.client.layout.analysis.AnalysisFragment();
                    fragmentTransaction.add(R.id.bottom_nav_content,analysisFragment);
                }else {
                    fragmentTransaction.show(analysisFragment);
                }
                break;
            case 2:
                layoutTitleBar.setVisibility(LinearLayout.GONE);
                if (personalFragment==null){
                    personalFragment=new PersonalFragment();
                    fragmentTransaction.add(R.id.bottom_nav_content,personalFragment);
                }else {
                    fragmentTransaction.show(personalFragment);
                }

                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    @OnClick({R.id.button_toggle, R.id.button_share, R.id.store_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_toggle:
                BeeUtil.showToast(mContext, "切换数据");

                //Toast.makeText(getActivity(),"button_toggle",Toast.LENGTH_LONG).show();
                break;
            case R.id.button_share:
                BeeUtil.showToast(mContext, "分享");
                break;
            case R.id.store_layout:
                Intent intent = new Intent();
                //设置Intent的class属性，跳转到SecondActivity
                intent.setClass(mContext, StoreSelectActivity.class);
                //为intent添加额外的信息
                //intent.putExtra("useName", etx.getText().toString());
                //启动Activity
                Bundle bundle = new Bundle();
                bundle.putSerializable(StoreSelectActivity.SELECT_STORE_TAG, selectStore);
                bundle.putSerializable(StoreSelectActivity.SELECT_DEVICE_TAG, selectDevice);
                //启动Activity
                intent.putExtras(bundle);
                startActivityForResult(intent,RESULT_FIRST_USER);
                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                //getActivity().overridePendingTransition( R.anim.,R.anim.trans_left_in);
                //Toast.makeText(getActivity(),"store_area",Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras;
        switch (resultCode) {
            case 1001:
                extras = data.getExtras();
                if (resultCode == RESULT_OK) {
                    BeeUtil.showToast(mContext, "收到返回值了");
                }
                break;
            case 1002:
                extras = data.getExtras();
                String temp="";
                Object storeObj = extras.get(StoreSelectActivity.SELECT_STORE_TAG);
                if(storeObj!=null){
                    selectStore=(Store)storeObj;
                    temp=selectStore.getName();
                    Object deviceObj = extras.get(StoreSelectActivity.SELECT_DEVICE_TAG);
                    if(deviceObj!=null){
                        selectDevice=(Device)deviceObj;
                        temp+="-"+selectDevice.getName();
                    }else{
                        selectDevice=null;
                    }
                }else{
                    temp+="全部店铺";
                    selectStore=null;
                }

                storeName.setText(temp);
                break;
        }
    }

}
