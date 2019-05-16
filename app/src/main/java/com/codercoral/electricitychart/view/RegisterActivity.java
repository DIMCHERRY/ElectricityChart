package com.codercoral.electricitychart.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codercoral.electricitychart.R;

import androidx.annotation.Nullable;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends Activity  implements View.OnClickListener{
    EditText etUsername;
    ImageView ivCleanPhone;
    EditText etPsd;
    EditText etEmail;
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etUsername = findViewById(R.id.et_register_username);
        ivCleanPhone = findViewById(R.id.iv_clean_phone);
        etPsd = findViewById(R.id.et_register_psd);
        btnRegister = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.et_email);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
        }
    }
}
