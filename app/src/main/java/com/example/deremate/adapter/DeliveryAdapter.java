package com.example.deremate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deremate.R;
import com.example.deremate.data.model.DeliveryDTO;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private List<DeliveryDTO> deliveries;
    private final OnDeliveryClickListener listener;

    public interface OnDeliveryClickListener {
        void onDeliveryClick(DeliveryDTO delivery);
    }

    public DeliveryAdapter(List<DeliveryDTO> deliveries, OnDeliveryClickListener listener) {
        this.deliveries = deliveries;
        this.listener = listener;
    }

    public void updateDeliveries(List<DeliveryDTO> newDeliveries) {
        this.deliveries = newDeliveries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        DeliveryDTO delivery = deliveries.get(position);
        holder.bind(delivery, listener);
    }

    @Override
    public int getItemCount() {
        return deliveries != null ? deliveries.size() : 0;
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvAddress, tvState;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tv_order_address);
            tvOrderId = itemView.findViewById(R.id.tv_delivery_id);
            tvState = itemView.findViewById(R.id.tv_delivery_state);
        }

        public void bind(DeliveryDTO deliveryDTO, DeliveryAdapter.OnDeliveryClickListener listener) {
            tvOrderId.setText("ID: " + deliveryDTO.getOrder().getId());
            tvAddress.setText("Dirección: " + deliveryDTO.getOrder().getAddress());
            tvState.setText("Estado: " + deliveryDTO.getStatus());

            itemView.setOnClickListener(v -> listener.onDeliveryClick(deliveryDTO));
        }
    }
}
