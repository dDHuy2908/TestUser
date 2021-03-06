package com.ddhuy4298.test.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.ddhuy4298.test.fragments.AccountFragment;
import com.ddhuy4298.test.fragments.BaseFragment;
import com.ddhuy4298.test.fragments.HistoryFragment;
import com.ddhuy4298.test.fragments.ServicesFragment;
import com.ddhuy4298.test.R;
import com.ddhuy4298.test.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private ServicesFragment fmServices = new ServicesFragment();
    private AccountFragment fmAccount = new AccountFragment();
    private HistoryFragment fmHistory = new HistoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1512);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.bottomNav.setOnNavigationItemSelectedListener(MainActivity.this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.e(getClass().getName(), token);
            }
        });

        initFragment();
    }

    /**
     * hide keyboard whem touch outside
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fmServices);
        transaction.add(R.id.container, fmAccount);
        transaction.add(R.id.container, fmHistory);
        transaction.commit();
        showFragment(fmServices);
        binding.bottomNav.getMenu().getItem(0).setEnabled(false);
    }

    private void showFragment(BaseFragment fmShow) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.hide(fmServices);
        transaction.hide(fmAccount);
        transaction.hide(fmHistory);
        transaction.show(fmShow);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_services:
                showFragment(fmServices);
                binding.bottomNav.getMenu().getItem(0).setChecked(true);
                binding.bottomNav.getMenu().getItem(0).setEnabled(false);
                binding.bottomNav.getMenu().getItem(1).setEnabled(true);
                binding.bottomNav.getMenu().getItem(2).setEnabled(true);
                break;
            case R.id.bottom_nav_history:
                showFragment(fmHistory);
                binding.bottomNav.getMenu().getItem(1).setChecked(true);
                binding.bottomNav.getMenu().getItem(0).setEnabled(true);
                binding.bottomNav.getMenu().getItem(1).setEnabled(false);
                binding.bottomNav.getMenu().getItem(2).setEnabled(true);
                break;
            case R.id.bottom_nav_account:
                showFragment(fmAccount);
                binding.bottomNav.getMenu().getItem(2).setChecked(true);
                binding.bottomNav.getMenu().getItem(0).setEnabled(true);
                binding.bottomNav.getMenu().getItem(1).setEnabled(true);
                binding.bottomNav.getMenu().getItem(2).setEnabled(false);
                break;
        }
        return true;
    }
}
