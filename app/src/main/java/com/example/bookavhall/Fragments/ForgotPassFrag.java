package com.example.bookavhall.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentForgotPassBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFrag extends Fragment {

    private ForgotPassViewModel mViewModel;
    private FragmentForgotPassBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPassBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController controller = Navigation.findNavController(view);

        mViewModel =  new ViewModelProvider(this).get(ForgotPassViewModel.class);

        mViewModel.getResetLiveData().observe(getViewLifecycleOwner(), aBoolean -> {
            Toast.makeText(getContext(), "Link sent successfully", Toast.LENGTH_SHORT).show();
            NavDirections directions = ForgotPassFragDirections.actionForgotPassFragToLoginFragment();
            controller.navigate(directions);
        });

        mViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), "Error sending reset link", Toast.LENGTH_SHORT).show();
        });

        binding.sendLinkBtn.setOnClickListener(view1 -> {
            String email = binding.email.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                binding.email.setError("Email cannot be empty");
                binding.email.requestFocus();
            }
            else{
                mViewModel.passReset(email);
            }
        });
    }
}