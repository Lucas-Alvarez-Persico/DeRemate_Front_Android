package com.example.deremate.data.network;
import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.model.VerifyCode;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/login")
    Call<UserAuth> login(@Body User user);

    @POST("user/register/mail")
    Call<String> registerMail(@Body User user);

    @POST("user/register")
    Call<UserAuth> verifyCode(@Body VerifyCode request);

}
