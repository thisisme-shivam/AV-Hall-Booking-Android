package com.example.bookavhall.Fragments;

import static android.content.ContentValues.TAG;

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

import com.example.bookavhall.Activities.LoginActivity;
import com.example.bookavhall.Activities.MainActivity;
import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        binding.ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections directions = LoginFragmentDirections.actionLoginFragmentToForgotPassFrag();
                controller.navigate(directions);
            }
        });

        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.EmailET.getText().toString().trim();
                String pass = binding.PasswordET.getText().toString().trim();

                Log.d(email, "onClick: pressed");
                Log.d(pass, "onClick: pressed");

                if(TextUtils.isEmpty(email)) {
                    binding.EmailET.setError("Email cannot be empty");
                    binding.EmailET.requestFocus();
                }
                else if(!email.contains("@gmail.com")) {
                    binding.EmailET.setError("Email is invalid");
                    binding.EmailET.requestFocus();
                }
                else if(TextUtils.isEmpty(pass)) {
                    binding.PasswordET.setError("Password cannot be empty");
                    binding.EmailET.requestFocus();
                }

                else {
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finishAffinity();
                                }
                                else {
                                    Toast.makeText(getContext(), "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }
}