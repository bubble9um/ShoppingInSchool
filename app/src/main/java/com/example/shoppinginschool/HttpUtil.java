package com.example.shoppinginschool;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //通过okHttp框架的异步请求方式来完成数据的抓取
        //1.获得OkHttpClient类的实例
        OkHttpClient client = new OkHttpClient();

        //2.创建Request对象，并通过属性设置目标网络地址，请求方式等
        Request request = new Request.Builder()
                        .url(address)   //将第一个参数传入url属性
                        .build();
        //3.通过OkHttpClient类的实例调用newCall()方法来创建call对象，并把异步请求的结果送入回调接口
        client.newCall(request).enqueue(callback);

    }
}

