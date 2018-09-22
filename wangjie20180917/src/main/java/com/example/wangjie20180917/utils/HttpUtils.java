package com.example.wangjie20180917.utils;

import android.util.Log;

import com.example.wangjie20180917.api.Api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2018/9/17.
 */

public class HttpUtils {

    //成员
    private  static  HttpUtils httpUtils;

    private OkHttpClient okHttpClient;

    private  HttpUtils(){
        okHttpClient =new OkHttpClient.Builder()
                .addInterceptor(new longinterceptor())
                .build();
    }
    class  longinterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String method = request.method();
            HttpUrl url = request.url();
            Log.d("longinterceptor", method + "===" + url);
            Response response = chain.proceed(request);
            return response;
        }
    }
    //单例
    public  static  HttpUtils getinstance(){
        if (httpUtils==null){
            synchronized (HttpUtils.class){
                if (httpUtils==null){
                    httpUtils =new HttpUtils();
                }
            }
        }
        return  httpUtils;
    }
    //方法
   public Api getmsg(String base){
       Retrofit retrofit = new Retrofit.Builder()
               .client(okHttpClient)
               .baseUrl(base)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       Api api = retrofit.create(Api.class);
       return  api;
   }

}
