package com.ddhuy4298.test.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddhuy4298.test.databinding.ItemServiceBinding;
import com.ddhuy4298.test.listeners.ServicesItemListener;
import com.ddhuy4298.test.models.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    private ArrayList<Service> data;
    private LayoutInflater inflater;
    private ServicesItemListener listener;

    public ServiceAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setData(ArrayList<Service> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(ServicesItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemServiceBinding binding = ItemServiceBinding.inflate(inflater, parent, false);
        return new ServiceHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.binding.setItem(data.get(position));
        if (listener != null) {
            holder.binding.setListener(listener);
        }
        holder.binding.image.setImageResource(data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {

        private ItemServiceBinding binding;

        public ServiceHolder(@NonNull ItemServiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
