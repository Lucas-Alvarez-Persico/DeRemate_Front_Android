package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deremate.adapter.DeliveryAdapter;
import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.delivery.DeliveryRepository;
import com.example.deremate.utils.RepositoryCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    @Inject
    DeliveryRepository deliveryRepository;
    @Inject
    TokenRepository tokenRepository;

    private RecyclerView recyclerView;
    private DeliveryAdapter deliveryAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        if (tokenRepository.getToken() == null) {
            Log.d("MainActivity", "No token found, redirecting to login.");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        tokenRepository.clearToken();
        Log.d("MainActivity", "Token eliminado al finalizar.");
    }*/

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_orders); // Podrías cambiar el ID a rv_deliveries si querés que refleje lo nuevo
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        deliveryAdapter = new DeliveryAdapter(List.of(), delivery -> {
            Intent intent = new Intent(MainActivity.this, DeliveryDetailActivity.class);
            intent.putExtra("delivery_id", delivery.getId());
            intent.putExtra("delivery_status", delivery.getStatus().name());
            intent.putExtra("delivery_address", delivery.getOrder().getAddress());
            startActivity(intent);
        });
        recyclerView.setAdapter(deliveryAdapter);

        String token = tokenRepository.getToken();
        if (token != null) {
            deliveryRepository.getCurrentDeliveriesByStatus("pendiente", new RepositoryCallback<List<DeliveryDTO>>() {
                @Override
                public void onSuccess(List<DeliveryDTO> deliveries) {
                    runOnUiThread(() -> deliveryAdapter.updateDeliveries(deliveries));
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }, token);
        }

        findViewById(R.id.btn_ver_historial).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

    }
}

