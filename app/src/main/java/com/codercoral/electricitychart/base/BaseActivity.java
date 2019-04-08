package com.codercoral.electricitychart.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.codercoral.electricitychart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends Activity implements
        View.OnClickListener {

    protected Toolbar mToolbar;

    /**解绑防止内存泄漏**/
    private Unbinder mUnbinder;

    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = true;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = false;
    /** 是否输出日志信息 **/
    private boolean isDebug;
    private String APP_NAME;
    /**获取当前类名**/
    protected final String TAG = this.getClass().getSimpleName();
    /**请求码**/
    private static final int PERMISSION_REQUEST_CODE = 0;

    /**请求权限**/
    String[] mNeedPermissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDebug = MyApplication.isDebug;
        APP_NAME = MyApplication.APP_NAME;
        $Log(TAG + "-->onCreate()");
        try {
            Bundle bundle = getIntent().getExtras();
            initParms(bundle);
            if (mAllowFullScreen) {
                this.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
            if (isSetStatusBar) {
                steepStatusBar();
            }
            //绑定视图
            setContentView(bindLayout());
            mUnbinder = ButterKnife.bind(this);
            //检查权限
            checkPermissions(mNeedPermissions);
            if (!isAllowScreenRoate) {
                //"portrait":竖屏显示(高比宽要长)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            } else {
                //"landscape":横屏显示（宽比高要长）
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            //初始化布局
            initView();
            doBusiness(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [初始化Bundle参数]
     *
     * @param parms Bundle参数
     */
    public abstract void initParms(Bundle parms);

    /**
     * [绑定布局]
     *
     * @return 绑定布局的id
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     */
    public abstract void initView();

    /**
     * [业务操作]
     *
     * @param mContext 我的上下文
     */
    public abstract void doBusiness(Context mContext);

    /** View点击 **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }


    /**
     * [页面跳转]
     *
     * @param clz 跳转到的界面
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz 跳转到的界面
     * @param bundle 携带的参数
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls 跳转到的界面
     * @param bundle 携带的参数
     * @param requestCode 给定的requestCode调用onActivityResult（）方法
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        $Log(TAG + "--->onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        $Log(TAG + "--->onDestroy()");
        mUnbinder.unbind();
    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen 全屏参数
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar 沉浸状态栏参数
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate 屏幕旋转参数
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {
        if (isDebug) {
            Log.d(APP_NAME, msg);
        }
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * @param permissions
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList
                && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissionList.toArray(
                            new String[needRequestPermissionList.size()]),
                    PERMISSION_REQUEST_CODE);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 显示提示信息
     *
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                (dialog, which) -> finish());

        builder.setPositiveButton(R.string.setting,
                (dialog, which) -> startAppSettings());

        builder.setCancelable(false);

        builder.show();
    }


    /**
     * 启动应用的设置
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!verifyPermissions(grantResults)) {
                showMissingPermissionDialog();
            }
        }
    }

}