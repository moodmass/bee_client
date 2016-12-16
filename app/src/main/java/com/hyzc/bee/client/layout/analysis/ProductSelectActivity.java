package com.hyzc.bee.client.layout.analysis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hyzc.bee.client.R;
import com.hyzc.bee.client.layout.common.ClearEditText;
import com.hyzc.bee.client.layout.common.PinyinComparator;
import com.hyzc.bee.client.layout.common.SideBar;
import com.hyzc.bee.client.layout.common.SortAdapter;
import com.hyzc.bee.client.layout.common.SortModel;
import com.hyzc.bee.client.util.BeeUtil;
import com.hyzc.bee.client.util.CharacterParser;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductSelectActivity extends Activity {
    @BindView(R.id.product_lv)
    ListView sortListView;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    @BindView(R.id.go_back)
    MaterialIconView goBack;
    @BindView(R.id.finish_select_product)
    Button finishSelectProduct;

    public static final String SELECT_PRODUCT_TAG="SELECTED_PRODUCT";
    /**
     * 显示字母的TextView
     */
    @BindView(R.id.dialog)
    TextView dialog;

    private SortAdapter adapter;
    @BindView(R.id.filter_edit)
    ClearEditText mClearEditText;
    private ArrayList<String> selectedProductList;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        selectedProductList = (ArrayList<String>)intent.getSerializableExtra(SELECT_PRODUCT_TAG);
        setContentView(R.layout.activity_product_select);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        //实例化汉字转拼音类  
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        //设置右侧触摸监听  
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置  
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                RadioButton rbProduct = (RadioButton) view.findViewById(R.id.product_checked_view);
                TextView tvTitle = (TextView) view.findViewById(R.id.product_title);
                SortModel item = (SortModel) adapter.getItem(position);
                if (!rbProduct.isChecked()) {
                    if (selectedProductList.size() >= 3) {
                        rbProduct.setChecked(false);
                        BeeUtil.showToast(ProductSelectActivity.this, getString(R.string.chose_three_product));
                        return;
                    }
                    selectedProductList.add(item.getCode());
                    rbProduct.setChecked(true);
                } else {
                    selectedProductList.remove(item.getCode());
                    rbProduct.setChecked(false);
                }
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.product_data));

        // 根据a-z进行排序源数据  
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList, selectedProductList);
        sortListView.setAdapter(adapter);


        //根据输入框输入值的改变来过滤搜索  
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            sortModel.setCode(String.valueOf(i));
            //汉字转换成拼音  
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母  
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序  
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @OnClick({R.id.go_back, R.id.finish_select_product})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                finish();
                break;
            case R.id.finish_select_product:
                Intent intent = new Intent();
                //intent.putStringArrayListExtra("selectedProduct", selectedProductList);
               // intent.putExtra("selectedProduct", selectedProductList.toArray());
                Bundle bundle = new Bundle();
                bundle.putSerializable(SELECT_PRODUCT_TAG, selectedProductList);
                intent.putExtras(bundle);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1001, intent);
                //    结束当前这个Activity对象的生命
                finish();
                break;
        }
    }
}