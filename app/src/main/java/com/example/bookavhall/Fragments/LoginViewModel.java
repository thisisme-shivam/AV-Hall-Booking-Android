package com.example.bookavhall.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<String> errorLiveData;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }
    public LiveData<FirebaseUser> getUserLiveData() {
        return userMutableLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        userMutableLiveData.setValue(mAuth.getCurrentUser());
                    }
                    else{
                        errorLiveData.setValue(task.getException().getMessage());
                    }
                });
    }

}