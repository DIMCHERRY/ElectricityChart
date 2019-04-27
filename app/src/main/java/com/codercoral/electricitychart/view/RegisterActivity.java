package com.codercoral.electricitychart.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codercoral.electricitychart.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends Activity {
    @BindView(R.id.et_register_username)
    EditText etUsername;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_register_psd)
    EditText etPsd;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.clean_email)
    ImageView cleanEmail;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.progress_register)
    ProgressBar progressRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_register)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_login:
                break;
        }
    }

    /**
     * 账号密码注册
     */
    public void register(){
        final String registerName = etUsername.getText().toString().trim();//账号
        final String registerPassword = etPsd.getText().toString().trim();//密码
        final String registerEmail = etEmail.getText().toString().trim();//邮箱
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(registerName);
        bmobUser.setPassword(registerPassword);
        bmobUser.setEmail(registerEmail);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),"恭喜，注册账号成功",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"register fail:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
