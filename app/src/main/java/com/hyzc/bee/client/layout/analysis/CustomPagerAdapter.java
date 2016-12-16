package com.hyzc.bee.client.layout.analysis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mViewList;
    private List<String>mTabList;
    public CustomPagerAdapter(FragmentManager fm, List<Fragment>viewList, List<String>tabList)
    {
        super(fm);
        this.mTabList = tabList;
        this.mViewList = viewList;
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }
}