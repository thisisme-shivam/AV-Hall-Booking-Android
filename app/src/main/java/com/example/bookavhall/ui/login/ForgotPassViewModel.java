package com.example.bookavhall.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> resetLiveData;
    private MutableLiveData<String> errorLiveData;

    public ForgotPassViewModel() {
        mAuth = FirebaseAuth.getInstance();
        resetLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> getResetLiveData() {
        return resetLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void passReset(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()) {
                       resetLiveData.setValue(true);
                   }
                   else{
                       errorLiveData.setValue(task.getException().getMessage());
                   }
                });
    }


}