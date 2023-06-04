package com.example.shoppinginschool;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private MyDBopenHelper myDBopenHelper;
    ImageView show_goods_img;
    Cursor cursor;
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
        cursor= db.rawQuery("select * from goods where goodsName = ?",new String[]{ke_name});
        //判断是否有数据
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String goodsDes = cursor.getString(cursor.getColumnIndexOrThrow("goodsDes"));
            String goodsImg = cursor.getString(cursor.getColumnIndexOrThrow("goodsImg"));
            String goodsPrice = cursor.getString(cursor.getColumnIndexOrThrow("goodsPrice"));
            show_goods_details.setText(goodsDes);
            show_goods_price.setText(goodsPrice+"元");
            show_goods_img.setImageBitmap(MainActivity.convertStringToIcon(goodsImg));
        }
        /* *
        *
        *点击返回imageview时，只能通过intent跳转，导致会重复开启Activity，设想用goBack调用onBackPressed方法，能够实现同虚拟按键的返回但购物车内数据不会更新
        *                               有待修改
        * */

        //ImageView 点击返回事件
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goBack();
                Intent intent = new Intent(GoodsDetailActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //添加到购物车
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据插入到cart_recipe表中
                db.execSQL("INSERT INTO cart_recipe (recipeName,recipeDes,recipeImg,recipePrice)VALUES(?,?,?,?)",
                        new String[]{show_goods_name.getText().toString(),show_goods_details.getText().toString(),
                                cursor.getString(cursor.getColumnIndexOrThrow("goodsImg")),cursor.getString(cursor.getColumnIndexOrThrow("goodsPrice"))
                                });
                Toast.makeText(GoodsDetailActivity.this,"已加入购物车",Toast.LENGTH_SHORT).show();
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
            //refreshData();
            super.onBackPressed();
        }
    }

   /*
   private void refreshData() {
        CartFragment cartFragment = new CartFragment();
        cartFragment.onCreateView(getLayoutInflater(),(ViewGroup) findViewById(R.id.cart_fragment),null);
    }
    */

}