package com.rememberdev.tirtaagung.retrofit;

import com.rememberdev.tirtaagung.model.Request;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MethodHTTP {

    //get spesiffic using username and password
    @GET("tirtaagung/Login.php?")
    Call<UserResponse> login(@Query("email") String email, @Query("password") String password);

    //submit user registration
    @POST("tirtaagung/User_Registration.php")
    Call<Request> sendUser(@Body User user);
}
