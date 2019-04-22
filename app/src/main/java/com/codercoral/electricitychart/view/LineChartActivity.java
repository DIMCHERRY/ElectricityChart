package com.codercoral.electricitychart.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.codercoral.electricitychart.R;
import com.codercoral.electricitychart.entity.TransformerLoadRate;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LineChartActivity extends Activity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_line_chart);
        setTitle("平均负载率折线图");
        initView();
        initData();
    }

    private void initView() {
        lineChart = findViewById(R.id.lineChart);
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
                    Log.d("LineBarActivity", "查询数据成功");
                } else {
                    Log.d("LineBarActivity", "查询数据失败"+e.getMessage());
                }
            }
        });
    }

    private void addData(List<TransformerLoadRate> list) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        menu.removeItem(R.id.actionToggleIcons);
        return true;
    }

    //顶部menu选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/AnotherBarActivity.java"));
                startActivity(i);
                break;
            }
        }
        return true;
    }
}
