package com.example.bookavhall.ui.bookavhall;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentBookAvHallBinding;
import com.example.bookavhall.model.TimeSlot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class BookAVHallFragment extends Fragment {
    FragmentBookAvHallBinding binding;
    BookAVHallViewModel bookAVHallViewModel;

    Dialog datepickDialog;
    String avHallUid;
    ArrayList<String> firstYearList,otherYearList;
    BookAVHallAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookAVHallViewModel = new ViewModelProvider(this).get(BookAVHallViewModel.class);
        adapter = new BookAVHallAdapter(getContext());
        firstYearList  = new ArrayList<>();
        otherYearList = new ArrayList<>();

    }



    private ArrayList<String> makeTimeSlot(ArrayList<String> timeslots){

        ArrayList<String> timings = new ArrayList<>();
        int j = 0;
        for(int i=0;i<timeslots.size() - 1 ;i++){
            String stime = timeslots.get(i);
            String etime = timeslots.get(i+1);
            if(!stime.split(":")[1].equals(etime.split(":")[1]))
                continue;

            timings.add(stime +" to "+ etime);
            Log.i("timing:",timings.get(j));
            j++;
            }
            return timings;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        DateTimeFormatter dtf = null;
        avHallUid = getArguments().getString("AV Hall ID");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        }
        LocalDateTime now;
        now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             now = LocalDateTime.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.datepickbutton.setText(dtf.format(now));
        }

        setDialog();
        setOnclickListener();

        RecyclerView recyclerView = getView().findViewById(R.id.timingrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        bookAVHallViewModel.getFirstYearTimeSlotMutable().observe(getViewLifecycleOwner(), new Observer<TimeSlot>() {
            @Override
            public void onChanged(TimeSlot timeSlot) {
                if(timeSlot == null)
                    return;

                if(timeSlot.getEndTime() == null)
                    return;


                firstYearList = new ArrayList<>();

                int start = Integer.parseInt(timeSlot.getStartTime().split(":")[0]);
                int end = Integer.parseInt(timeSlot.getEndTime().split(":")[0]);
                int lunch = Integer.parseInt(timeSlot.getLunchTime().split(":")[0]);

                Log.i("start", String.valueOf(start + end));
                while(start<end+13){

                    String timeHour;
                    if(start<=lunch)
                    {
                        timeHour = start +":"+ timeSlot.getStartTime().split(":")[1];

                    }else{
                        if(start == 12){
                            timeHour = start + ":"+timeSlot.getEndTime().split(":")[1];
                        }else
                            timeHour = start-12 + ":"+timeSlot.getEndTime().split(":")[1];
                    }
                    firstYearList.add(timeHour);
                    Log.i("time",timeHour);

                    start++;


                }
                firstYearList = makeTimeSlot(firstYearList);

            }
        });

        bookAVHallViewModel.getOtherYearTimeSlotMutable().observe(getViewLifecycleOwner(), new Observer<TimeSlot>() {
            @Override
            public void onChanged(TimeSlot timeSlot) {
                if(timeSlot == null)
                    return;

                if(timeSlot.getEndTime() == null)
                    return;

                otherYearList = new ArrayList<>();

                int start = Integer.parseInt(timeSlot.getStartTime().split(":")[0]);
                int end = Integer.parseInt(timeSlot.getEndTime().split(":")[0]);
                int lunch = Integer.parseInt(timeSlot.getLunchTime().split(":")[0]);
                if(lunch == 0)
                        lunch = 12;

                Log.i("start", String.valueOf(start + end));
                while(start<end+13){

                    String timeHour;
                    if(start<=lunch)
                    {
                        timeHour = start +":"+ timeSlot.getStartTime().split(":")[1];

                    }else{
                        if(start == 12){
                            timeHour = start + ":"+timeSlot.getEndTime().split(":")[1];
                        }else
                            timeHour = start-12 + ":"+timeSlot.getEndTime().split(":")[1];
                    }
                    otherYearList.add(timeHour);
                    Log.i("time",timeHour);

                    start++;


                }
                otherYearList = makeTimeSlot(otherYearList);
            }
        });


    }

    private void setDialog() {
        datepickDialog = new Dialog(getContext());
        datepickDialog.setContentView(R.layout.datepickerlayout);

        datepickDialog.setCancelable(false);
    }

    private void setOnclickListener() {
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
                datepickDialog.dismiss();
                binding.datepickbutton.setText(date);
            }
        });

        binding.radiofirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    return;
                if(!firstYearList.isEmpty()){
                    for(int i = 0;i<6;i++)
                        if(adapter.selectedList[i] == 1){
                            binding.timingrv.findViewHolderForAdapterPosition(i).itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            binding.timingrv.findViewHolderForAdapterPosition(i).itemView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.border));

                        }

                    adapter.setTiming(firstYearList);
                    binding.timingrv.setVisibility(View.VISIBLE);
                    binding.bookavhallbutton.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.radioother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                        return;

                if(!otherYearList.isEmpty()){
                    for(int i = 0;i<6;i++)
                        if(adapter.selectedList[i] == 1){
                            binding.timingrv.findViewHolderForAdapterPosition(i).itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            binding.timingrv.findViewHolderForAdapterPosition(i).itemView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.border));

                        }
                    adapter.setTiming(otherYearList);
                    binding.timingrv.setVisibility(View.VISIBLE);
                    binding.bookavhallbutton.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.bookavhallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.radioother.isChecked()){
                    ArrayList<String>  bookingTime = new ArrayList<>();
                    for(int i  = 0;i<6;i++){
                        if(adapter.selectedList[i]==1){
                            bookingTime.add(firstYearList.get(i));
                        }
                    }
                    bookAVHallViewModel.bookAVHall(avHallUid,bookingTime,binding.datepickbutton.getText().toString());
                }else{

                    ArrayList<String>  bookingTime = new ArrayList<>();
                    for(int i  = 0;i<6;i++){
                        if(adapter.selectedList[i]==1){
                            bookingTime.add(firstYearList.get(i));
                        }
                    }
                    bookAVHallViewModel.bookAVHall(avHallUid,bookingTime, binding.datepickbutton.getText().toString());

                }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding  = FragmentBookAvHallBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}