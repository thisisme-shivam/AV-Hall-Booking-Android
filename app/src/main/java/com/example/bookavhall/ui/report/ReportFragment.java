package com.example.bookavhall.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookavhall.databinding.FragmentReportBinding;

public class ReportFragment extends Fragment {

    private ReportFragmentViewModel mViewModel;
    private ReportAdapter reportAdapter;
    FragmentReportBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(ReportFragmentViewModel.class);
        reportAdapter = new ReportAdapter();
        binding.reportsRv.setAdapter(reportAdapter);

        mViewModel.getReports().observe(getViewLifecycleOwner(), reports -> {
            reportAdapter.setList(reports);
            reportAdapter.notifyDataSetChanged();
        });

    }
}