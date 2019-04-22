package com.codercoral.electricitychart;

import java.util.concurrent.Executors;

/**
 * Author:wuxianglong;
 * Time:2017/10/26.
 */
public class AppConfig {

    public static final String Transformer_Load_FLAG = "负载率";//负载率
    public static final String ANDROID_BASE_FLAG = "android_base";//android基础题
    public static final String ANDROID_SENIOR_FLAG = "android_senior";//android高级题
    public static final String JAVA_WEB_FLAG = "java_web";//JAVA WEB

    /**
     * 缓存路径
     */
    public static final String ANDROID_BASE_DIR = '/' + "androidBaseListCache";
    public static final String JAVA_QUESTION_DIR = '/' + "TransformerLoadRateCache";
    public static final String ANDROID_SENIOR_DIR = '/' + "androidSeniorListCache";
    public static final String JAVA_WEB_DIR = '/' + "javaWebListCache";

    public static final String SETTING_DIR = '/' + "setting";
}
