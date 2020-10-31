package com.developer.duongnguyen.appquanlisinhvien.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Class Này Dùng Để Khởi Tạo Và Cấu Hình Retrofit

    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(String baseURL)
    {
        Gson mGson = new GsonBuilder().setLenient().create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        return mRetrofit;
    }

}
