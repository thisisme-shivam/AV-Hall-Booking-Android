package com.example.bookavhall.ui.avhall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bookavhall.R;
import com.example.bookavhall.databinding.FragmentAvHallBinding;
import com.example.bookavhall.model.AVHalls;

import java.util.ArrayList;


public class AVHallFragment extends Fragment  {


    public interface AVHallFragmentInterface{
        public void onclick(String avHallName);
    }


    private AVHallViewModel avHallViewModel;
    private AVHallAdapter avHallAdapter;
    private FragmentAvHallBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAvHallBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avHallViewModel = new ViewModelProvider(this).get(AVHallViewModel.class);
        NavController controller = Navigation.findNavController(view);
        AVHallFragmentInterface avHallFragmentInterface;
        avHallAdapter = new AVHallAdapter();
        binding.recyclerView.setAdapter(avHallAdapter);

        avHallViewModel.getAvHalls().observe(getViewLifecycleOwner(), avHalls -> {
            avHallAdapter.setHalls((ArrayList<AVHalls>) avHalls, avHallName -> {
                Bundle bundle = new Bundle();
                bundle.putString("AV Hall Name", avHallName) ;
                controller.navigate(R.id.navigation_bookavhall,bundle);
            });
            avHallAdapter.notifyDataSetChanged();

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}