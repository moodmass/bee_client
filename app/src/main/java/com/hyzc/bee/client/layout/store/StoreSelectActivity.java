package com.hyzc.bee.client.layout.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.BeeUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreSelectActivity extends AppCompatActivity {

    @BindView(R.id.back)
    MaterialIconView back;
    @BindView(R.id.store_select_title)
    TextView storeSelectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);
        ButterKnife.bind(this);
        storeSelectTitle.setTypeface(BeeUtil.typefaceLatoRegular);
    }


    @OnClick(R.id.back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

}
