package com.example.wangjie20180917.di;

import com.example.wangjie20180917.api.Api;
import com.example.wangjie20180917.constant.constant;
import com.example.wangjie20180917.utils.HttpUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by lenovo on 2018/9/17.
 */

public class moudleimp implements  icontract.imoudle {
    @Override
    public void requestdata(final callisten callisten) {
       //请求
        HttpUtils httpUtils = HttpUtils.getinstance();
        Api api = httpUtils.getmsg(constant.BASE_URL);
        //获取方法
        Observable<ResponseBody> observable = api.getrespinsebody();
        //获取
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        //回传
                        callisten.responsemsg(json);
                    }
                });
    }
}
