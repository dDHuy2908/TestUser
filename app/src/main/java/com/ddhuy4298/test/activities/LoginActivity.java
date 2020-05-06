package com.ddhuy4298.test.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ddhuy4298.test.listeners.LoginItemListener;
import com.ddhuy4298.test.R;
import com.ddhuy4298.test.databinding.ActivityLoginBinding;
import com.ddhuy4298.test.utils.Const;
import com.ddhuy4298.test.utils.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginItemListener {

    public static final int REQUEST_CODE = 1;
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AlertDialog dialog;
    private SharedPreferencesUtils preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setListener(this);
        binding.edtEmail.setText("a@gmail.com");
        binding.edtPassword.setText("123456");
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Please wait ...")
                .setTheme(R.style.CustomSpotsDialog)
                .build();

        preferences = new SharedPreferencesUtils(this);
        String email = preferences.getValue(Const.KEY_EMAIL);
        String password = preferences.getValue(Const.KEY_PASSWORD);
        if (email != null && password != null) {
            dialog.show();
            login(email, password);
            return;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onLoginClick() {
        dialog.show();
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Empty info", Toast.LENGTH_LONG).show();
        } else {
            login(email, password);
        }
    }

    private void login(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        preferences.putString(Const.KEY_EMAIL, email);
                        preferences.putString(Const.KEY_PASSWORD, password);

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("Users").child("User").child(firebaseAuth.getCurrentUser().getUid());
                        reference.child("token").setValue(FirebaseInstanceId.getInstance().getToken());

                        dialog.dismiss();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onRegisterClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(RegisterActivity.EXTRA_EMAIL);
                String password = data.getStringExtra(RegisterActivity.EXTRA_PASSWORD);
                binding.edtEmail.setText(email);
                binding.edtPassword.setText(password);
            }
        }
    }
}
