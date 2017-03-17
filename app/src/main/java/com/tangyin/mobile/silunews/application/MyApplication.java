package com.tangyin.mobile.silunews.application;

import android.app.Application;

import com.tangyin.mobile.silunews.httputils.CommonURL;
import com.tangyin.mobile.silunews.service.APIService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mitchell on 2017/3/14.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private static APIService mApiService;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        initRetrofit();
    }


    /**
     * 初始化Retrofit网络框架
     */
    private void initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CommonURL.BASE_RUL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService=retrofit.create(APIService.class);
    }

    /**
     * 获取API
     * @return
     */
    public static APIService getApi() {
        return mApiService;
    }
}
