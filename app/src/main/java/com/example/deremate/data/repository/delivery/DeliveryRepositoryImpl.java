package com.example.deremate.data.repository.delivery;

import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DeliveryRepositoryImpl implements DeliveryRepository{

    private final ApiService apiService;

    @Inject
    public DeliveryRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getCurrentDeliveriesByStatus(String status, RepositoryCallback<List<DeliveryDTO>> callback, String token) {
        Call<List<DeliveryDTO>> call = apiService.getCurrentDeliveriesByStatus("Bearer " + token, status);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<DeliveryDTO>> call, Response<List<DeliveryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener entregas. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryDTO>> call, Throwable t) {
                callback.onError("Fallo al conectar: " + t.getMessage());
            }
        });
    }

}
