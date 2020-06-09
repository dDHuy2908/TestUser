package com.ddhuy4298.test.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddhuy4298.test.activities.JobDetailActivity;
import com.ddhuy4298.test.PostedJobAdapter;
import com.ddhuy4298.test.R;
import com.ddhuy4298.test.databinding.FragmentHistoryBinding;
import com.ddhuy4298.test.listeners.PostedJobClickListener;
import com.ddhuy4298.test.models.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding> implements PostedJobClickListener {
    private PostedJobAdapter adapter;
    private ArrayList<Request> data = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
            .child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests");

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new PostedJobAdapter(getLayoutInflater());
        binding.rvPostedJob.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request job = snapshot.getValue(Request.class);
                    data.add(job);
                }
                adapter.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.setListener(this);
    }

    @Override
    public void onPostedJobClick(Request postedJob) {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
    }
}
