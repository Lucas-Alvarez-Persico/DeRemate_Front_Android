package com.example.deremate.data.repository.order;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.deremate.LoginActivity;
import com.example.deremate.MainActivity;
import com.example.deremate.data.model.Order;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class OrderRepositoryImpl implements OrderRepository {

    private final ApiService apiService;
    @Inject
    public OrderRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getAllOrders(RepositoryCallback<List<Order>> callback, String token){
        Log.e("TOKEN_DEBUG", "Token enviado: Bearer " + token);
        apiService.getAllOrders("Bearer " + token).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.e("ENTRE?","hola?");
                Log.e("aver",response.toString());
                if (response.isSuccessful() && response.body() != null){
                    List<Order> results = response.body();
                    callback.onSuccess(results);
                }
                else{
                    callback.onError("No se pudieron obtener las ordenes");
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }



}
