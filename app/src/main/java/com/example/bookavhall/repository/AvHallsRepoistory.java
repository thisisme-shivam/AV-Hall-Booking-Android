package com.example.bookavhall.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.bookavhall.model.AVHalls;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvHallsRepoistory {
    private DatabaseReference db;
    private MutableLiveData<List<AVHalls>> avHallList;

    public interface AVHallCallback{
        public void onAVHallRegistered();
    }
    public AvHallsRepoistory() {
        db = FirebaseDatabase.getInstance().getReference();
        avHallList = new MutableLiveData<>();
        loadAvHalls();
    }

    public LiveData<List<AVHalls>> getAvHalls() {
        return avHallList;
    }

    public void loadAvHalls() {
        db.child("AVHalls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AVHalls> avHalls = new ArrayList<>();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AVHalls avHall = dataSnapshot.getValue(AVHalls.class);
                    avHalls.add(avHall);
                }
                avHallList.postValue(avHalls);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserRepository", "Failed to load Halls: " + error.getMessage());
            }
        });
    }

}
