package com.codercoral.electricitychart.marker;

import android.content.Context;

import com.codercoral.electricitychart.R;
import com.github.mikephil.charting.components.MarkerView;

public class RoundMarker extends MarkerView {

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     * @param context
     */
    public RoundMarker(Context context) {
        super(context, R.layout.item_chart_round);
    }
}
