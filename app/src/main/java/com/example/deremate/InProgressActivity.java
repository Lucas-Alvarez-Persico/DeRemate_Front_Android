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
public class InProgressActivity extends BaseActivity {

    @Inject
    DeliveryRepository deliveryRepository;

    @Inject
    TokenRepository tokenRepository;

    private RecyclerView rvInProgress;
    private DeliveryAdapter adapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_in_progress;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvInProgress = findViewById(R.id.rvInProgress);
        rvInProgress.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DeliveryAdapter(List.of(), delivery -> {
            Intent intent = new Intent(InProgressActivity.this, DeliveryDetailActivity.class);
            intent.putExtra("delivery_id", delivery.getOrder().getId());
            intent.putExtra("delivery_address", delivery.getOrder().getAddress());
            intent.putExtra("delivery_status", delivery.getStatus().name());
            intent.putExtra("delivery_client", delivery.getOrder().getClient());
            intent.putExtra("delivery_start_time", delivery.getStartTime());
            intent.putExtra("delivery_end_time", delivery.getEndTime());
            intent.putExtra("delivery_time_difference", delivery.getDeliveryTime());
            startActivity(intent);
        });
        rvInProgress.setAdapter(adapter);

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
                    runOnUiThread(() -> Toast.makeText(InProgressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show());
                }
            }, token);
        } else {
            Toast.makeText(this, "Token no disponible", Toast.LENGTH_SHORT).show();
        }
    }
}