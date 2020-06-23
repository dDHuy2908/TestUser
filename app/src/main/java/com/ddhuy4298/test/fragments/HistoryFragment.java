package com.ddhuy4298.test.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddhuy4298.test.activities.JobDetailActivity;
import com.ddhuy4298.test.adapters.RequestAdapter;
import com.ddhuy4298.test.R;
import com.ddhuy4298.test.databinding.FragmentHistoryBinding;
import com.ddhuy4298.test.listeners.RequestClickListener;
import com.ddhuy4298.test.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding> implements RequestClickListener {
    private RequestAdapter adapter;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
            .child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RequestAdapter(getLayoutInflater());
        binding.rvPostedRequest.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Request> data = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    data.add(request);
                }
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.setListener(this);
    }

    @Override
    public void onRequestClick(Request postedJob) {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
    }
}
