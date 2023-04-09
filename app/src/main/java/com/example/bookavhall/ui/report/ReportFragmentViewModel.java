package com.example.bookavhall.ui.report;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.model.Report;
import com.example.bookavhall.repository.ReportsRepository;

import java.util.ArrayList;

public class ReportFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    String date;
    private ReportsRepository reportsRepository;
    private MutableLiveData<ArrayList<Report>> reportList = new MutableLiveData<>();

    public ReportFragmentViewModel() {
        reportsRepository  = new ReportsRepository();
    }

    public MutableLiveData<ArrayList<Report>> getReports() {
        if(reportList.getValue()==null) {
            Log.i("fdjl","fjdkj");
            reportList = (MutableLiveData<ArrayList<Report>>) reportsRepository.getReports();
        }
        return reportList;
    }



    public void loadData(String format) {
        reportsRepository.loadReports(format);
    }
}