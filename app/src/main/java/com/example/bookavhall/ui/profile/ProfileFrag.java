package com.example.bookavhall.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentProfile2Binding;

public class ProfileFrag extends Fragment {

    FragmentProfile2Binding binding;


    private ProifleFragmentViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProifleFragmentViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getTeacher().observe(getViewLifecycleOwner(), new Observer<Teacher>() {
            @Override
            public void onChanged(Teacher teacher) {
                if(teacher == null)
                    return;

                if(teacher.getFirstName() == null)
                    return;

                Log.i("yes","fjk");
                Log.i("gmail",teacher.getGmail());
//                binding.email.setText(teacher.getGmail());
//                binding.profileName.setText(teacher.getFirstName() + " " + teacher.getLastName());
//                binding.phoneNo.setText(teacher.getPhoneNumber());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.i("bind","yes");

        binding = FragmentProfile2Binding.inflate(inflater, container, false);


         return inflater.inflate(R.layout.fragment_profile2, null);
    }



}