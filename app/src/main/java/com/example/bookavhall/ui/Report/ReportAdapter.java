package com.example.bookavhall.ui.Report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookavhall.R;
import com.example.bookavhall.model.Report;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {

    private ArrayList<Report> reports = new ArrayList<>();


    @NonNull
    @Override
    public ReportAdapter.ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_rv_layout, parent, false);
        return new ReportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportHolder holder, int position) {
        Report curr_report = reports.get(position);
        holder.hall_name.setText(curr_report.getHall_name());
        holder.booking_id.setText(curr_report.getBooking_id());
        holder.date.setText(curr_report.getDate());
        holder.time.setText(curr_report.getTime());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportHolder extends RecyclerView.ViewHolder{

        private TextView hall_name;
        private TextView booking_id;
        private TextView date;
        private TextView time;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            hall_name = itemView.findViewById(R.id.tv_hall_name);
            booking_id = itemView.findViewById(R.id.tv_booking_id);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);
        }



    }

}
