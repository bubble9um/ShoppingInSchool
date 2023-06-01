package com.example.shoppinginschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GoodsDetailActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private MyDBopenHelper myDBopenHelper;
    ImageView show_goods_img;
    private SQLiteDatabase db;
    Button add_to_cart;
    TextView show_goods_name,show_goods_details,show_goods_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        myDBopenHelper = new MyDBopenHelper(this,"GOODS_Database.db",null,1);
        db = myDBopenHelper.getReadableDatabase();
        findViewById();
        String ke_name  = getIntent().getStringExtra("goodsdata");
        show_goods_name.setText(ke_name);
        Cursor cursor= db.rawQuery("select * from goods where goodsName = ?",new String[]{ke_name});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String goodsDes = cursor.getString(cursor.getColumnIndexOrThrow("goodsDes"));
            String goodsImg = cursor.getString(cursor.getColumnIndexOrThrow("goodsImg"));
            String goodsPrice = cursor.getString(cursor.getColumnIndexOrThrow("goodsPrice"));
            show_goods_details.setText(goodsDes);
            show_goods_price.setText(goodsPrice+"元");
            show_goods_img.setImageBitmap(MainActivity.convertStringToIcon(goodsImg));
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void findViewById() {
        imageButton = findViewById(R.id.back_arrow);
        show_goods_name = findViewById(R.id.show_goods_name);
        show_goods_details = findViewById(R.id.show_goods_details);
        show_goods_price = findViewById(R.id.show_goods_price);
        show_goods_img = findViewById(R.id.show_goods_img);
        add_to_cart = findViewById(R.id.add_to_cart);
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

}