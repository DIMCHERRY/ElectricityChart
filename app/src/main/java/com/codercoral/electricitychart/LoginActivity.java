package com.codercoral.electricitychart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codercoral.electricitychart.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.rl_psd)
    RelativeLayout rlPsd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.pb)
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "c80c01865a80121e07991434d2a3ecfd");
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    @OnClick({R.id.tv_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    public void login() {
        final String accountName = etUsername.getText().toString().trim();//账号
        final String accountPassword = etPsd.getText().toString().trim();//密码

        if (TextUtils.isEmpty(accountName)) {
            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(accountPassword)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //登录过程
        showProgressBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = new BmobUser();
                bmobUser.setUsername(accountName);
                bmobUser.setPassword(accountPassword);
                bmobUser.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            //登录成功后进入主界面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            hiddenProgressBar();//隐藏
                        }
                    }
                });
            }
        }, 1000);

    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hiddenProgressBar() {
        pb.setVisibility(View.GONE);
    }



}
