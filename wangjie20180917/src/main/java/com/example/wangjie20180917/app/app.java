package com.example.wangjie20180917.app;

import android.app.Application;

import com.example.wangjie20180917.yc.yc;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by lenovo on 2018/9/17.
 */

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
       yc.getInstance().my(this);

    }
}
