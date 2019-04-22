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
import com.codercoral.electricitychart.utils.MPChartHelper;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MultiLineChartActivity extends Activity {

    private LineChart lineChart;
    private List<String> xAxisValues;
    private List<String> titles;
    private List<List<Float>> yAxisValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_line_chart);
        setTitle("高低平均负载率折线图");
        initView();
        initData();

    }

    private void initView(){
        lineChart= findViewById(R.id.lineChart);
    }

    private void initData(){


        BmobQuery<TransformerLoadRate> lostInfomationReqBmobQuery = new BmobQuery<>();
        lostInfomationReqBmobQuery.order("-updatedAt");//排序
        lostInfomationReqBmobQuery.findObjects(new FindListener<TransformerLoadRate>() {
            @Override
            public void done(List<TransformerLoadRate> list, BmobException e) {
                if (e == null) {
                    addData(list);
                    Log.d("AnotherBarActivity", "查询数据成功");
                } else {
                    Log.d("AnotherBarActivity", "查询数据失败"+e.getMessage());
                }
            }
        });
    }

    public void addData(List<TransformerLoadRate> list){
        xAxisValues = new ArrayList<>();
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

        yAxisValues = new ArrayList<>();
        yAxisValues.add(yAxisValues1);
        yAxisValues.add(yAxisValues2);
        yAxisValues.add(yAxisValues3);

        titles = new ArrayList<>();
        titles.add("平均负载率");
        titles.add("最小负载率");
        titles.add("最大负载率");
        MPChartHelper.setLinesChart(lineChart,xAxisValues,yAxisValues,titles,false,null);
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
