package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deremate.data.model.User;
import com.example.deremate.data.network.ApiService;

import java.util.Optional;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class NewPasswordActivity extends AppCompatActivity {

    private EditText etNewPassword, etConfirmPassword;
    private Button btnSaveNewPassword;

    @Inject
    ApiService apiService;

    private String username;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnSaveNewPassword = findViewById(R.id.btn_save_new_password);

        // Obtener el nombre de usuario desde el Intent
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            token = intent.getStringExtra("token");

        }

        btnSaveNewPassword.setOnClickListener(v -> {
            String newPassword = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Complete ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            saveNewPassword(username, newPassword, "Bearer " + token);
        });
    }

    private void saveNewPassword(String username, String newPassword, String token) {
        User user = new User(username,newPassword);
        Log.d("User",username);
        Log.d("password",newPassword);
        Log.d("token",token);


        apiService.setNewPassword(token,user).enqueue(new Callback<Optional<User>>() {
            @Override
            public void onResponse(Call<Optional<User>> call, Response<Optional<User>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NewPasswordActivity.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NewPasswordActivity.this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Optional<User>> call, Throwable t) {
                Toast.makeText(NewPasswordActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
