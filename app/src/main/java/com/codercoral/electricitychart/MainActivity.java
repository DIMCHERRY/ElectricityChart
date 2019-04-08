package com.codercoral.electricitychart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.codercoral.electricitychart.marker.DetailsMarkerView;
import com.codercoral.electricitychart.marker.PositionMarker;
import com.codercoral.electricitychart.marker.RoundMarker;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    MyLineChart mLineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLineChart = findViewById(R.id.chart);

        findViewById(R.id.btn_show).setOnClickListener(v -> {
            List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();
            for (ILineDataSet set : dataSets)
                set.setVisible(!set.isVisible());
            mLineChart.animateXY(500, 500);
            mLineChart.invalidate();
        });

        findViewById(R.id.btn_update).setOnClickListener(v -> {

            //1,准备要更换的数据
            List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < 12; i++)
                entries.add(new Entry(i, new Random().nextInt(300)));

            //2. 获取LineDataSet线条数据集
            List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();

            //是否存在
            if (dataSets != null && dataSets.size() > 0) {
                //直接更换数据源
                for (ILineDataSet set : dataSets) {
                    LineDataSet data = (LineDataSet) set;
                    data.setValues(entries);
                }
            } else {
                //重新生成LineDataSet线条数据集
                LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                dataSet.setDrawCircles(false);
                dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
                dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
                dataSet.setLineWidth(1f);//线条宽度
                LineData lineData = new LineData(dataSet);
                //是否绘制线条上的文字
                lineData.setDrawValues(false);
                mLineChart.setData(lineData);
            }
            //更新
            mLineChart.invalidate();
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据
                mLineChart.getLineData().clearValues();
                mLineChart.highlightValues(null);
                mLineChart.invalidate();
            }
        });


        findViewById(R.id.btn_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float scaleX = mLineChart.getScaleX();
                if (scaleX == 1)
                    mLineChart.zoomToCenter(5, 1f);
                else {
                    BarLineChartTouchListener barLineChartTouchListener = (BarLineChartTouchListener) mLineChart.getOnTouchListener();
                    barLineChartTouchListener.stopDeceleration();
                    mLineChart.fitScreen();
                }

                mLineChart.invalidate();
            }
        });

        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            entries.add(new Entry(i, new Random().nextInt(300)));

        //2.把数据赋值到你的线条
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        mLineChart.setScaleEnabled(false);

        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(false);
        rightAxis.setAxisMaximum(dataSet.getYMax() * 2);
        leftAxis.setAxisMaximum(dataSet.getYMax() * 2);
        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大x轴标签重绘
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(String.valueOf(i + 1).concat("月"));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        //透明化图例
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);
        //legend.setYOffset(-2);

        //点击图表坐标监听
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
                if (mLineChart.isMarkerAllNull()) {
                    //重新绑定覆盖物
                    createMakerView();
                    //并且手动高亮覆盖物
                    mLineChart.highlightValue(h);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        //创建覆盖物
        createMakerView();

        //3.chart设置数据
        LineData lineData = new LineData(dataSet);
        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh

    }

    /**
     * 创建覆盖物
     */
    public void createMakerView() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(this);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setPositionMarker(new PositionMarker(this));
        mLineChart.setRoundMarker(new RoundMarker(this));
    }
}
