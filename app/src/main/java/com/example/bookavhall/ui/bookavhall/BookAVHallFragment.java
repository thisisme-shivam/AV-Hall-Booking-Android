package com.example.bookavhall.ui.bookavhall;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentBookAvHallBinding;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;


public class BookAVHallFragment extends Fragment {
    FragmentBookAvHallBinding binding;

    Dialog datepickDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DateTimeFormatter dtf = null;
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

        ThemedButton button = new ThemedButton(getContext());
        button.setText("lsjdf");
        List<ThemedButton> list  = new ArrayList<>();
        list.add(button);
        binding.themebuttongroup.addView(button, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    private void setDialog() {
        datepickDialog = new Dialog(getContext());
        datepickDialog.setContentView(R.layout.datepickerlayout);
        datepickDialog.show();
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
                String month = String.valueOf(datePicker.getMonth() + 1);
                String year = String.valueOf(datePicker.getYear());

                String date = year+"/"+month+"/"+day;
                datepickDialog.dismiss();
                binding.datepickbutton.setText(date);
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