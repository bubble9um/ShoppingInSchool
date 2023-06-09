package com.example.shoppinginschool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBopenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public MyDBopenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE goods(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "goodsName TEXT NOT NULL," +
                "goodsImg TEXT," +
                "goodsDes TEXT," +
                "goodsPrice DOUBLE NOT NULL);");
        db.execSQL("CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userName TEXT UNIQUE," +
                "userPsd TEXT NOT NULL);");
        db.execSQL("CREATE TABLE admin(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "adminName TEXT UNIQUE," +
                "adminPsd TEXT NOT NULL);");
        db.execSQL("CREATE TABLE cart_recipe(" +
                "_id integer primary key autoincrement," +
                "recipeName TEXT," +
                "recipePrice DOUBLE," +
                "recipeImg TEXT," +
                "recipeDes TEXT," +
                "isCheck integer NOT NULL DEFAULT 0);");
        /*db.execSQL("CREATE TABLE orders(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderNum INTEGER NOT NULL," +
                "orderUser TEXT NOT NULL," +
                "CONSTRAINT fk_user" +
                "FOREIGN KEY (orderUser)," +
                "REFERENCE user(userName);");*/
        db.execSQL("INSERT INTO goods (goodsName, goodsPrice) VALUES ('乐事薯片',6.5)");
        db.execSQL("INSERT INTO goods (goodsName, goodsPrice) VALUES ('可口可乐',3.0)");
        db.execSQL("INSERT INTO goods (goodsName, goodsPrice) VALUES ('卫龙辣条',5.0)");
        db.execSQL("INSERT INTO goods (goodsName, goodsPrice) VALUES ('南孚电池',19.5)");
        db.execSQL("INSERT INTO goods (goodsName, goodsPrice) VALUES ('Type-C数据线',21.0)");

        db.execSQL("INSERT INTO user (userName, userPsd) VALUES ('user',123)");

        db.execSQL("INSERT INTO admin (adminName,adminPsd) VALUES ('admin',123)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
        _db.execSQL("DROP TABLE IF EXISTS goods");
        _db.execSQL("DROP TABLE IF EXISTS user");
        _db.execSQL("DROP TABLE IF EXISTS admin");
        onCreate(_db);
    }

    //自定义方法
    public void add(String name,String password){
        db.execSQL("INSERT INTO user (userName,userPsd) VALUES(?,?)",new Object[]{name,password});
    }
    public void update(String name,String password){
        db.execSQL("UPDATE user SET userPsd = ? WHERE userName = ?",new Object[]{password,name});
    }

    public ArrayList<User> getAllData(){
        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"userName DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range")
            String name = cursor.getString(cursor.getColumnIndex("userName"));
            @SuppressLint("Range")
            String password = cursor.getString(cursor.getColumnIndex("userPsd"));
            list.add(new User(name,password));
        }
        return list;
    }
    public ArrayList<Goods> getAllGoodsData(){
        ArrayList<Goods> list = new ArrayList<Goods>();
        Cursor cursor = db.query("goods",null,null,null,null,null,"goodsName");
        while(cursor.moveToNext()){
            String goodsName = cursor.getString(cursor.getColumnIndexOrThrow("goodsName"));
            String goodsImg = cursor.getString(cursor.getColumnIndexOrThrow("goodsImg"));
            String goodsPrice = cursor.getString(cursor.getColumnIndexOrThrow("goodsPrice"));
            String goodsDes = cursor.getString(cursor.getColumnIndexOrThrow("goodsDes"));
            list.add(new Goods(goodsImg,goodsName,goodsPrice,goodsDes));
        }
        return list;
    }

    public ArrayList<Cart> getAllCartData(){
        ArrayList<Cart> list = new ArrayList<Cart>();
        Cursor cursor = db.query("cart_recipe",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String recipeName = cursor.getString(cursor.getColumnIndexOrThrow("recipeName"));
            String recipeImg = cursor.getString(cursor.getColumnIndexOrThrow("recipeImg"));
            String recipePrice = cursor.getString(cursor.getColumnIndexOrThrow("recipePrice"));
            String recipeDes = cursor.getString(cursor.getColumnIndexOrThrow("recipeDes"));
            list.add(new Cart(recipeImg,recipeName,recipePrice,recipeDes));
        }
        return list;
    }

    public double getSumPrice() {
        double sumPrice = 0;
        //Cursor cursor = db.rawQuery("SELECT * FROM cart_recipe WHERE isCheck = 1",null);
        Cursor cursor = db.rawQuery("SELECT * FROM cart_recipe",null);
        while(cursor.moveToNext()){
            String recipePrice = cursor.getString(cursor.getColumnIndexOrThrow("recipePrice"));
            sumPrice += Double.parseDouble(recipePrice);
        }
        return sumPrice;
    }

    public void deleteAllCartData() {
        //db.execSQL("DELETE  FROM cart_recipe WHERE isCheck = 1");
        db.execSQL("DELETE  FROM cart_recipe");
    }
}
