package com.example.shoppinginschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private MyDBopenHelper myDBopenHelper;
    private SQLiteDatabase dbRead;
    private int position = 0;
    private Fragment GoodsFragment,CartFragment,PersonalFragment;
    private List<Fragment> fragarrList = new ArrayList<>();
    private RadioButton radio_one;
    private RadioButton radio_two;
    private RadioButton radio_three;
    private RadioGroup radio_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBopenHelper = new MyDBopenHelper(getApplicationContext(),"GOODS_Database.db",null,1);
        dbRead = myDBopenHelper.getReadableDatabase();
        initView();
        initData();
        initMove();
        initOnClick();
    }
    private void initView() {
        view_pager = findViewById(R.id.view_pager);
        radio_one = findViewById(R.id.radio_one);
        radio_two = findViewById(R.id.radio_two);
        radio_three = findViewById(R.id.radio_three);
        radio_group = findViewById(R.id.radio_group);
    }
    private void initData(){
        GoodsFragment = new GoodsFragment();
        CartFragment = new CartFragment();
        PersonalFragment = new PersonalFragment();
        fragarrList.add(GoodsFragment);
        fragarrList.add(CartFragment);
        fragarrList.add(PersonalFragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(this.getSupportFragmentManager(),fragarrList);
        view_pager.setAdapter(adapter);
        ((RadioButton)radio_group.getChildAt(position)).setChecked(true);
    }
    //滑动屏幕进行页面切换，图标变化
    private void initMove(){
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ((RadioButton)radio_group.getChildAt(position)).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //图片String类型转Bitmap
    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    //点击页面下方图标进行切换
    private void initOnClick(){
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int CheckId) {
                        if (CheckId == R.id.radio_one){
                        position = 0;
                        view_pager.setCurrentItem(position);
                        } else if (CheckId == R.id.radio_two) {
                            position = 1;
                            view_pager.setCurrentItem(position);
                        } else if (CheckId == R.id.radio_three) {
                            position = 2;
                            view_pager.setCurrentItem(position);
                        }
                        else {
                        position=0;
                        view_pager.setCurrentItem(position);}
            }
        });
    }
}