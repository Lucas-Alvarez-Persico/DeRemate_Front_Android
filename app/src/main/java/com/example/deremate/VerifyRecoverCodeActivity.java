package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.deremate.data.model.VerifyCode;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.data.repository.TokenRepository;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class VerifyRecoverCodeActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @Inject
    TokenRepository tokenRepository;

    private EditText[] digits;
    private Button btnVerificar;
    private String username;
    private String token;

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
                Toast.makeText(VerifyRecoverCodeActivity.this, "Por favor ingresa el código", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEditTexts() {
        for (int i = 0; i < digits.length; i++) {
            final int index = i;
            digits[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
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
                @Override public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
            });
        }
    }

    private void verificarCodigo(String codigo, String username) {
        VerifyCode request = new VerifyCode(codigo, username);
        apiService.recover(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    token = response.body();
                    Toast.makeText(VerifyRecoverCodeActivity.this, "Código verificado exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyRecoverCodeActivity.this, NewPasswordActivity.class);
                    intent.putExtra("username", username);
                    tokenRepository.saveToken(token);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerifyRecoverCodeActivity.this, "Código incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(VerifyRecoverCodeActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
