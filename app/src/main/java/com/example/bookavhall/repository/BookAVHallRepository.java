package com.example.bookavhall.repository;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.bookavhall.SendNotification;
import com.example.bookavhall.activities.Interfaces;
import com.example.bookavhall.activities.SendMail;
import com.example.bookavhall.model.TimeSlot;
import com.example.bookavhall.model.Booking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BookAVHallRepository {


    MutableLiveData<TimeSlot> firstYearTimeSlotMutable, otherYearTimeSlotMutable;
    DatabaseReference ref;
    String avHallName= "";

    Interfaces.LoadingInterface loadingInterface;
    private Context context;

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

    public void bookAVHall(String avHallUid, ArrayList<String> bookingTime, String date,String avHallName, Interfaces.LoadingInterface loadingInterface) {
        ArrayList<Booking> bookings = new ArrayList<>();
        this.loadingInterface = loadingInterface;
        this.avHallName = avHallName;
        FirebaseDatabase.getInstance().getReference().child("Bookings")
                .child(date).child(avHallUid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Log.i("date",avHallUid + " " + date);
                        Log.i("val", String.valueOf(dataSnapshot.getChildrenCount()));
                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                            Booking booking = snapshot.getValue(Booking.class);
                            bookings.add(booking);
                        }
                        Log.i("value",bookings.toString());
                        validate(avHallUid,bookings,bookingTime,date);
                    }
                });




    }

    private void validate(String avHallUid, ArrayList<Booking> bookings, ArrayList<String> bookingTime, String date) {
        Log.i("check","us");
        for(Booking booking:bookings){
            for(String book:bookingTime){
                if(Objects.equals(booking.getBookingTime(), book)){
                    loadingInterface.alreadyBooked();
                    return;
                }
            }
        }
        book(avHallUid,bookings,bookingTime,date);

    }

    private void book(String avHallUid, ArrayList<Booking> bookings, ArrayList<String> bookingTime, String date) {
        for(String booktime:bookingTime){
            String bookingId = FirebaseDatabase.getInstance().getReference().child("Bookings")
                    .child(avHallUid)
                    .child(date)
                    .push().getKey();

            Booking bookingModel = new Booking();
            bookingModel.setBookingUid(bookingId);
            bookingModel.setBookingTime(booktime);
            bookingModel.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            bookingModel.setAvHallUid(avHallUid);
            bookingModel.setBookingDate(date);
            FirebaseDatabase.getInstance().getReference().child("Bookings")
                    .child(date)
                    .child(avHallUid)
                    .child(bookingId).setValue(bookingModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            SendMail mail  = new SendMail();

                            mail.sendBookingMailToThisUser(bookingModel,avHallName);
                            SendNotification notification = new SendNotification();
                            notification.sendMessageToAdmin(context,bookingModel);
                            loadingInterface.onCompleteTask();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingInterface.onFailedTask();
                        }
                    });

            HashMap<String,String> mp = new HashMap<>();
            mp.put("bookingUid",bookingId);
            mp.put("avHallUid",avHallUid);
            FirebaseDatabase.getInstance().getReference().child("UserBookings")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child(date).child(bookingId).setValue(mp)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingInterface.onFailedTask();
                        }
                    });
        }


    }

    public void setContext(Context requireActivity) {
        this.context = requireActivity;
    }
}