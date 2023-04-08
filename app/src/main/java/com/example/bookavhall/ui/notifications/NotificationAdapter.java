package com.example.bookavhall.ui.notifications;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookavhall.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {





    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false);
        return new NotificationHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class NotificationHolder extends RecyclerView.ViewHolder{


        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
