package com.example.shoppinginschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePsdActivity extends AppCompatActivity {
    private ImageButton imageButton;
    SharedPreferences preferences;
    private String username1;
    EditText password_update;
    MyDBopenHelper myDBopenHelper;
    SQLiteDatabase db;
    android.widget.Button confrim,cancel;
    TextView username_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_psd);
        username_update = findViewById(R.id.username_update);
        imageButton = findViewById(R.id.back_arrow1);
        password_update = findViewById(R.id.password_update);
        confrim = findViewById(R.id.update_confirm);
        cancel = findViewById(R.id.cancel);
        myDBopenHelper = new MyDBopenHelper(this,"GOODS_Database.db",null,1);
        db = myDBopenHelper.getWritableDatabase();

        //确认更新按钮
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isempty =password_update.getText().toString().trim();
                if (! TextUtils.isEmpty(isempty)){
               myDBopenHelper.update(username1,password_update.getText().toString());
                //showFragmentToast("密码修改成功,下次登录变更");
                Toast.makeText(UpdatePsdActivity.this,"密码修改成功,下次登录变更",Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(UpdatePsdActivity.this,PersonalFragment.class);
                startActivity(intent);*/
                }
                else {
                    Toast.makeText(UpdatePsdActivity.this,"请输入正确的密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* password_update.setText("");
                Intent intent = new Intent(UpdatePsdActivity.this,PersonalFragment.class);
                startActivity(intent);
                finish();*/
                goBack();
            }
        });
        //返回按钮点击监听
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        //读取sharedpreferences中在登录时保存的用户名
        preferences = this.getSharedPreferences("userinfo1", Context.MODE_PRIVATE);
        username1 = preferences.getString("userName", " ");
        username_update.setText(username1);
    }
    //ImageView 点击返回事件
    private void goBack() {
        onBackPressed();
    }
    public void onBackPressed() {
        View currentView = getCurrentFocus();
        if (currentView != null && currentView.equals(imageButton)) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
    public void showFragmentToast(String text) {
        PersonalFragment.showToast(this, text);
    }
}