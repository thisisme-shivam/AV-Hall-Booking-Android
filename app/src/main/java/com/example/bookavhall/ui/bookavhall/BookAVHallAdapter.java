package com.example.bookavhall.ui.bookavhall;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.TiminglayoutBinding;
import com.example.bookavhall.model.AVHalls;
import com.example.bookavhall.ui.avhall.AVHallAdapter;
import com.example.bookavhall.ui.avhall.AVHallFragment;

import java.util.ArrayList;

public class BookAVHallAdapter extends RecyclerView.Adapter<BookAVHallAdapter.TimeViewHolder> {

    ArrayList<String> timings;
    Context context ;

    int selectedList[];
    public BookAVHallAdapter(Context context){
        timings = new ArrayList<>();
        selectedList = new int[0];
        this.context = context;
    }
    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timinglayout, parent, false);
        return new TimeViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        String timing   = timings.get(position);
        holder.binding.timing.setText(timing);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedList[holder.getAdapterPosition()] == 1){
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    holder.itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.border));
                    selectedList[holder.getAdapterPosition()] = 0;
                }else{
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
                    selectedList[holder.getAdapterPosition()] =1;
                 }

            }
        });
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    public void setTiming(ArrayList<String> timings) {

        notifyDataSetChanged();
        this.timings = timings;
        this.selectedList = new int[timings.size()];


    }

    public class TimeViewHolder  extends RecyclerView.ViewHolder {
        TiminglayoutBinding binding;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =  TiminglayoutBinding.bind(itemView);
        }
    }
}

