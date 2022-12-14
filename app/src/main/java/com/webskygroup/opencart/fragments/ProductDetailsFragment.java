package com.webskygroup.opencart.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.gson.JsonObject;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.FragmentProductDetailsBinding;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import at.blogc.android.views.ExpandableTextView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProductDetailsFragment extends Fragment {

    FragmentProductDetailsBinding binding;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPrefsLang;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_details,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewModel();

        binding.shimmerText.startShimmerAnimation();


        sharedPrefsLang = getActivity().getSharedPreferences("language_app",Context.MODE_PRIVATE);
        String language = sharedPrefsLang.getString("lang_prefs_name","en");
        String currency = sharedPrefsLang.getString("currency","TOM");

        if (language.equals("fa")){
            language="fa-ir";
        }else if (language.equals("en")){
            language="en-gb";
        }else {
            language="ar";
        }


        String product_id = getArguments().getString("product_id");
        getProductDetails(currency,language, "192",product_id);
    }

    //setup ViewModel
    private void setUpViewModel(){
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        compositeDisposable = new CompositeDisposable();
        sharedPreferences = getActivity().getSharedPreferences("session_id", Context.MODE_PRIVATE);

    }


    private void getProductDetails(String currency, String language, String customer_id, String product_id){

        appViewModel.ProductDetails(currency, language, customer_id, product_id, new AppViewModel.vmProductDetails() {
            @Override
            public void onResponse(JsonObject jsonObject) {

                // set product name
                binding.productName.setText(jsonObject.get("name").getAsString());
                //set rate product
                binding.productRate.setRating((float) jsonObject.get("rating").getAsFloat());
                binding.rateNumber.setText("("+jsonObject.get("rating").getAsString()+")");
                //set follower shop
                binding.storeFollowers.setText(jsonObject.get("followerstotal").getAsString());
                //set products count
                binding.storeProducts.setText(jsonObject.get("totalproducts").getAsString());
                // set seller profile image
                Picasso.get().load(jsonObject.get("sellerimage").getAsString()).placeholder(R.drawable.placeholder).into(binding.storeImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.storeImage.setAlpha(0f);
                        binding.storeImage.animate().setDuration(500).alpha(1f).start();
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });

                Picasso.get().load(jsonObject.get("thumb").getAsString()).placeholder(R.drawable.placeholder).into(binding.imageProductDetails, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.storeImage.setAlpha(0f);
                        binding.storeImage.animate().setDuration(500).alpha(1f).start();
                    }
                    @Override
                    public void onError(Exception e) {
                    }
                });
            }



            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void loadStoreImage(int store_image, int target_imageview, View view){

    }
}