package com.example.bookavhall.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookavhall.model.TimeSlot;
import com.example.bookavhall.ui.bookavhall.Booking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookAVHallRepository {

    MutableLiveData<TimeSlot> firstYearTimeSlotMutable, otherYearTimeSlotMutable;
    DatabaseReference ref;
    public BookAVHallRepository(){
        firstYearTimeSlotMutable = new MutableLiveData<>();
        otherYearTimeSlotMutable = new MutableLiveData<>();
        loadData();
    }

    public MutableLiveData<TimeSlot> getFirstYearTimeSlotMutable() {
        return firstYearTimeSlotMutable;
    }

    public MutableLiveData<TimeSlot> getOtherYearTimeSlotMutable() {
        return otherYearTimeSlotMutable;
    }

    private void loadData() {

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("TimeSlot").child("First Year").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
               TimeSlot firstYearTimeSlot = dataSnapshot.getValue(TimeSlot.class);
               firstYearTimeSlotMutable.postValue(firstYearTimeSlot);

            }
        });

        ref.child("TimeSlot").child("Other Year").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                TimeSlot otherYearTimeSlot = dataSnapshot.getValue(TimeSlot.class);
                otherYearTimeSlotMutable.postValue(otherYearTimeSlot);
            }
        });
    }

    public void bookAVHall(String avHallUid, ArrayList<String> bookingTime, String date) {


        for(String booking:bookingTime){
            String bookingId = FirebaseDatabase.getInstance().getReference().child("Bookings")
                    .child(avHallUid)
                    .child(date)
                    .push().getKey();
            date = date.replace("/","");
            Booking bookingModel = new Booking();
            bookingModel.setBookingId(bookingId);
            bookingModel.setTime(booking);
            bookingModel.setUid("sid");
            bookingModel.setAvHall(avHallUid);
            bookingModel.setDate(date);
            FirebaseDatabase.getInstance().getReference().child("Bookings")
                    .child(avHallUid)
                    .child(date)
                    .child(bookingId).setValue(bookingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.i("Booked",booking);
                        }
                    });

            FirebaseDatabase.getInstance().getReference().child("UserBookings")
                    .child("uid")
                    .child(bookingId).setValue(bookingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.i("Booked",booking);
                        }
                    });
        }


    }
}