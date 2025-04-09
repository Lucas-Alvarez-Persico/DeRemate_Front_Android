package com.example.deremate.data.network;

import com.example.deremate.data.model.Order;
import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.model.VerifyCode;
import com.example.deremate.data.model.DeliveryDTO;

import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("order")
    Call<List<Order>> getAllOrders(@Header("Authorization") String token);

    // NUEVOS ENDPOINTS PARA DELIVERY
    @GET("delivery/{status}")
    Call<List<DeliveryDTO>> getCurrentDeliveriesByStatus(@Header("Authorization") String token, @Path("status") String status);

    @PUT("delivery/{deliveryId}")
    Call<String> assignDelivery(@Header("Authorization") String token, @Path("deliveryId") Long deliveryId);

    @PUT("delivery/completed/{deliveryId}")
    Call<String> completeDelivery(@Header("Authorization") String token, @Path("deliveryId") Long deliveryId);
}
