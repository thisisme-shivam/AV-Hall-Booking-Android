package com.example.bookavhall.ui.report;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookavhall.R;
import com.example.bookavhall.model.Report;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {

    private ArrayList<Report> reports = new ArrayList<>();

    private DatabaseReference database;
    LocalDateTime now = null;
    DateTimeFormatter dtf = null;



    public ReportAdapter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        }
    }


    @NonNull
    @Override
    public ReportAdapter.ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_rv_layout, parent, false);
        return new ReportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {


        Report curr_report = reports.get(position);


        database = FirebaseDatabase.getInstance().getReference();

        database.child("AVHalls").child(curr_report.getAvHall()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String curr_hall_name;
                curr_hall_name = snapshot.child("name").getValue(String.class);
                holder.hall_name.setText(curr_hall_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        String date = curr_report.getDate();

        Log.i("datr",date);
        String year = date.substring(0, 4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(Integer.parseInt(year)< now.getYear())
                holder.status.setText("Previous Booking");
            else if(Integer.parseInt(month)<now.getMonthValue())
                holder.status.setText("Booked Before");
            else if(Integer.parseInt(day)<now.getDayOfMonth()){
                holder.status.setText("Booked Before");
            }else{
                holder.status.setText("Upcoming");

            }

            holder.date.setText("Date:" + day + "/" + month + "/" + year);
        }
        holder.time.setText("Time: " + curr_report.getTime());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setList(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public class ReportHolder extends RecyclerView.ViewHolder{

        private TextView hall_name;
        private TextView status;
        private TextView date;
        private TextView time;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            hall_name = itemView.findViewById(R.id.tv_hall_name);
            status = itemView.findViewById(R.id.tv_status);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);
        }



    }

}
