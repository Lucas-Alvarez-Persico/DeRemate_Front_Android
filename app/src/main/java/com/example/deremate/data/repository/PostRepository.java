package com.example.deremate.data.repository;

import com.example.deremate.data.model.Post;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private final ApiService apiService;

    @Inject
    public PostRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getPosts(RepositoryCallback<List<Post>> callback) {
        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
