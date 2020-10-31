package com.developer.duongnguyen.appquanlisinhvien.Retrofit;

public class ApiUtils {

    public static final String baseURL = "http://10.200.200.3/server_QLSV/API/";

    public static IApi getAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IApi.class);
    }
}
