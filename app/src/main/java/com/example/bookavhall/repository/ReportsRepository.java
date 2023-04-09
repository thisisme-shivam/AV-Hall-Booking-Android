package com.example.bookavhall.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bookavhall.model.AVHalls;
import com.example.bookavhall.model.Booking;
import com.example.bookavhall.model.Report;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportsRepository {
    private DatabaseReference db;

    private FirebaseAuth mAuth;
    private MutableLiveData<ArrayList<Report>> reportList ;
    ArrayList<Report> reports;
    String date;
    public ReportsRepository() {

        db = FirebaseDatabase.getInstance().getReference();
        reportList = new MutableLiveData<>();
        reports= new ArrayList<>();

    }

    public MutableLiveData<ArrayList<Report>> getReports() {
        return reportList;
    }

    public void loadReports(String format) {
        //Database path to be set for fetching data from Firebase
        date = format;
        reports= new ArrayList<>();
        reportList.setValue(new ArrayList<>());
        mAuth = FirebaseAuth.getInstance();
        String curr_uid = mAuth.getCurrentUser().getUid();

        Log.i("value",";jf");
        db.child("UserBookings").child(curr_uid).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.i("value", String.valueOf(snapshot.getChildrenCount()));
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    HashMap<String,Object> mp = (HashMap<String, Object>) snapshot1.getValue();
                    Log.i("val", String.valueOf(mp.get("avHallUid")));
                    String avHallUid = String.valueOf(mp.get("avHallUid"));
                    String bookingUid = String.valueOf(mp.get("bookingUid"));
                    Log.i("value" , avHallUid+" "+bookingUid);
                    loadDataBooking(avHallUid,bookingUid);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserRepository", "Failed to load reports: " + error.getMessage());
            }
        });
    }

    private void loadDataBooking(String avHallUid, String bookingUid) {
        FirebaseDatabase.getInstance().getReference().child("Bookings")
                .child(date).child(avHallUid).child(bookingUid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Booking booking = dataSnapshot.getValue(Booking.class);
                        Report report = new Report();
                        Log.i("booking",booking.getBookingTime());
                        report.setBookingId(bookingUid);
                        report.setDate(date);
                        report.setTime(booking.getBookingTime());
                        report.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        FirebaseDatabase.getInstance().getReference().child("AVHalls")
                                .child(avHallUid)

                                .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        Log.i("av",dataSnapshot.getValue(AVHalls.class).getName());
                                        report.setAvHall(dataSnapshot.getValue(AVHalls.class).getName());
                                        reports.add(report);

                                        reportList.postValue(reports);
                                    }
                                });
                    }

                });
    }

}
