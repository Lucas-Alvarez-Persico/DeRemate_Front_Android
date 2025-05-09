package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private ProgressBar progressBar;
    private TextView tvProgress;
    private View darkOverlay;

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
        ImageButton btnVolver= findViewById(R.id.btn_volver);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        darkOverlay = findViewById(R.id.rectangulo);


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
                User newUser = new User (email, nombre, contrasena);
                newUser.setRole(role);
                registerUser(newUser);
                //Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(RegisterActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showLoading(boolean show) {
        darkOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        tvProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        btnRegistrar.setEnabled(!show);
    }

    private void registerUser(User user) {
        showLoading(true);
        apiService.registerMail(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                showLoading(false);
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
                showLoading(false);
                Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("REGISTER_ERROR", t.getMessage());
            }
        });
    }
}
