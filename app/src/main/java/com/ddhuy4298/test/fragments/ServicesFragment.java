package com.ddhuy4298.test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ddhuy4298.test.R;
import com.ddhuy4298.test.activities.ServiceActivity;
import com.ddhuy4298.test.adapters.ServiceAdapter;
import com.ddhuy4298.test.databinding.FragmentServicesBinding;
import com.ddhuy4298.test.listeners.ServicesItemListener;
import com.ddhuy4298.test.models.Service;

import java.io.Serializable;
import java.util.ArrayList;

public class ServicesFragment extends BaseFragment<FragmentServicesBinding> implements ServicesItemListener {

    public static final String SERVICE_ID = "service";
    private ServiceAdapter adapter;
    private ArrayList<Service> data = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_services;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.rvService.setLayoutManager(new GridLayoutManager(getContext(), 2));
        initService();
    }

    private void initService() {
        adapter = new ServiceAdapter(getLayoutInflater());
        data.clear();
        data.add(new Service(R.drawable.ic_house_cleaning, "HouseCleaning"));
        data.add(new Service(R.drawable.ic_house_transfer, "HouseTransfer"));
        data.add(new Service(R.drawable.ic_cooking, "Cooking"));
        data.add(new Service(R.drawable.ic_washing, "Washing"));
        data.add(new Service(R.drawable.ic_electronic_repair, "Electronic"));
        data.add(new Service(R.drawable.ic_computer_repair, "Computer"));
        data.add(new Service(R.drawable.ic_water_repair, "Water"));
        data.add(new Service(R.drawable.ic_repaint, "Repaint"));
        if (adapter != null) {
            adapter.setData(data);
            adapter.setListener(this);
        }
        binding.rvService.setAdapter(adapter);
    }

    @Override
    public void onServiceClick(Service service) {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        intent.putExtra(SERVICE_ID, service);
        startActivity(intent);
    }
}
