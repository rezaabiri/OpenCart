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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webskygroup.opencart.Hilt.Modules.SessionManager;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.RoomDb.RoomDao;
import com.webskygroup.opencart.databinding.FragmentSignInBinding;
import com.webskygroup.opencart.repositories.AppRepository;
import com.webskygroup.opencart.retrofit.RequestApi;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewModel();
        CheckInputs();
        Login();
        DoNotHaveAccountText(view);
        //ReceiveInfoArgs();
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


    private void Login(){
        binding.buttonLogin.setOnClickListener(v -> {
            appViewModel.LoginRequest(
                    binding.editEmailLogin.getText().toString(),
                    binding.editPasswordLogin.getText().toString(),
                    sharedPreferences.getString("session_id", ""),"fa-ir", new AppViewModel.vmLoginResponse() {
                        @Override
                        public void onResponse(JsonObject jsonObject) {
                            Toast.makeText(getActivity(), "msg: "+jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("sff",jsonObject.toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.i("sfferr",t.getMessage());
                        }
                    });

            appViewModel.getResultLogin().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("success")){
                        savedStateHandle.set("success", true);
                        Log.i("state",s);
                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                        //Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_profileFragment);
                    }else {
                        if (!s.equals("checking")){
                            Toast.makeText(getActivity(), "state: "+s, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        });

    }

    /*
    private void ReceiveInfoArgs(){
        Bundle arguments = new Bundle();
        if (arguments == null){
            Log.i("args","null");
        }

        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(arguments);

        if (args.getPassword()==null || args.getUsername()==null){
            Log.i("args","empty");
        }else {
            binding.editEmailLogin.setText(args.getUsername());
            binding.editPasswordLogin.setText(args.getPassword());
        }

    }

     */

    private void CheckInputs(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[a-z])(?=\\S+$).{4,}$";
        binding.editEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.editEmailLogin.getText().toString().matches(emailPattern) && s.length() > 0) {
                    binding.emailLoginEditText.setErrorEnabled(true);
                    binding.emailLoginEditText.setError(getText(R.string.email_not_valid));
                }
                else {
                    binding.emailLoginEditText.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editPasswordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.editPasswordLogin.getText().toString().matches(passwordPattern)) {
                    binding.passwordLoginEditText.setErrorEnabled(true);
                    binding.passwordLoginEditText.setError(getText(R.string.password_not_valid));
                }
                else {
                    binding.passwordLoginEditText.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void DoNotHaveAccountText(View view){
        binding.dontHaveAccount.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
        });
    }
}