package com.developer.duongnguyen.appquanlisinhvien.Retrofit;

import com.developer.duongnguyen.appquanlisinhvien.Model.LichHocList;
import com.developer.duongnguyen.appquanlisinhvien.Model.MonHoc;
import com.developer.duongnguyen.appquanlisinhvien.Model.MonHocList;
import com.developer.duongnguyen.appquanlisinhvien.Model.SinhVien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApi {

    // interface này dùng để thực hiện các Request từ Client

    @FormUrlEncoded
    @POST("login.php")
    Call<List<SinhVien>> SinhVienLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("changepassword.php")
    Call<String> ChangePassword(@Field("email") String email, @Field("password") String password);

    @GET("monhoc.php")
    Call<MonHocList> GetMonHoc(@Query("id") String id);

    @GET("monhocfail.php")
    Call<MonHocList> GetMonHocFail(@Query("id") String id);

    @GET("lichhoc.php")
    Call<LichHocList> GetLichHoc(@Query("id") String id);

    @GET("search.php")
    Call<MonHocList> SearchMonHoc(@Query("tenmon") CharSequence tenmon , @Query("id") String id);

}
