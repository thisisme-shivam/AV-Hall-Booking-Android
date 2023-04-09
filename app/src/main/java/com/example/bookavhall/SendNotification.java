package com.example.bookavhall;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookavhall.model.Booking;
import com.example.bookavhall.model.Teacher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SendNotification {
    private static String url = "https://fcm.googleapis.com/fcm/send";
    private Context context;


    static void sendToFcm(Context context,String token,Booking boookingModel){
        try {
            String url = "https://fcm.googleapis.com/fcm/send";
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject data = new JSONObject();
            data.put("title", "AV Hall Booking");


            JSONObject notificationData = new JSONObject();
            notificationData.put("notification",data);

            notificationData.put("to",token);

            JsonObjectRequest request = new JsonObjectRequest(url, notificationData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("success",response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> mp = new HashMap<>();
                    mp.put("name","blash");
                    String key = "key=BNbVNt6j_Lse6F4k791fI7UoWdqkA0c-iSJaSwaPa7tO2IXNCaezrl2hkAjPNiDEja5FGVVfeDdFPvZJdV35cSk";
                    mp.put("Authorization",key);
                    mp.put("Content-Type","application/json");
                    return mp;
                }
            };
            queue.add(request);
        }catch (Error | JSONException e){
            Log.i("Failed","at some point");
            e.printStackTrace();
        }
    }


    public void sendMessageToAdmin(Context context, Booking bookingModel) {
        this.context = context;

        String key = FirebaseDatabase.getInstance().getReference().child("Admin Notification").push().getKey();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setMessage("AV Hall Booked for "+bookingModel.getBookingTime());
        notificationModel.setDate(bookingModel.getBookingDate());
        notificationModel.setNotificationUid(key);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationModel.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
        }

        FirebaseDatabase.getInstance().getReference().child("Admin Notification").child(notificationModel.date)
                .child(notificationModel.notificationUid).setValue(notificationModel);

        FirebaseDatabase.getInstance().getReference().child("Admin")
                .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        FirebaseMessaging.Admin admin = dataSnapshot.getValue(FirebaseMessaging.Admin.class);
                        sendToFcm(context,admin.getToken(),bookingModel);
                    }
                });



    }
}
