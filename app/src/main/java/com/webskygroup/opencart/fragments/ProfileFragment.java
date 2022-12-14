package com.webskygroup.opencart.fragments;

import static com.webskygroup.opencart.activities.MainActivity.navController;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.FragmentProfileBinding;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewModel();
        CheckLogin(view);
        //Navigation.findNavController(view).navigate(R.id.signInFragment);

    }

    private void setUpViewModel(){
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        compositeDisposable = new CompositeDisposable();

        sharedPreferences = getActivity().getSharedPreferences("session_id", Context.MODE_PRIVATE);

        //Log.i("profile",sharedPreferences.getString("session_id",""));

    }

    private void CheckLogin(View view){
        appViewModel.getResultLogin().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("state",s);
                if (s.equals("failure") || s.equals("checking")){
                    Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_signInFragment);
                }
            }
        });
    }

}