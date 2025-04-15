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

import com.example.deremate.data.network.ApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class RecoverPasswordActivity extends AppCompatActivity {

    private EditText etUsuario;
    private Button btnEnviar;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private View darkOverlay;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        etUsuario = findViewById(R.id.et_usuario);
        btnEnviar = findViewById(R.id.btn_enviar);
        ImageButton btnVolver= findViewById(R.id.btn_volver);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        darkOverlay = findViewById(R.id.dark_overlay);

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverPasswordActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnEnviar.setOnClickListener(v -> {
            String username = etUsuario.getText().toString().trim();

            if (!username.isEmpty()) {
                recoverPassword(username);
            } else {
                Toast.makeText(RecoverPasswordActivity.this, "Por favor, ingresa tu nombre de usuario.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean show) {
        darkOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        tvProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        btnEnviar.setEnabled(!show);
    }

    private void recoverPassword(String username) {
        // Mostrar el indicador de carga
        showLoading(true);

        apiService.recoverMail(username).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Ocultar el indicador de carga
                showLoading(false);

                if (response.isSuccessful()) {
                    Toast.makeText(RecoverPasswordActivity.this, "Correo de recuperación enviado. Revisa tu bandeja.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecoverPasswordActivity.this, VerifyRecoverCodeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RecoverPasswordActivity.this, "No se pudo enviar el correo. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Ocultar el indicador de carga
                showLoading(false);
                Toast.makeText(RecoverPasswordActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
