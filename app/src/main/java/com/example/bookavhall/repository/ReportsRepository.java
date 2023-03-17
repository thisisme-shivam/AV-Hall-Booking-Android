package com.example.bookavhall.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookavhall.model.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportsRepository {
    private DatabaseReference db;

    private FirebaseAuth mAuth;
    private MutableLiveData<ArrayList<Report>> reportList;

    public ReportsRepository() {
        db = FirebaseDatabase.getInstance().getReference();
        reportList = new MutableLiveData<>();
        loadReports();
    }

    public LiveData<ArrayList<Report>> getReports() {
        return reportList;
    }

    public void loadReports() {
        //Database path to be set for fetching data from Firebase
        mAuth = FirebaseAuth.getInstance();
        String curr_uid = mAuth.getCurrentUser().getUid();
        db.child("UserBookings").child(curr_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Report> reports = new ArrayList<>();

                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    Report report = snapshot1.getValue(Report.class);
                    reports.add(report);
                }

                reportList.postValue(reports);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserRepository", "Failed to load reports: " + error.getMessage());
            }
        });
    }

}
