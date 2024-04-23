package com.example.kiemtragiuaky.singleton;

import android.app.Application;

import retrofit2.Retrofit;

public class MyApplication extends Application {
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = RetrofitClient.getInstance();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
