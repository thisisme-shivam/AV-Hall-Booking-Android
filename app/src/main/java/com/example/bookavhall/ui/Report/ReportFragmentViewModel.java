package com.example.bookavhall.ui.Report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.model.Report;
import com.example.bookavhall.repository.ReportsRepository;

import java.util.List;

public class ReportFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private ReportsRepository reportsRepository;
    private MutableLiveData<List<Report>> reportList;

    public ReportFragmentViewModel() {
        reportsRepository = new ReportsRepository();
        getReports();
    }

    public LiveData<List<Report>> getReports() {
        if(reportList.getValue()==null) {
            reportList = (MutableLiveData<List<Report>>) reportsRepository.getReports();
        }
        return reportList;
    }

}