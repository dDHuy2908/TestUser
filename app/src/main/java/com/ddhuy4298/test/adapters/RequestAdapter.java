package com.ddhuy4298.test.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddhuy4298.test.databinding.ItemRequestBinding;
import com.ddhuy4298.test.listeners.RequestClickListener;
import com.ddhuy4298.test.models.Request;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {
    private ArrayList<Request> data;
    private LayoutInflater inflater;
    private RequestClickListener listener;

    public RequestAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setData(ArrayList<Request> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(RequestClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRequestBinding binding = ItemRequestBinding.inflate(inflater, parent, false);
        return new RequestHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        holder.binding.setItem(data.get(position));
        if (listener != null) {
            holder.binding.setListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder {
        private ItemRequestBinding binding;

        public RequestHolder(@NonNull ItemRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
