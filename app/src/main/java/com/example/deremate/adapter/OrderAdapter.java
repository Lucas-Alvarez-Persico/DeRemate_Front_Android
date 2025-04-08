package com.example.deremate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deremate.R;
import com.example.deremate.data.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnItemClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    public void updateOrders(List<Order> newOrders) {
        this.orderList = newOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order, listener);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvAddress, tvState;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvAddress = itemView.findViewById(R.id.tv_order_address);
            tvState = itemView.findViewById(R.id.tv_order_state);
        }

        public void bind(Order order, OnItemClickListener listener) {
            tvOrderId.setText("ID: " + order.getId());
            tvAddress.setText("Dirección: " + order.getAddress());

            itemView.setOnClickListener(v -> listener.onItemClick(order));
        }
    }
}