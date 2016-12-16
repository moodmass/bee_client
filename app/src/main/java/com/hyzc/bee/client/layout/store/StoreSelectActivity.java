package com.hyzc.bee.client.layout.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.util.BeeUtil;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hyzc.bee.client.layout.analysis.ProductSelectActivity.SELECT_PRODUCT_TAG;


public class StoreSelectActivity extends AppCompatActivity {

    public static final String SELECT_STORE_TAG = "SELECT_STORE";
    public static final String SELECT_DEVICE_TAG ="SELECT_DEVICE" ;
    @BindView(R.id.store_select_title)
    TextView storeSelectTitle;
    @BindView(R.id.go_back)
    MaterialIconView goBack;
    @BindView(R.id.store_rv)
    RecyclerView storeRv;
    @BindView(R.id.device_rv)
    RecyclerView deviceRv;
    @BindView(R.id.select_btn)
    Button selectBtn;
    private Context mContext;

    @BindView(R.id.device_layout)
    LinearLayout deviceLayout;

    private Store selectStore;
    private Device selectDevice;

    List<Store> stores;
   private  StaggeredGridLayoutManager staggeredGridLayoutManager;
   private  StoreRecyclerAdapter storeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        selectStore = (Store)intent.getSerializableExtra(SELECT_STORE_TAG);
        selectDevice = (Device)intent.getSerializableExtra(SELECT_DEVICE_TAG);

        setContentView(R.layout.activity_store_select);
        ButterKnife.bind(this);
        storeSelectTitle.setTypeface(BeeUtil.typefaceLatoRegular);
        stores = generateStoreData();
        mContext=this;
        staggeredGridLayoutManager=new  StaggeredGridLayoutManager(3,OrientationHelper.VERTICAL);
        staggeredGridLayoutManager.setAutoMeasureEnabled(true);
        storeRv.setLayoutManager(staggeredGridLayoutManager);
        //创建适配器，并且设置
        storeRecyclerAdapter= new StoreRecyclerAdapter(this,stores,selectStore);
        storeRv.setAdapter(storeRecyclerAdapter);

        storeRecyclerAdapter.setOnItemClickListener(new StoreRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                if(position!=0){
                    selectDevice=null;
                    // BeeUtil.showToast(StoreSelectActivity.this,stores.get(position).getName());
                    StaggeredGridLayoutManager staggeredGridLayoutManager=new  StaggeredGridLayoutManager(3,OrientationHelper.VERTICAL);
                    staggeredGridLayoutManager.setAutoMeasureEnabled(true);
                    deviceRv.setLayoutManager(staggeredGridLayoutManager);
                    //创建适配器，并且设置

                    selectStore = stores.get(position);
                    deviceLayout.setVisibility(LinearLayout.VISIBLE);
                    DeviceRecyclerAdapter  deviceRecyclerAdapter= new DeviceRecyclerAdapter(mContext,selectStore.getDeviceList(),selectDevice);
                    deviceRv.setAdapter(deviceRecyclerAdapter);
                    deviceRecyclerAdapter.setOnItemClickListener(new DeviceRecyclerAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data, int position) {
                            if(position!=0){
                                selectDevice = selectStore.getDeviceList().get(position-1);
                            }else{
                                selectDevice=null;

                            }
                        }
                    });
                }else{
                    deviceLayout.setVisibility(LinearLayout.INVISIBLE);
                    selectStore=stores.get(position);
                    selectDevice=null;
                }
            }
        });
   }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @OnClick({R.id.go_back, R.id.select_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                finish();
                break;
            case R.id.select_btn:
                Intent intent = new Intent();
                //intent.putStringArrayListExtra("selectedProduct", selectedProductList);
                // intent.putExtra("selectedProduct", selectedProductList.toArray());
                Bundle bundle = new Bundle();
                bundle.putSerializable(SELECT_STORE_TAG, selectStore);
                bundle.putSerializable(SELECT_DEVICE_TAG, selectDevice);
                intent.putExtras(bundle);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1002, intent);
                //    结束当前这个Activity对象的生命
                finish();
                break;
        }
    }


    private List<Store> generateStoreData() {
        //生成动态数组，并且转载数据
        List<Store> storeList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Store store = new Store();
            store.setCode(String.valueOf("code" + i));
            store.setName("店铺" + (i + 1));
            List<Device> deviceList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Device device = new Device();
                device.setCode("code" +i+j);
                device.setName("设备" + (i + 1)+(j+1));
                deviceList.add(device);
            }
            store.setDeviceList(deviceList);
            storeList.add(store);
        }

        storeList.add(0,Store.getDefaultStore());
        return storeList;
    }







}
