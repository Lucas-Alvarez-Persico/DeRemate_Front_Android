package com.example.deremate.data.network;
import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/login")
    Call<UserAuth> login(@Body User user);

    @POST("register/mail")
    Call<String> RegisterMail(@Body User user);



}
