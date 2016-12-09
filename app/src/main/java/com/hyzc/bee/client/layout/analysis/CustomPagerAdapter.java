package com.hyzc.bee.client.layout.analysis;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private List<String>mTabList;
    public CustomPagerAdapter(List<View>viewList,List<String>tabList)
    {
        this.mTabList = tabList;
        this.mViewList = viewList;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.remove(position));
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }
}