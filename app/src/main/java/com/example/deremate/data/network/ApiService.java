package com.example.deremate.data.network;
import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.model.VerifyCode;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/login")
    Call<UserAuth> login(@Body User user);

    @POST("user/register/mail")
    Call<String> registerMail(@Body User user);

    @POST("user/register")
    Call<UserAuth> verifyCode(@Body VerifyCode request);
    @POST("user/recover/mail")
    Call<String> recoverMail(@Body String username);

    @POST("user/recover")
    Call<String> recover(@Body VerifyCode request);

    @POST("user/newPassword")
    Call<Optional<User>> setNewPassword(@Header("Authorization") String token, @Body User user);
}
