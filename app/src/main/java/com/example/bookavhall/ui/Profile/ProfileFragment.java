package com.example.bookavhall.ui.Profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bookavhall.databinding.FragmentProfileBinding;
import com.example.bookavhall.model.AVHalls;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.xml.namespace.QName;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;


    private ProfileFragmentViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        mViewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        mViewModel.getTeacher().observe(getViewLifecycleOwner(), new Observer<Teacher>() {
            @Override
            public void onChanged(Teacher teacher) {
                if(teacher == null)
                        return;

                if(teacher.getFirstName() == null)
                        return;

                binding.email.setText(teacher.getGmail());
                binding.profileName.setText(teacher.getFirstName() + " " + teacher.getLastName());
                binding.phoneNo.setText(teacher.getPhoneNumber());
            }
        });


        return binding.getRoot();
    }



}