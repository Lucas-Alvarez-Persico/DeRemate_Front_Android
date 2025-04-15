package com.example.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
public class HistoryActivity extends BaseActivity {

    @Inject
    DeliveryRepository deliveryRepository;

    @Inject
    TokenRepository tokenRepository;

    private RecyclerView rvHistory;
    private DeliveryAdapter adapter;
    private TextView textDefault;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_history;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rvHistory = findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        textDefault = findViewById(R.id.text_default);


        adapter = new DeliveryAdapter(List.of(), delivery -> {
            Intent intent = new Intent(HistoryActivity.this, DeliveryDetailActivity.class);
            intent.putExtra("delivery_id", delivery.getOrder().getId());
            intent.putExtra("delivery_address", delivery.getOrder().getAddress());
            intent.putExtra("delivery_status", delivery.getStatus().name());
            intent.putExtra("delivery_client", delivery.getOrder().getClient());
            intent.putExtra("delivery_start_time", delivery.getStartTime());
            intent.putExtra("delivery_end_time", delivery.getEndTime());
            intent.putExtra("delivery_time_difference", delivery.getDeliveryTime());
            startActivity(intent);
        });
        rvHistory.setAdapter(adapter);

        String token = tokenRepository.getToken();
        if (token != null) {
            deliveryRepository.getCurrentDeliveriesByStatus("completado", new RepositoryCallback<List<DeliveryDTO>>() {
                @Override
                public void onSuccess(List<DeliveryDTO> deliveries) {
                    runOnUiThread(() -> {
                        if(!deliveries.isEmpty()){
                            textDefault.setVisibility(View.GONE);
                            adapter.updateDeliveries(deliveries);
                        }else{
                            textDefault.setVisibility(View.VISIBLE);
                        }
                    });

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
