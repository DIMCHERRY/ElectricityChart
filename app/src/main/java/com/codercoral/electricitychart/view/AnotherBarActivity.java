
package com.codercoral.electricitychart.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.codercoral.electricitychart.R;
import com.codercoral.electricitychart.entity.TransformerLoadRate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AnotherBarActivity extends DemoBase {
    private BarChart chart;

    private List<String> xAxisValues;
    private List<Float> yAxisValues1;
    private List<Float> yAxisValues2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

        setTitle("负载率柱状图");

        chart = findViewById(R.id.chart1);

        chart.getDescription().setEnabled(false);

        //只显示60条
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //设置标签居中
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);
        Intent intent = new Intent(AnotherBarActivity.this, MyService.class);
        startService(intent);
        initData();
    }

    //返回查询到的负载率list
    private void initData() {
        xAxisValues = new ArrayList<>();
        yAxisValues1 = new ArrayList<>();
        yAxisValues2 = new ArrayList<>();

        BmobQuery<TransformerLoadRate> lostInfomationReqBmobQuery = new BmobQuery<>();
        lostInfomationReqBmobQuery.order("-updatedAt");//排序
        lostInfomationReqBmobQuery.findObjects(new FindListener<TransformerLoadRate>() {
            @Override
            public void done(List<TransformerLoadRate> list, BmobException e) {
                if (e == null) {
                    addData(list);
                    Log.d("AnotherBarActivity", "查询数据成功");
                } else {
                    Log.d("AnotherBarActivity", "查询数据失败");
                }
            }
        });

    }

    public void addData(List<TransformerLoadRate> list){
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
            case R.id.actionToggleValues: {

                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            /*
            case R.id.actionToggleIcons: { break; }
             */
            case R.id.actionToggleHighlight: {

                if (chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (chart.isPinchZoomEnabled())
                    chart.setPinchZoom(false);
                else
                    chart.setPinchZoom(true);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
                chart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                chart.invalidate();
                break;
            }
            case R.id.animateX: {
                chart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                chart.animateY(2000);
                break;
            }
            case R.id.animateXY: {

                chart.animateXY(2000, 2000);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "AnotherBarActivity");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AnotherBarActivity.this, MyService.class);
        stopService(intent);
    }
}
