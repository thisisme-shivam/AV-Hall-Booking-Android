package com.example.bookavhall.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookavhall.model.Report;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReportsRepository {
    private DatabaseReference db;
    private MutableLiveData<List<Report>> reportList;

    public ReportsRepository() {
        db = FirebaseDatabase.getInstance().getReference();
        reportList = new MutableLiveData<>();
        loadReports();
    }

    public LiveData<List<Report>> getReports() {
        return reportList;
    }

    public void loadReports() {
        //Database path to be set for fetching data from Firebase

    }

}
