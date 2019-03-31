package com.codercoral.electricitychart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartTest extends Activity {
    @BindView(R.id.chart)
    LineChart chart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        chart = new LineChart(this);
    }
}
