package com.example.bookavhall.ui.report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.model.Report;
import com.example.bookavhall.repository.ReportsRepository;

import java.util.ArrayList;

public class ReportFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private ReportsRepository reportsRepository;
    private MutableLiveData<ArrayList<Report>> reportList = new MutableLiveData<>();

    public ReportFragmentViewModel() {
        reportsRepository = new ReportsRepository();
        getReports();
    }

    public LiveData<ArrayList<Report>> getReports() {
        if(reportList.getValue()==null) {
            reportList = (MutableLiveData<ArrayList<Report>>) reportsRepository.getReports();
        }
        return reportList;
    }

}