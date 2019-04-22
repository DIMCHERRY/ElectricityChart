package com.codercoral.electricitychart.view;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * 对字符串类型的坐标轴标记进行格式化
 */
public class StringAxisValueFormatter extends ValueFormatter {

    //区域值
    private List<String> mStrs;

    /**
     * 对字符串类型的坐标轴标记进行格式化
     * @param strs
     */
    public StringAxisValueFormatter(List<String> strs){
        this.mStrs =strs;
    }

    @Override
    public String getFormattedValue(float v) {
        return mStrs.get((int)v);
    }
}
