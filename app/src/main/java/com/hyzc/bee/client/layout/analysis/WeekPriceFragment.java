package com.hyzc.bee.client.layout.analysis;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyzc.bee.client.R;
import com.hyzc.bee.client.layout.data.MyMarkerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekPriceFragment  extends Fragment {
    @BindView(R.id.week_line_chart)
    LineChart weekLineChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View weekPriceFrag = inflater.inflate(R.layout.fragment_week_price, null);
        ButterKnife.bind(this, weekPriceFrag);

        initLineChart();
        //initTimeChart();
        return weekPriceFrag;
    }

    private void initLineChart() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;

        weekLineChart.getDescription().setText("单位:元/Kg");
        //  weekLineChart.getDescription().setEnabled(false);
        weekLineChart.getDescription().setPosition(width-20,80);
        weekLineChart.setDrawGridBackground(false);
        weekLineChart.setScaleXEnabled(true);
        weekLineChart.setScaleYEnabled(false);
        weekLineChart.setData(generateLineData());
        weekLineChart.animateX(500);
        MarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(weekLineChart);
        weekLineChart.setMarker(mv);
        weekLineChart.getAxisRight().setEnabled(false);
        weekLineChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        XAxis xAxis = weekLineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        final ArrayList<String> axisShowLable = getXAxisShowLable();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return axisShowLable.get((int) value);
            }
        });
        YAxis leftAxis = weekLineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setEnabled(true);
    }


    private ArrayList<String> getXAxisShowLable() {
        ArrayList<String> m = new ArrayList<String>();
        m.add("一");
        m.add("二");
        m.add("三");
        m.add("四");
        m.add("五");
        m.add("六");
        m.add("日");
        return m;
    }

    protected LineData generateLineData() {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        ArrayList<Entry> p1 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            float val = (float) (Math.random() * 10) + 100;
            p1.add(new Entry(i, val));
        }

        ArrayList<Entry> p2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            float val = (float) (Math.random() * 10) + 100;
            p2.add(new Entry(i, val));
        }

        ArrayList<Entry> p3 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            float val = (float) (Math.random() * 10) + 100;
            p3.add(new Entry(i, val));
        }

        LineDataSet ds1 = new LineDataSet(p1, "产品1");
        LineDataSet ds2 = new LineDataSet(p2, "产品2");
        LineDataSet ds3 = new LineDataSet(p3, "产品3");

        ds1.setDrawValues(false);
        ds2.setDrawValues(false);
        ds3.setDrawValues(false);

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);
        ds3.setLineWidth(2f);


        //ds1.setHighLightColor(color);
        ds1.setHighlightLineWidth(1f);
        ds1.enableDashedHighlightLine(10f, 5f, 0f);
        ds1.setDrawHorizontalHighlightIndicator(false);

        ds2.setHighlightLineWidth(1f);
        ds2.enableDashedHighlightLine(10f, 5f, 0f);
        ds2.setDrawHorizontalHighlightIndicator(false);

        ds3.setHighlightLineWidth(1f);
        ds3.enableDashedHighlightLine(10f, 5f, 0f);
        ds3.setDrawHorizontalHighlightIndicator(false);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);
        ds3.setDrawCircles(false);

        ds1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        ds2.setColor(ColorTemplate.COLORFUL_COLORS[1]);
        ds3.setColor(ColorTemplate.COLORFUL_COLORS[2]);
        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);
        sets.add(ds3);
        LineData d = new LineData(sets);
      //  d.setValueTypeface(tf);
        return d;
    }


}
