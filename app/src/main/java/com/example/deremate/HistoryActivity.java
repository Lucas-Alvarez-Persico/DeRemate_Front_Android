package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deremate.adapter.DeliveryAdapter;
import com.example.deremate.data.model.DeliveryDTO;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.delivery.DeliveryRepository;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryActivity extends AppCompatActivity {

    @Inject
    DeliveryRepository deliveryRepository;

    @Inject
    TokenRepository tokenRepository;

    private RecyclerView rvHistory;
    private DeliveryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeliveryAdapter(List.of(), delivery -> {
            Intent intent = new Intent(HistoryActivity.this, DeliveryDetailActivity.class);
            intent.putExtra("delivery_id", delivery.getOrder().getId());
            intent.putExtra("order_address", delivery.getOrder().getAddress());
            intent.putExtra("delivery_state", delivery.getStatus());
            startActivity(intent);
        });
        rvHistory.setAdapter(adapter);

        Button btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish());

        String token = tokenRepository.getToken();
        Log.e("HOLAAAA",token);
        if (token != null) {
            deliveryRepository.getCurrentDeliveriesByStatus("completado", new RepositoryCallback<List<DeliveryDTO>>() {
                @Override
                public void onSuccess(List<DeliveryDTO> deliveries) {
                    runOnUiThread(() -> adapter.updateDeliveries(deliveries));
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("ERROR", errorMessage);
                    runOnUiThread(() -> Toast.makeText(HistoryActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                }
            }, token);
        } else {
            Toast.makeText(this, "Token no disponible", Toast.LENGTH_SHORT).show();
        }
    }
}
