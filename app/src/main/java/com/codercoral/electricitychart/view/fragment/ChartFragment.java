package com.codercoral.electricitychart.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.codercoral.electricitychart.R;
import com.codercoral.electricitychart.entity.TransformerLoadRate;
import com.codercoral.electricitychart.utils.MPChartHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChartFragment extends Fragment {
    private static final String Chart = "Chart";
    @BindView(R.id.chart1)
    BarChart chart;
    @BindView(R.id.lineTwoChart)
    LineChart lineTwoChart;
    @BindView(R.id.lineChart)
    LineChart lineChart;

    private int mPage;

    public static ChartFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(Chart, page);
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPage = getArguments().getInt(Chart);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        CalendarView mCalendar = root.findViewById(R.id.myCalendar);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("您选择的时间是：" + year + "年" + month + "月" + dayOfMonth + "日", "tag");
            }
        });

        ButterKnife.bind(this, root);
        chart.getDescription().setEnabled(false);

        //只显示60条
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置标签居中
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);
        initData();
        return root;
    }

    //返回查询到的负载率list
    private void initData() {
        BmobQuery<TransformerLoadRate> lostInfomationReqBmobQuery = new BmobQuery<>();
        lostInfomationReqBmobQuery.order("-updatedAt");//排序
        lostInfomationReqBmobQuery.findObjects(new FindListener<TransformerLoadRate>() {
            @Override
            public void done(List<TransformerLoadRate> list, BmobException e) {
                if (e == null) {
                    addData(list);
                    addData2(list);
                    addData3(list);
                    Log.d("AnotherBarActivity", "查询数据成功");
                } else {
                    Log.d("AnotherBarActivity", "查询数据失败");
                }
            }
        });

    }

    private void addData(List<TransformerLoadRate> list) {
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            //用户名字
            xAxisValues.add(list.get(i).getG());
            //最小负载率
            yAxisValues1.add((Float.parseFloat(list.get(i).getM())));
            //最大负载率
            yAxisValues2.add(Float.parseFloat(list.get(i).getK()));
        }
        MPChartHelper.setTwoBarChart(chart, xAxisValues, yAxisValues1, yAxisValues2, "柱状图1", "柱状图2");
    }

    //局部变量，不用修改x和y
    private void addData2(List<TransformerLoadRate> list) {
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            //用户名字
            xAxisValues.add(list.get(i).getG());
            //平均负载率
            yAxisValues.add((Float.parseFloat(list.get(i).getJ())));
        }
        MPChartHelper.setLineChart(lineChart, xAxisValues, yAxisValues, "平均负载率", true);
    }

    //局部变量，不用修改x和y
    private void addData3(List<TransformerLoadRate> list){
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        List<Float> yAxisValues3 = new ArrayList<>();
        for(int i=0;i<30;++i){
            xAxisValues.add(list.get(i).getG());
            yAxisValues1.add((Float.parseFloat(list.get(i).getJ())));
            //最小负载率
            yAxisValues2.add((Float.parseFloat(list.get(i).getM())));
            //最大负载率
            yAxisValues3.add(Float.parseFloat(list.get(i).getK()));
        }

        List<List<Float>> yAxisValues = new ArrayList<>();
        yAxisValues.add(yAxisValues1);
        yAxisValues.add(yAxisValues2);
        yAxisValues.add(yAxisValues3);

        List<String> titles = new ArrayList<>();
        titles.add("平均负载率");
        titles.add("最小负载率");
        titles.add("最大负载率");
        MPChartHelper.setLinesChart(lineTwoChart,xAxisValues,yAxisValues, titles,false,null);
    }
}
