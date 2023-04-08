package com.example.bookavhall.ui.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProifleFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<Teacher> teacherMutable;
    public ProifleFragmentViewModel(){
        teacherMutable  = new MutableLiveData<>();
        loadData();
    }

    private void loadData() {
        Log.i("fdjk",FirebaseAuth.getInstance().getUid());
        FirebaseDatabase.getInstance().getReference().child("Teachers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = new Teacher();
                if(snapshot.getValue()!=null){
                    teacher  = snapshot.getValue(Teacher.class);
                    Log.i("teacher",teacher.getFirstName());
                }
                teacherMutable.postValue(teacher);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<Teacher> getTeacher(){
        return teacherMutable;
}

}
