package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.data.model.VerifyCode;
import com.example.deremate.data.network.ApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class VerifyRecoverCodeActivity extends AppCompatActivity {
    @Inject
    ApiService apiService;

    private EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
    private Button btnVerificar;
    private String username; // Variable para guardar el email recibido
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        // Obtener las referencias a los EditText
        etCode1 = findViewById(R.id.digit1);
        etCode2 = findViewById(R.id.digit2);
        etCode3 = findViewById(R.id.digit3);
        etCode4 = findViewById(R.id.digit4);
        etCode5 = findViewById(R.id.digit5);
        etCode6 = findViewById(R.id.digit6);

        btnVerificar = findViewById(R.id.btn_verificar);
        username = getIntent().getStringExtra("username");

        btnVerificar.setOnClickListener(v -> {
            String codigo = etCode1.getText().toString() +
                    etCode2.getText().toString() +
                    etCode3.getText().toString() +
                    etCode4.getText().toString() +
                    etCode5.getText().toString() +
                    etCode6.getText().toString();

            if (!codigo.isEmpty() && codigo.length() == 6) {
                verificarCodigo(codigo, username);
            } else {
                Toast.makeText(VerifyRecoverCodeActivity.this, "Por favor ingresa el código completo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarCodigo(String codigo, String username) {
        VerifyCode request = new VerifyCode(codigo, username);
        Log.d("codigo", request.getCode());
        Log.d("user", request.getUsername());

        apiService.recover(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(VerifyRecoverCodeActivity.this, "Código verificado exitosamente", Toast.LENGTH_SHORT).show();
                    token = response.body();
                    Intent intent = new Intent(VerifyRecoverCodeActivity.this, NewPasswordActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerifyRecoverCodeActivity.this, "Código incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(VerifyRecoverCodeActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("VERIFICATION_ERROR", t.getMessage());
            }
        });
    }
}
