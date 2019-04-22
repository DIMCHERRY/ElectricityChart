package com.codercoral.electricitychart.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter extends ValueFormatter {
    private DecimalFormat mFormat;
    public MyYAxisValueFormatter(){
        mFormat = new DecimalFormat("###,###,##"); // use one decimal
    }
    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " %";
    }
}
