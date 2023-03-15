package com.example.bookavhall.ui.avhall;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.model.AVHalls;
import com.example.bookavhall.repository.AvHallsRepoistory;

import java.util.List;

public class AVHallViewModel extends ViewModel {

    private AvHallsRepoistory avHallsRepoistory;
    private MutableLiveData<List<AVHalls>> hallList = new MutableLiveData<>();

    public AVHallViewModel() {
        avHallsRepoistory = new AvHallsRepoistory();
        getAvHalls();
    }

    public LiveData<List<AVHalls>> getAvHalls() {
        if(hallList.getValue()==null) {
            hallList = (MutableLiveData<List<AVHalls>>) avHallsRepoistory.getAvHalls();
        }
        return hallList;
    }
}