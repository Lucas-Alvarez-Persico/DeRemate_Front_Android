package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.deremate.data.model.User;
import com.example.deremate.data.network.ApiService;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etContrasena;
    private Button btnRegistrar;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.et_nombre);
        etEmail = findViewById(R.id.et_email);
        etContrasena = findViewById(R.id.et_contrasena);
        btnRegistrar = findViewById(R.id.btn_registrar);
        Button btnVolver= findViewById(R.id.btn_volver);

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String email = etEmail.getText().toString();
            String contrasena = etContrasena.getText().toString();
            String role = "ADMIN";

            if (!nombre.isEmpty() && !email.isEmpty() && !contrasena.isEmpty()) {
                User newUser = new User (email, contrasena);
                newUser.setRole(role);
                registerUser(newUser);
                //Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(RegisterActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(User user) {
        apiService.registerMail(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Revisa tu correo.", Toast.LENGTH_SHORT).show();

                    // Devuelve resultado a MainActivity
                    Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
                    intent.putExtra("username", user.getUsername()); // Pasar el email ingresado por el usuario
                    startActivity(intent);
                    finish(); // Cierra RegisterActivity
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("REGISTER_ERROR", t.getMessage());
            }
        });
    }
}
