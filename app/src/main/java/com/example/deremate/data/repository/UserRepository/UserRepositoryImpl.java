package com.example.deremate.data.repository.UserRepository;

import android.util.Log;

import com.example.deremate.data.model.UserDTO;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.utils.RepositoryCallback;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private final ApiService apiService;

    @Inject
    public UserRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getUser(RepositoryCallback<UserDTO> callback, String token) {
        // Log para verificar el token recibido
        Log.e("TOKEN_DEBUG", "Token enviado: Bearer " + token);

        // Realizar la llamada a la API para obtener el usuario
        apiService.getProfile("Bearer " + token).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa, pasamos el usuario al callback
                    UserDTO user = response.body();
                    callback.onSuccess(user);
                } else {
                    // En caso de error, pasamos el mensaje de error al callback
                    callback.onError("No se pudo obtener el perfil del usuario");
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Si hay un error de red o de conexión, pasamos el error al callback
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }
}
