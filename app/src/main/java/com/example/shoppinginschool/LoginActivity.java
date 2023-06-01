package com.example.shoppinginschool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static final String KEY1="userName";
    private MyDBopenHelper myDBopenHelper;
    EditText myUserName,myPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("userinfo1", Context.MODE_PRIVATE);
        editor = preferences.edit();
        myDBopenHelper =new MyDBopenHelper(this,"GOODS_Database.db",null,1);

        //注册按钮
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText myUserName = findViewById(R.id.username);
                EditText myPassword = findViewById(R.id.password);
                String username = myUserName.getText().toString().trim();
                String password = myPassword.getText().toString().trim();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) ) {
                    //判断用户是否已经存在，若存在则提示已被注册
                    ArrayList<User> data = myDBopenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (username.equals(user.getName())) {
                            Toast.makeText(LoginActivity.this, "该用户已被注册", Toast.LENGTH_SHORT).show();
                            match = true;
                            break;
                        }
                    }
                    if (!match){
                        myDBopenHelper.add(username, password);
                        Toast.makeText(LoginActivity.this,  "恭喜你注册成功", Toast.LENGTH_SHORT).show();
                }
            }else {
                    Toast.makeText(LoginActivity.this, "请填写完整的注册信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //登录按钮
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myUserName = findViewById(R.id.username);
                myPassword = findViewById(R.id.password);
                //String name = preferences.getString(KEY1,"当前用户不存在");
                //String pass = preferences.getString(KEY2,"当前用户不存在");
                String name = myUserName.getText().toString().trim();
                String password = myPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = myDBopenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        editor.putString(KEY1,name);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}