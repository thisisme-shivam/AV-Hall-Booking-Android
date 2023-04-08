package com.example.bookavhall.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookavhall.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<Teacher> teacherMutable;
    public ProfileFragmentViewModel(){
        teacherMutable  = new MutableLiveData<>();
        loadData();
    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference().child("Teachers").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = new Teacher();
                if(snapshot.getValue()!=null){
                    teacher  = snapshot.getValue(Teacher.class);
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