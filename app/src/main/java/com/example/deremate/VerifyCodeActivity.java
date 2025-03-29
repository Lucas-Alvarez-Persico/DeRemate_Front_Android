package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.model.VerifyCode;
import com.example.deremate.data.network.ApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class VerifyCodeActivity extends AppCompatActivity {
    @Inject
    ApiService apiService;

    private EditText etCodigo;
    private Button btnVerificar;
    private String username; // Variable para guardar el email recibido
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        etCodigo = findViewById(R.id.et_codigo);
        btnVerificar = findViewById(R.id.btn_verificar);
        username = getIntent().getStringExtra("username");

        btnVerificar.setOnClickListener(v -> {
            String codigo = etCodigo.getText().toString();

            if (!codigo.isEmpty()) {
                verificarCodigo(codigo,username);
            } else {
                Toast.makeText(VerifyCodeActivity.this, "Por favor ingresa el código", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarCodigo(String codigo, String username) {

        VerifyCode request = new VerifyCode(codigo,username);
        Log.d("codigo",request.getCode());
        Log.d("user",request.getUsername());

        apiService.verifyCode(request).enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                Log.d("codigo",request.getCode());
                Log.d("user",request.getUsername());
                Log.d("respuesta", String.valueOf(response.isSuccessful()));
                Log.d("body", String.valueOf(response.message()));
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(VerifyCodeActivity.this, "Código verificado exitosamente", Toast.LENGTH_SHORT).show();
                    UserAuth userAuth = response.body();
                    // Aquí puedes redirigir al usuario a la pantalla principal o a la de login
                    Intent intent = new Intent(VerifyCodeActivity.this, MainActivity.class);
                    Log.d("Token", userAuth.getAccess_token());
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(VerifyCodeActivity.this, "Código incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(VerifyCodeActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("VERIFICATION_ERROR", t.getMessage());
            }
        });
    }
}
