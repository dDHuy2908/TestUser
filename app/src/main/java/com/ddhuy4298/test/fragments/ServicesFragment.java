package com.ddhuy4298.test.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ddhuy4298.test.R;
import com.ddhuy4298.test.activities.HouseCleanActivity;
import com.ddhuy4298.test.databinding.FragmentServicesBinding;
import com.ddhuy4298.test.listeners.ServicesItemListener;

public class ServicesFragment extends BaseFragment<FragmentServicesBinding> implements ServicesItemListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_services;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        binding.setListener(this);
    }

    @Override
    public void onHouseCleanClick() {
        Intent intent = new Intent(getActivity(), HouseCleanActivity.class);
        startActivity(intent);
    }

    @Override
    public void onHouseTransferClick() {

    }
}
