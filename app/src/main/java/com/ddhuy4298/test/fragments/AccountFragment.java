package com.ddhuy4298.test.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ddhuy4298.test.R;
import com.ddhuy4298.test.activities.LoginActivity;
import com.ddhuy4298.test.databinding.FragmentAccountBinding;
import com.ddhuy4298.test.listeners.AccountItemListener;
import com.ddhuy4298.test.utils.Const;
import com.ddhuy4298.test.utils.SharedPreferencesUtils;

public class AccountFragment extends BaseFragment<FragmentAccountBinding> implements AccountItemListener {

    private SharedPreferencesUtils preferences;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setListener(this);
    }

    @Override
    public void onLogoutClicked() {
        preferences = new SharedPreferencesUtils(getContext());
        preferences.remove(Const.KEY_EMAIL);
        preferences.remove(Const.KEY_PASSWORD);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
