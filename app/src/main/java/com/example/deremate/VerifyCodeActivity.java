package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.model.VerifyCode;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.data.repository.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class VerifyCodeActivity extends AppCompatActivity {
    @Inject
    ApiService apiService;

    @Inject
    TokenRepository tokenRepository;
    private EditText[] digits;
    private Button btnVerificar;
    private String username; // Variable para guardar el email recibido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        // Inicializamos el array de EditTexts
        digits = new EditText[]{
                findViewById(R.id.digit1),
                findViewById(R.id.digit2),
                findViewById(R.id.digit3),
                findViewById(R.id.digit4),
                findViewById(R.id.digit5),
                findViewById(R.id.digit6)
        };

        btnVerificar = findViewById(R.id.btn_verificar);
        username = getIntent().getStringExtra("username");

        // Llamamos al método para configurar todos los EditText
        setupEditTexts();

        // Lógica del botón de verificar
        btnVerificar.setOnClickListener(v -> {
            StringBuilder codigo = new StringBuilder();
            for (EditText digit : digits) {
                codigo.append(digit.getText().toString());
            }

            if (!codigo.toString().isEmpty()) {
                verificarCodigo(codigo.toString(), username);
            } else {
                Toast.makeText(VerifyCodeActivity.this, "Por favor ingresa el código", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEditTexts() {
        for (int i = 0; i < digits.length; i++) {
            final int index = i;
            digits[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    // Moverse al siguiente campo si se ha ingresado un dígito
                    if (s.length() == 1 && index < digits.length - 1) {
                        digits[index + 1].requestFocus();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                    // Moverse al campo anterior si se borra un dígito
                    if (count > 0 && index > 0) {
                        digits[index - 1].requestFocus();
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                }
            });
        }
    }

    private void verificarCodigo(String codigo, String username) {
        VerifyCode request = new VerifyCode(codigo, username);
        Log.d("codigo", request.getCode());
        Log.d("user", request.getUsername());

        apiService.verifyCode(request).enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(VerifyCodeActivity.this, "Código verificado exitosamente", Toast.LENGTH_SHORT).show();
                    UserAuth userAuth = response.body();

                    // Verificar si el token es de recuperación y redirigir adecuadamente
                    Intent intent;
                    intent = new Intent(VerifyCodeActivity.this, MainActivity.class);

                    tokenRepository.saveToken(userAuth.getAccess_token());
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
