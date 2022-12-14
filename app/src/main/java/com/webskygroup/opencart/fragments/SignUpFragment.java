package com.webskygroup.opencart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.FragmentSignUpBinding;
import com.webskygroup.opencart.repositories.AppRepository;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    private SavedStateHandle savedStateHandle;

    @Inject
    AppRepository appRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HaveAccountText();
        setUpViewModel();
        Register("fa-ir");

        savedStateHandle = Navigation.findNavController(view)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set("success", false);
    }

    private void setUpViewModel(){
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        compositeDisposable = new CompositeDisposable();
        sharedPreferences = getActivity().getSharedPreferences("session_id", Context.MODE_PRIVATE);

    }

    private void Register(String language){
        binding.buttonSignUp.setOnClickListener(v -> {
            appViewModel.RegisterRequest(
                    binding.editFirstname.getText().toString(),
                    binding.editLastname.getText().toString(),
                    binding.editPhoneNumber.getText().toString(),
                    binding.editEmailSignup.getText().toString(),
                    binding.editPassword.getText().toString(),
                    language, new AppViewModel.vmRegisterResponse() {
                        @Override
                        public void onResponse(JsonObject jsonObject) {
                            Toast.makeText(getActivity(), "msg: "+jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            //NavHostFragment.findNavController(getParentFragment()).popBackStack();
                        }
                        @Override
                        public void onFailure(Throwable t) {}
                    });
        });

    }

    /*
    private void PostUserPassToLoginFrag(View view, String username, String password){
        SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
        action.setPassword(password);
        action.setUsername(username);
        Navigation.findNavController(view).navigate(action);
    }

     */

    private void HaveAccountText(){
        binding.doYouHaveAccount.setOnClickListener(v -> {
            NavHostFragment.findNavController(getParentFragment()).popBackStack();
        });
    }
}