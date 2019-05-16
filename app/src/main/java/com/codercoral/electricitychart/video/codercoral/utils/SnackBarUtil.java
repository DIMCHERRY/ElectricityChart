package com.codercoral.electricitychart.video.codercoral.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtil {

    public static void show(View rootView, int textId) {
        Snackbar.make(rootView, textId, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }
}
