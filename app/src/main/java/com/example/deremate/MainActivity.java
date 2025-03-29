package com.example.deremate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.di.NetworkModule;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint // Anotación necesaria para Hilt
public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService apiService; // Inyectar ApiService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText etUsuario = findViewById(R.id.et_usuario);
        EditText etContrasena = findViewById(R.id.et_contrasena);
        Button btnLogin = findViewById(R.id.btn_login2);

        btnLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contrasena = etContrasena.getText().toString();

            if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                // Crear objeto User con las credenciales
                User user = new User(usuario, contrasena);

                // Llamar al método de login
                loginUser(user);
            } else {
                Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(User user) {
        apiService.login(user).enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Login exitoso
                    UserAuth userAuth = response.body();
                    Toast.makeText(MainActivity.this, "Bienvenido, " + userAuth.getAccess_token(), Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN_SUCCESS", "Token: " + userAuth.getRole());
                } else {
                    // Error de login
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                // Error de red o API
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_ERROR", t.getMessage());
            }
        });
    }
}
