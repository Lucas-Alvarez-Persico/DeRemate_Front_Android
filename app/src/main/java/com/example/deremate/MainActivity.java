package com.example.deremate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.example.deremate.adapter.OrderAdapter;
import com.example.deremate.data.model.Order;
import com.example.deremate.data.model.User;
import com.example.deremate.data.model.UserAuth;
import com.example.deremate.data.network.ApiService;
import com.example.deremate.data.repository.TokenRepository;
import com.example.deremate.data.repository.order.OrderRepository;
import com.example.deremate.di.NetworkModule;
import com.example.deremate.utils.RepositoryCallback;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint // Anotación necesaria para Hilt
public class MainActivity extends AppCompatActivity {

    @Inject
    OrderRepository orderRepository;
    @Inject
    TokenRepository tokenRepository;

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Override
    protected void onStart(){
        super.onStart();
        if (tokenRepository.getToken() == null){
            Log.d("prueba2", "esto es otra prueba");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(List.of(), order -> {
            Intent intent = new Intent(MainActivity.this, OrderDetailActivity.class);
            intent.putExtra("order_id", order.getId());
            intent.putExtra("order_address", order.getAddress());
            intent.putExtra("order_state", order.getState());
            startActivity(intent);
        });
        recyclerView.setAdapter(orderAdapter);

        String token = tokenRepository.getToken();
        if (token != null) {
            orderRepository.getAllOrders(new RepositoryCallback<List<Order>>() {
                @Override
                public void onSuccess(List<Order> orders) {
                    runOnUiThread(() -> orderAdapter.updateOrders(orders));
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }, token);
        }
    }


}
