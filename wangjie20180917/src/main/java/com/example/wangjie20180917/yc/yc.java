package com.example.wangjie20180917.yc;

import android.content.Context;
import android.util.Log;

/**
 * Created by lenovo on 2018/9/17.
 */

public class yc implements Thread.UncaughtExceptionHandler {
    private static final yc ourInstance = new yc();
    private Context context;
    public static yc getInstance() {
        return ourInstance;
    }

    private yc() {
    }
   public  void   my(Context context){
        //捕获异常
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context =context;
   }
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("aaa", "thread:" + thread);
    }
}
