package com.codercoral.electricitychart.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codercoral.electricitychart.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements View.OnClickListener {

    ImageView logo;
    EditText etUsername;
    ImageView ivCleanPhone;
    RelativeLayout rlPhone;
    EditText etPsd;
    ImageView cleanPassword;
    ImageView ivShowPwd;
    RelativeLayout rlPsd;
    TextView tvRegister;
    Button btnLogin;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pb = findViewById(R.id.pb);
        etUsername = findViewById(R.id.et_username);
        etPsd = findViewById(R.id.et_psd);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
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

        new Handler().postDelayed(() -> {
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
                        Log.d(" success","yeah");
                        finish();
                    } else {
                        Log.d("not success",e.getMessage());
                        Toast.makeText(getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        hiddenProgressBar();//隐藏
                    }
                }
            });
        }, 1000);

    }

    private void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    private void hiddenProgressBar() {
        pb.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
        }

    }
}
