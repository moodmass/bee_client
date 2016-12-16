package com.hyzc.bee.client.layout.data;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.hyzc.bee.client.R;
import com.hyzc.bee.client.layout.record.SaleRecordActivity;
import com.hyzc.bee.client.util.BeeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_FIRST_USER;


/**
 * Created by Administrator on 2016/11/29.
 */
public class DataFragment extends Fragment {
    @BindView(R.id.view_record)
    TextView viewRecord;
    private View dataFrag;

    private List<String> dataList = new ArrayList<>();

    private Context context;

    @BindView(R.id.sale_amount)
    TextView saleAmount;

    private String[] datas = {"选项1", "选项2", "选项3", "选项4", "选项5"};
    @BindView(R.id.time_select_tv)
    TextView timeSelectTv;

    @BindView(R.id.history_chart)
    LineChart historyChart;

    @BindView(R.id.time_chart)
    LineChart timeChart;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFrag = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, dataFrag);

        saleAmount.setTypeface(BeeUtil.typefaceLatoLight);
        initView();
        initTimeChart();
        initHistoryChart();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

            }
        });
        context = dataFrag.getContext();

        return dataFrag;

    }

    private void initView() {

    }

    private void initHistoryChart() {
        historyChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

                // un-highlight values after the gesture is finished and no single-tap
                if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
                    timeChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                Log.i("LongPress", "Chart longpressed.");
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Log.i("DoubleTap", "Chart double-tapped.");
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Log.i("SingleTap", "Chart single-tapped." + me.toString());
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
            }
        });
        historyChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
                Log.i("LOWHIGH", "low: " + timeChart.getLowestVisibleX() + ", high: " + timeChart.getHighestVisibleX());
                Log.i("MIN MAX", "xmin: " + timeChart.getXChartMin() + ", xmax: " + timeChart.getXChartMax() + ", ymin: " + timeChart.getYChartMin() + ", ymax: " + timeChart.getYChartMax());
            }

            @Override
            public void onNothingSelected() {
                Log.i("Nothing selected", "Nothing selected.");
            }
        });
        historyChart.setViewPortOffsets(30, 0, 30, 100);
        historyChart.setDrawGridBackground(false);

        // no description text
        historyChart.getDescription().setEnabled(false);

        // enable touch gestures
        historyChart.setTouchEnabled(true);

        // enable scaling and dragging
        historyChart.setDragEnabled(true);
        historyChart.setScaleEnabled(true);
        historyChart.setScaleXEnabled(true);
        historyChart.setScaleYEnabled(false);


        // set an alternative background color
        historyChart.setBackgroundColor(Color.WHITE);


        MarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        //mv.setChartView(historyChart);
        //historyChart.setMarker(mv);
        XAxis xAxis = historyChart.getXAxis();
        xAxis.mLabelHeight = 10;
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setEnabled(true);
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine

        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(11f);
        xAxis.setLabelCount(12);
        xAxis.setGranularity(1f);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis leftAxis = historyChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);

        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        historyChart.getAxisRight().setEnabled(false);

        historyChart.getViewPortHandler().setMaximumScaleY(2f);
        historyChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setHistoryData(30, 5000);
        //timeChart.setVisibleXRange(5f,1f);

        // timeChart.centerViewTo(20, 50, YAxis.AxisDependency.LEFT);

        historyChart.animateX(1500);
        // timeChart.invalidate();
        historyChart.getLegend().setEnabled(false);

        historyChart.getAxisRight().setEnabled(false);
        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void initTimeChart() {
        timeChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

                // un-highlight values after the gesture is finished and no single-tap
                if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
                    timeChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                Log.i("LongPress", "Chart longpressed.");
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Log.i("DoubleTap", "Chart double-tapped.");
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Log.i("SingleTap", "Chart single-tapped." + me.toString());
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
            }
        });
        timeChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.i("Entry selected", e.toString());
                Log.i("LOWHIGH", "low: " + timeChart.getLowestVisibleX() + ", high: " + timeChart.getHighestVisibleX());
                Log.i("MIN MAX", "xmin: " + timeChart.getXChartMin() + ", xmax: " + timeChart.getXChartMax() + ", ymin: " + timeChart.getYChartMin() + ", ymax: " + timeChart.getYChartMax());
            }

            @Override
            public void onNothingSelected() {
                Log.i("Nothing selected", "Nothing selected.");
            }
        });
        timeChart.setViewPortOffsets(30, 0, 30, 100);
        timeChart.setDrawGridBackground(false);

        // no description text
        timeChart.getDescription().setEnabled(false);

        // enable touch gestures
        timeChart.setTouchEnabled(true);

        // enable scaling and dragging
        timeChart.setDragEnabled(true);
        timeChart.setScaleEnabled(true);
        timeChart.setScaleXEnabled(true);
        timeChart.setScaleYEnabled(false);


        // set an alternative background color
        timeChart.setBackgroundColor(Color.WHITE);


        MarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(timeChart);
        timeChart.setMarker(mv);
        XAxis xAxis = timeChart.getXAxis();
        xAxis.mLabelHeight = 7;
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setEnabled(true);
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine

        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(11f);
        xAxis.setLabelCount(7);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setGranularity(1f);


        YAxis leftAxis = timeChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(false);

        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        //   leftAxis.setAxisLineColor(Color.WHITE);

        leftAxis.setTextColor(Color.TRANSPARENT);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setXOffset(-135f);


        YAxis rightAxis = timeChart.getAxisRight();
        rightAxis.setDrawAxisLine(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(Color.TRANSPARENT);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setXOffset(-135f);

        timeChart.getAxisRight().setEnabled(false);

        timeChart.getViewPortHandler().setMaximumScaleY(2f);
        timeChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setTimeData(30, 5000);
        //timeChart.setVisibleXRange(5f,1f);

        // timeChart.centerViewTo(20, 50, YAxis.AxisDependency.LEFT);

        timeChart.animateX(1500);
        // timeChart.invalidate();
        timeChart.getLegend().setEnabled(false);

        timeChart.getAxisRight().setEnabled(false);
        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    @OnClick({R.id.time_select_tv,  R.id.view_record})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.time_select_tv:
                BeeUtil.showToast(context, "时间选择");
                // TODO: 2016/5/17 构建一个popupwindow的布局
                View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow, null);

                // TODO: 2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
                ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
                lsvMore.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datas));

                // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                PopupWindow window = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                // TODO: 2016/5/17 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);
                // TODO: 2016/5/17 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                // TODO: 2016/5/17 设置可以获取焦点
                window.setFocusable(true);
                // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // TODO：更新popupwindow的状态
                window.update();
                window.showAsDropDown(timeSelectTv, 0, 20);
                break;
            case R.id.view_record:{
                Intent it = new Intent(getActivity(), SaleRecordActivity.class);
                //设置Intent的class属性，跳转到SecondActivity
                Bundle bundle = new Bundle();
                bundle.putSerializable(SaleRecordActivity.SELECT_DATE_TIME, new Date());
                //启动Activity
                it.putExtras(bundle);
                startActivityForResult(it, RESULT_FIRST_USER);
                //设置切换动画，从右边进入，左边退出
                // getActivity().overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }

        }
    }


    private void setTimeData(int count, float range) {
        int color = ContextCompat.getColor(getActivity(), R.color.theme_color);
        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {

            float val = (float) (Math.random() * range) + 10000;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (timeChart.getData() != null &&
                timeChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) timeChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            timeChart.getData().notifyDataChanged();
            timeChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(values, null);

            set1.setLineWidth(1);
            //set1.setCircleRadius(3f);
            // set1.setDrawCircleHole(false);
            set1.setValueTextSize(9);
            set1.setDrawFilled(true);
            set1.setDrawValues(false);
            set1.setCubicIntensity(0.2f);
            set1.setFormLineWidth(1);
            // set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            // set1.setFormSize(15.f);

            set1.setDrawCircles(true);
            set1.setColor(color);
            set1.setLineWidth(1);
            set1.setCircleColor(color);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setHighLightColor(color);
            set1.setHighlightLineWidth(1f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setDrawHorizontalHighlightIndicator(false);


            final ArrayList<String> axisShowLable = getXAxisShowLable();
            XAxis xAxis = timeChart.getXAxis();
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return axisShowLable.get((int) value);
                }
            });
            //xAxis.setValueFormatter(new DayAxisValueFormatter(timeChart));

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_color);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(R.color.theme_color);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            timeChart.setData(data);
        }
    }

    private void setHistoryData(int count, float range) {
        int color = ContextCompat.getColor(getActivity(), R.color.theme_color);
        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {

            float val = (float) (Math.random() * range) + 10000;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (historyChart.getData() != null &&
                historyChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) historyChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            historyChart.getData().notifyDataChanged();
            historyChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(values, null);

            set1.setLineWidth(1);
            //set1.setCircleRadius(3f);
            // set1.setDrawCircleHole(false);
            set1.setValueTextSize(10f);
            set1.setDrawFilled(false);
            set1.setDrawValues(true);
            set1.setCubicIntensity(0.2f);
            set1.setFormLineWidth(1);
            // set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            // set1.setFormSize(15.f);
            set1.setDrawCircles(true);
            set1.setColor(color);
            set1.setLineWidth(1.5f);
            int whiteColor = ContextCompat.getColor(getActivity(), R.color.nav_gray);
            set1.setCircleColor(whiteColor);
            set1.setCircleRadius(4f);
            set1.setCircleHoleRadius(3f);
            set1.setHighLightColor(color);
            set1.setHighlightLineWidth(1f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setMode(LineDataSet.Mode.LINEAR);

            final ArrayList<String> axisShowLable = getHistoryXAxisShowLable();
            XAxis xAxis = historyChart.getXAxis();
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return axisShowLable.get((int) value);
                }
            });
            //xAxis.setValueFormatter(new DayAxisValueFormatter(timeChart));


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            historyChart.setData(data);
        }
    }

    private ArrayList<String> getXAxisShowLable() {
        ArrayList<String> m = new ArrayList<String>();
        m.add("1月");
        m.add("2月");
        m.add("3月");
        m.add("4月");
        m.add("5月");
        m.add("6月");
        m.add("7月");
        m.add("8月");
        m.add("9月");
        m.add("10月");
        m.add("11月");
        m.add("12月");
        return m;
    }

    private ArrayList<String> getHistoryXAxisShowLable() {
        ArrayList<String> m = new ArrayList<String>();
        m.add("11.25");
        m.add("11.26");
        m.add("11.27");
        m.add("11.28");
        m.add("11.29");
        m.add("11.30");
        m.add("12.1");
        m.add("12.2");
        m.add("12.3");
        m.add("12.4");
        m.add("12.5");
        m.add("12.6");
        return m;
    }

}
