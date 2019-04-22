package com.codercoral.electricitychart.base;

import android.app.Application;

import cn.bmob.v3.Bmob;


public class MyApplication  extends Application {
    public static boolean isDebug = true;
    public static String APP_NAME = "Electricity Chart";

    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        Bmob.initialize(this, "c80c01865a80121e07991434d2a3ecfd");
        super.onCreate();
        app = this;
    }
}
