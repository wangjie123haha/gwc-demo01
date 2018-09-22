package com.example.wangjie20180917.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by lenovo on 2018/9/17.
 */

public interface Api {

    @GET("getCarts?uid=71")
    Observable<ResponseBody> getrespinsebody();

}
