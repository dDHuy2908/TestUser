package com.ddhuy4298.test.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddhuy4298.test.PostedJobAdapter;
import com.ddhuy4298.test.R;
import com.ddhuy4298.test.databinding.FragmentHistoryBinding;
import com.ddhuy4298.test.models.PostedJob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding>{
    private PostedJobAdapter adapter;
    private ArrayList<PostedJob> data = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
            .child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
