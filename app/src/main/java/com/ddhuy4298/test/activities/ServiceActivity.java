package com.ddhuy4298.test.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import com.ddhuy4298.test.R;
import com.ddhuy4298.test.api.ApiBuilder;
import com.ddhuy4298.test.databinding.ActivityServiceBinding;
import com.ddhuy4298.test.listeners.HouseCleanItemListener;
import com.ddhuy4298.test.models.Notification;
import com.ddhuy4298.test.models.Receiver;
import com.ddhuy4298.test.models.Request;
import com.ddhuy4298.test.models.Service;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddhuy4298.test.fragments.ServicesFragment.SERVICE_ID;

public class ServiceActivity extends AppCompatActivity implements HouseCleanItemListener {

    private ActivityServiceBinding binding;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar = Calendar.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service);

        binding.setListener(this);

        long currentDate = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        binding.tvDate.setText(format.format(currentDate));
    }

    /**
     * hide keyboard when touch outside
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onDateClicked() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month < 10) {
                    binding.tvDate.setText(dayOfMonth + "/0" + month + "/" + year);
                } else if (dayOfMonth < 10) {
                    binding.tvDate.setText("0" + dayOfMonth + "/" + month + "/" + year);
                } else if (dayOfMonth < 10 && month < 10) {
                    binding.tvDate.setText("0" + dayOfMonth + "/0" + month + "/" + year);
                } else {
                    binding.tvDate.setText(dayOfMonth + "/" + month + "/" + year);
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onTimeClicked() {
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                binding.tvTime.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onBookClicked() {
        DatabaseReference requestReference = FirebaseDatabase.getInstance().getReference("Requests");
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Users").child("User").child(firebaseAuth.getCurrentUser().getUid());
        String requestId = requestReference.push().getKey();
        String address = binding.edtAddress.getText().toString();
        String date = binding.tvDate.getText().toString();
        String time = binding.tvTime.getText().toString();

        Intent intent = getIntent();
        Service service = (Service) intent.getSerializableExtra(SERVICE_ID);

        Request request = new Request();
        request.setAddress(address);
        request.setDate(date);
        request.setTime(time);
        request.setUserId(firebaseAuth.getCurrentUser().getUid());
        request.setJob(service.getService());
        request.setRequestId(requestId);
        request.setStatus("Pending");

        reference.child("requests").child(requestId).setValue(request);
        requestReference.child(requestId).setValue(request);

        Snackbar.make(binding.serviceLayout, "Đặt thành công!", Snackbar.LENGTH_SHORT).show();

        Notification notification = new Notification();
        notification.setTitle("New request available!");
        notification.setBody("A new request is suitable for you. Check it!.");
        Receiver receiver = new Receiver();
        receiver.setTo("/topics/" + service.getService());
        receiver.setNotification(notification);

        ApiBuilder.getInstance()
                .sendNotification(receiver)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int n = 0;
                        Log.e(getClass().getName(), n +"");
                        if (response.code() == 200) {
                            Snackbar.make(binding.serviceLayout, "Success", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(binding.serviceLayout, "Fail", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
