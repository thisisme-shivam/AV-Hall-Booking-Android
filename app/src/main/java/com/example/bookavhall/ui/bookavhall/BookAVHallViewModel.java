package com.example.bookavhall.ui.bookavhall;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.activities.Interfaces;
import com.example.bookavhall.model.TimeSlot;
import com.example.bookavhall.repository.BookAVHallRepository;

import java.util.ArrayList;

public class BookAVHallViewModel extends ViewModel {
    BookAVHallRepository repository;

    MutableLiveData<TimeSlot> firstYearTimeSlotMutable, otherYearTimeSlotMutable;

    public BookAVHallViewModel(){
        repository = new BookAVHallRepository();
        firstYearTimeSlotMutable = new MutableLiveData<>();
        otherYearTimeSlotMutable = new MutableLiveData<>();
    }


    public MutableLiveData<TimeSlot> getFirstYearTimeSlotMutable() {
        if(firstYearTimeSlotMutable.getValue() == null){
            return repository.getFirstYearTimeSlotMutable();
        }

        return otherYearTimeSlotMutable;
    }

    public MutableLiveData<TimeSlot> getOtherYearTimeSlotMutable() {
        if(otherYearTimeSlotMutable.getValue() == null){
            return repository.getOtherYearTimeSlotMutable();
        }

        return otherYearTimeSlotMutable;
    }

    public void bookAVHall(String avHallUid, ArrayList<String> bookingTime, String s,String avHallName, Interfaces.LoadingInterface loadingInterface) {
        repository.bookAVHall(avHallUid,bookingTime,s,avHallName,loadingInterface);
    }

    public void setContext(Context requireActivity) {
        repository.setContext(requireActivity);
    }
}
