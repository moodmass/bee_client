package com.hyzc.bee.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        addBottomItems();
    }

    private void addBottomItems(){
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "数据").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "分析").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "用户").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
