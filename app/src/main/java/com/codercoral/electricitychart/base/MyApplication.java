package com.codercoral.electricitychart.base;

import android.app.Application;


public class MyApplication  extends Application {
    public static boolean isDebug = true;
    public static String APP_NAME = "Electricity Chart";

    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
