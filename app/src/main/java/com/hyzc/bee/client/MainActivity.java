package com.hyzc.bee.client;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hy.bee.bottomnavigation.BottomNavigationBar;
import com.hy.bee.bottomnavigation.BottomNavigationItem;
import com.hyzc.bee.client.layout.data.DataFragment;
import com.hyzc.bee.client.layout.personal.PersonalFragment;
import com.hyzc.bee.client.util.BeeUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private Context mContext;
    private DataFragment dataFragment;
    private com.hyzc.bee.client.layout.analysis.AnalysisFragment analysisFragment;
    private PersonalFragment personalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        BeeUtil.init(mContext);
        //showBackwardView(R.string.text_back, true);
        addBottomItems();
        setDefaultFragment();
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
                if (dataFragment==null){
                    dataFragment=new DataFragment();
                    fragmentTransaction.add(R.id.bottom_nav_content,dataFragment);
                }else {
                    fragmentTransaction.show(dataFragment);
                }
                break;
            case 1:
                if (analysisFragment==null){
                    analysisFragment=new com.hyzc.bee.client.layout.analysis.AnalysisFragment();
                    fragmentTransaction.add(R.id.bottom_nav_content,analysisFragment);

                }else {
                    fragmentTransaction.show(analysisFragment);
                }
                break;
            case 2:
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
}
