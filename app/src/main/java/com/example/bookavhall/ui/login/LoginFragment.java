package com.example.bookavhall.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.bookavhall.activities.HomeActivity;
import com.example.bookavhall.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    FragmentLoginBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        NavController controller = Navigation.findNavController(view);


        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finishAffinity();
        });

        mViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), "Email or Password incorrect", Toast.LENGTH_SHORT).show();
        });

        binding.LoginBtn.setOnClickListener(view1 -> {
            String email = binding.EmailET.getText().toString().trim();
            String pass = binding.PasswordET.getText().toString().trim();

            Log.d(email, "onClick: pressed");
            Log.d(pass, "onClick: pressed");

            if(TextUtils.isEmpty(email)) {
                binding.EmailET.setError("Email cannot be empty");
                binding.EmailET.requestFocus();
            }
            else if(TextUtils.isEmpty(pass)) {
                binding.PasswordET.setError("Password cannot be empty");
                binding.EmailET.requestFocus();
            }
            else{
                mViewModel.loginUser(email, pass);
            }
        });

        binding.ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections directions = LoginFragmentDirections.actionLoginFragmentToForgotPassFrag();
                controller.navigate(directions);
            }
        });

    }
}