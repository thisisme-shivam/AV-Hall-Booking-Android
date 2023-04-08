package com.example.bookavhall.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bookavhall.databinding.FragmentProfileBinding;
import com.example.bookavhall.model.Teacher;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;


    private ProfileFragmentViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
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

                binding.email.setText(teacher.getGmail());
                binding.name.setText(teacher.getFirstName()+" "+teacher.getLastName());
                binding.phone.setText(teacher.getPhoneNumber());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        

        binding = FragmentProfileBinding.inflate(inflater, container, false);




        return binding.getRoot();
    }



}