package com.example.kiemtragiuaky.service;



import com.example.kiemtragiuaky.model.DangNhapModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DangNhapService {
    @POST("/api/login/signin") // Địa chỉ của API trên Spring Boot
    Call<DangNhapModel> findByTaiKhoanAndMatKhau(@Query("username") String taiKhoan, @Query("password") String matKhau); // DataModel là lớp Java đại diện cho dữ liệu trả về từ API
    @POST("/api/login/signup") // Địa chỉ của API trên Spring Boot
    Call<Object> setAccount(@Body DangNhapModel dangNhapModel);
}
