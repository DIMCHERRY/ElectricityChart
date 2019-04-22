package com.codercoral.electricitychart.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * Created by Charlie on 2016/9/23.
 * 对字符串类型的坐标轴标记进行格式化
 */
public class StringAxisValueFormatter extends ValueFormatter {

    //区域值
    private List<String> mStrs;

    /**
     * 对字符串类型的坐标轴标记进行格式化
     * @param strs
     */
    StringAxisValueFormatter(List<String> strs){
        this.mStrs =strs;
    }

    @Override
    public String getFormattedValue(float v) {
        return mStrs.get((int)v);
    }
}
