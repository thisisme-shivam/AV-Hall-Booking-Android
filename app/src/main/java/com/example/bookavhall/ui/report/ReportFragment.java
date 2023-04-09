package com.example.bookavhall.ui.report;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentReportBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportFragment extends Fragment {

    private ReportFragmentViewModel mViewModel;
    private ReportAdapter reportAdapter;
    FragmentReportBinding binding;
    LocalDateTime now;
    private Dialog datepickDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DateTimeFormatter dtf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        }

        now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.datepickbutton.setText(dtf.format(now));
        }
        setDialog();
        mViewModel = new ViewModelProvider(requireActivity()).get(ReportFragmentViewModel.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mViewModel.loadData(dtf.format(now));
        }
        reportAdapter = new ReportAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.reportsRv.setLayoutManager(linearLayoutManager);
        binding.reportsRv.setAdapter(reportAdapter);

        mViewModel.getReports().observe(getViewLifecycleOwner(), reports -> {
            reportAdapter.setList(reports);
            reportAdapter.notifyDataSetChanged();
        });

        binding.datepickbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickDialog.show();
            }
        });
        datepickDialog.findViewById(R.id.donebuttondatepick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = datepickDialog.findViewById(R.id.datepicker);
                String day = String.valueOf(datePicker.getDayOfMonth());
                if(day.length() == 1)
                    day = "0"+day;
                String month = String.valueOf(datePicker.getMonth() + 1);
                if(month.length() == 1)
                    month = "0"+month;
                String year = String.valueOf(datePicker.getYear());

                String date = year+"/"+month+"/"+day;
                mViewModel.loadData(date);

                reportAdapter.notifyDataSetChanged();

                datepickDialog.dismiss();
            }
        });
    }

    private void setDialog() {
        datepickDialog = new Dialog(getContext());
        datepickDialog.setContentView(R.layout.datepickerlayout);
        datepickDialog.setCancelable(true);
        DatePicker pick = datepickDialog.findViewById(R.id.datepicker);
        pick.setMinDate(System.currentTimeMillis()- 30L *86400000);
    }
}