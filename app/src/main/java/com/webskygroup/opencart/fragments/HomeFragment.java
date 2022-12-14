package com.webskygroup.opencart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.webskygroup.opencart.CToast;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.RoomDb.Entites.SliderImageEntity;
import com.webskygroup.opencart.adapters.FirstGridBannerAdapter.FirstGridBannerAdapter;
import com.webskygroup.opencart.adapters.FirstHorProductAdapter.FirstHorProductAdapter;
import com.webskygroup.opencart.adapters.HorizontalHomeCategory.HorizontalHomeCategoryAdapter;
import com.webskygroup.opencart.adapters.SecondGridBannerAdapter.SecondGridBannerAdapter;
import com.webskygroup.opencart.adapters.SliderAdapter.SliderAdapter;
import com.webskygroup.opencart.databinding.FragmentHomeBinding;
import com.webskygroup.opencart.models.FirstGridBanner.FirstGridBannerModel;
import com.webskygroup.opencart.models.FirstHorProduct.FirstHorProductModel;
import com.webskygroup.opencart.models.HorizontalHomeCategory.Category;
import com.webskygroup.opencart.models.SecondGridBanner.SecondGridBannerModel;
import com.webskygroup.opencart.models.SliderModels.Banner;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    public static String quot="\"";

    FragmentHomeBinding binding;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    // Adapters
    SliderAdapter sliderAdapter;
    HorizontalHomeCategoryAdapter homeCategoryAdapter;
    FirstGridBannerAdapter firstGridBannerAdapter;
    FirstHorProductAdapter firstHorProductAdapter;
    SecondGridBannerAdapter secondGridBannerAdapter;

    // Lists
    List<Banner> banners = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<FirstGridBannerModel> gridBannerModels = new ArrayList<>();
    List<FirstHorProductModel> firstHorProductModels = new ArrayList<>();
    List<SecondGridBannerModel> secondGridBannerModels = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPrefsLang;

    Handler handler = new Handler();

    private String EVENT_DATE_TIME = "2022-12-10 10:30:00";
    private String END = "2022-12-30 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Runnable runnable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        setUpViewModel();
        setUpSearchBar(language);
        setUpSlider();
        setUpHorHomeCategory(language,"95");
        setUpGridBanner(language,"78");
        setUpFirstHorProducts(currency,language,"192","148","1","1");
        setUpSecondGridBanner(currency,language,"192","96","1");
        countDownStart();

    }

    //setup ViewModel
    private void setUpViewModel(){
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        compositeDisposable = new CompositeDisposable();
        sharedPreferences = getActivity().getSharedPreferences("session_id", Context.MODE_PRIVATE);

    }

    //setup SearchBox
    private void setUpSearchBar(String language){
        appViewModel.ModuleRequest(language, new AppViewModel.vmModulesResponse() {
            @Override
            public void onResponse(JsonObject jsonObject) {
                Log.i("icon",jsonObject.get("storeicon").getAsString());

                String url = jsonObject.get("storeicon").getAsString();

                //Glide.with(getActivity()).load(url).into(binding.opencartLogoSearch);
                Picasso.get().load(url).into(binding.opencartLogoSearch);

                //Glide.with(getContext()).load(jsonObject.get("storeicon").getAsString()).into(binding.testlogo);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //Slider Homepage
    public void setUpSlider(){
        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.viewPagerSlider,R.layout.layout_slider_items,3);
        skeleton.showSkeleton();
        Disposable disposable = appViewModel.getSliderImagesVmFlow()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SliderImageEntity>() {
                    @Override
                    public void accept(SliderImageEntity sliderImageEntity) throws Throwable {
                        ArrayList<String> arrayList = new ArrayList<>();

                        try {
                            for (int i = 0; i < sliderImageEntity.getSliderImageModel().getBanners().size(); i++) {
                                banners.add(sliderImageEntity.getSliderImageModel().getBanners().get(i));
                                //String link = sliderImageEntity.getSliderImageModel().getBanners().get(i).link;
                                arrayList.add(sliderImageEntity.getSliderImageModel().getBanners().get(i).imageurl);

                                if (binding.viewPagerSlider.getAdapter() != null) {
                                    sliderAdapter  = (SliderAdapter) binding.viewPagerSlider.getAdapter();
                                    //skeleton.showOriginal();
                                } else {
                                    sliderAdapter = new SliderAdapter(arrayList, binding.viewPagerSlider);
                                    binding.viewPagerSlider.setAdapter(sliderAdapter);
                                    binding.viewPagerSlider.getViewTreeObserver().addOnPreDrawListener(
                                            new ViewTreeObserver.OnPreDrawListener() {

                                                @Override
                                                public boolean onPreDraw() {
                                                    binding.viewPagerSlider.getViewTreeObserver().removeOnPreDrawListener(this);

                                                    for (int i = 0; i < binding.viewPagerSlider.getChildCount(); i++) {
                                                        View v = binding.viewPagerSlider.getChildAt(i);
                                                        v.setAlpha(0f);
                                                        v.animate().alpha(1f)
                                                                .setDuration(1000)
                                                                .start();
                                                    }

                                                    return true;
                                                }
                                            });
                                    //skeleton.showOriginal();
                                }



                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        binding.viewPagerSlider.setClipToPadding(false);
                        binding.viewPagerSlider.setClipChildren(false);
                        binding.viewPagerSlider.setOffscreenPageLimit(3);
                        binding.viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View page, float position) {
                                float r = 1 - Math.abs(position);
                                page.setScaleY(0.85f + r * 0.15f);
                            }
                        });
                        binding.viewPagerSlider.setPageTransformer(compositePageTransformer);
                        binding.viewPagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                            @Override
                            public void onPageSelected(int position) {
                                super.onPageSelected(position);
                                handler.removeCallbacks(sliderRunnable);
                                handler.postDelayed(sliderRunnable,3000);

                            }
                        });




                    }
                });
        //skeleton.showOriginal();
        compositeDisposable.add(disposable);
        skeleton.showOriginal();


    }
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewPagerSlider.setCurrentItem(binding.viewPagerSlider.getCurrentItem() + 1);
        }
    };

    // Horizontal Home Category
    private void setUpHorHomeCategory(String language, String IdComponent){
        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.horizontalHomeCategoryRecyclerview,R.layout.layout_horizontal_home_category,8);
        skeleton.setMaskCornerRadius(30);
        skeleton.showSkeleton();
        appViewModel.HorHomeCategoryRequest(language, IdComponent, new AppViewModel.vmHorHomeCategoryResponse() {
            @Override
            public void onResponse(JsonObject jsonObject) {
                categories.clear();
                JsonArray jsonArray = jsonObject.getAsJsonArray("categories");
                try {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Category category = new Category();
                        Category.RootCategory rootCategory = new Category.RootCategory();
                        rootCategory.setShow_category(jsonObject.get("show_category").getAsString());
                        rootCategory.setFirst_category_id(jsonObject.get("first_category_id").getAsString());
                        rootCategory.setShow_category_inhome(jsonObject.get("show_category_inhome").getAsInt());
                        JsonObject js = jsonArray.get(i).getAsJsonObject();
                        category.setCategory_name(js.get("name").getAsString());
                        category.setCategory_id(js.get("category_id").getAsString());
                        category.setImageurl(js.get("imageurl").toString().replace(quot,""));
                        categories.add(category);
                        homeCategoryAdapter = new HorizontalHomeCategoryAdapter(categories);
                        binding.horizontalHomeCategoryRecyclerview.setAdapter(homeCategoryAdapter);

                        binding.horizontalHomeCategoryRecyclerview.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {

                                    @Override
                                    public boolean onPreDraw() {
                                        binding.horizontalHomeCategoryRecyclerview.getViewTreeObserver().removeOnPreDrawListener(this);

                                        for (int i = 0; i < binding.horizontalHomeCategoryRecyclerview.getChildCount(); i++) {
                                            View v = binding.horizontalHomeCategoryRecyclerview.getChildAt(i);
                                            v.setAlpha(0f);
                                            v.animate().alpha(1f)
                                                    .setDuration(1000)
                                                    .start();
                                        }

                                        return true;
                                    }
                                });

                        //setUpGridBanner(language,"87");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                CToast.CToast(getContext(),R.string.networkError);
            }
        });
    }

    // first grid banner
    private void setUpGridBanner(String language, String IdComponent){
        //skeleton = SkeletonLayoutUtils.applySkeleton(binding.gridBannerRecyclerview, R.layout.layout_grid_banner);
        final Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.gridBannerRecyclerview,R.layout.skeleton_first_grid_banner,4);
        skeleton.setMaskCornerRadius(30);

        skeleton.showSkeleton();

        appViewModel.FirstGridBannerRequest(language, IdComponent, new AppViewModel.vmGridBannerResponse() {
            @Override
            public void onResponse(JsonObject jsonObject) {
                gridBannerModels.clear();
                JsonArray jsonArray = jsonObject.getAsJsonArray("banners");
                try {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        FirstGridBannerModel firstGridBannerModel = new FirstGridBannerModel();
                        FirstGridBannerModel.GridBannerRoot gridBannerRoot = new FirstGridBannerModel.GridBannerRoot();

                        JsonObject js = jsonArray.get(i).getAsJsonObject();
                        Log.i("imggg",js.toString());
                        firstGridBannerModel.setImageurl(js.get("imageurl").getAsString());
                        gridBannerModels.add(firstGridBannerModel);
                        firstGridBannerAdapter = new FirstGridBannerAdapter(gridBannerModels, getContext());
                        binding.gridBannerRecyclerview.setAdapter(firstGridBannerAdapter);
                        binding.gridBannerRecyclerview.setHasFixedSize(true);
                        binding.gridBannerRecyclerview.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {

                                    @Override
                                    public boolean onPreDraw() {
                                        binding.gridBannerRecyclerview.getViewTreeObserver().removeOnPreDrawListener(this);

                                        for (int i = 0; i < binding.gridBannerRecyclerview.getChildCount(); i++) {
                                            View v = binding.gridBannerRecyclerview.getChildAt(i);
                                            v.setAlpha(0f);
                                            v.animate().alpha(1f)
                                                    .setDuration(1000)
                                                    .start();
                                        }

                                        return true;
                                    }
                                });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                CToast.CToast(getContext(),R.string.networkError);
            }
        });
    }

    // first horizontal product
    private void setUpFirstHorProducts(String currency, String language, String customer_id, String id_component, String sort, String page){
        final Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.specialProductRecyclerview,R.layout.skeleton_product_items,6);
        skeleton.setMaskCornerRadius(30);
        skeleton.showSkeleton();

        appViewModel.FirstHorizontalProducts(currency, language, customer_id, id_component, sort, page, new AppViewModel.vmFirstHorProduct() {
            @Override
            public void onResponse(JsonObject jsonObject) {
                firstHorProductModels.clear();
                JsonArray jsonArray = jsonObject.getAsJsonArray("products");
                try {
                    for (int i = 0; i < jsonArray.size(); i++) {

                        FirstHorProductModel firstHorProductModel = new FirstHorProductModel();
                        FirstHorProductModel.FirstHorProductRoot firstHorProductRoot = new FirstHorProductModel.FirstHorProductRoot();
                        JsonObject js = jsonArray.get(i).getAsJsonObject();
                        firstHorProductRoot.setBanner_heading(jsonObject.get("banner_heading").getAsString());

                        // set title to first horizontal product head
                        binding.firstHorizontalProductHeading.setText(firstHorProductRoot.banner_heading.toString());

                        Log.i("specialpro",js.get("date_start").getAsString());

                        firstHorProductModel.setId(js.get("id").getAsString());
                        firstHorProductModel.setImageurl(js.get("imageurl").getAsString());
                        firstHorProductModel.setName(js.get("name").getAsString());
                        firstHorProductModel.setPrice(js.get("price").getAsString());
                        firstHorProductModel.setSpecial_price(js.get("special_price").getAsString());
                        firstHorProductModel.setPercentage(js.get("percentage").getAsDouble());


                        firstHorProductModels.add(firstHorProductModel);

                        firstHorProductAdapter = new FirstHorProductAdapter(firstHorProductModels);
                        binding.specialProductRecyclerview.setAdapter(firstHorProductAdapter);
                        binding.specialProductRecyclerview.setHasFixedSize(true);

                        binding.specialProductRecyclerview.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {

                                    @Override
                                    public boolean onPreDraw() {
                                        binding.specialProductRecyclerview.getViewTreeObserver().removeOnPreDrawListener(this);

                                        for (int i = 0; i < binding.specialProductRecyclerview.getChildCount(); i++) {
                                            View v = binding.specialProductRecyclerview.getChildAt(i);
                                            v.setAlpha(0f);
                                            v.animate().alpha(1f)
                                                    .setDuration(1000)
                                                    .start();
                                        }

                                        return true;
                                    }
                                });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

    // second grid banner
    private void setUpSecondGridBanner(String currency, String language, String customer_id, String id_component, String page){
        final Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.secondGridBannerRecyclerview,R.layout.skeleton_second_grid_banner,9);
        skeleton.setMaskCornerRadius(30);
        skeleton.showSkeleton();

        appViewModel.SecondGridBanner(currency, language, customer_id, id_component, page, new AppViewModel.vmSecondGridBanner() {
            @Override
            public void onResponse(JsonObject jsonObject) {
                secondGridBannerModels.clear();
                JsonArray jsonArray = jsonObject.getAsJsonArray("products");
                try {
                    for (int i = 0; i < jsonArray.size(); i++) {

                        SecondGridBannerModel secondGridBannerModel = new SecondGridBannerModel();
                        SecondGridBannerModel.SecondGridBannerRoot secondGridBannerRoot = new SecondGridBannerModel.SecondGridBannerRoot();
                        JsonObject js = jsonArray.get(i).getAsJsonObject();
                        //secondGridBannerRoot.setBanner_heading(jsonObject.get("banner_heading").getAsString());

                        // set title to first horizontal product head
                        //binding.secondGridBannerOnDeskTxt.setText(secondGridBannerRoot.banner_heading.toString());

                        //Log.i("specialpro",secondGridBannerRoot.banner_heading);
                        Log.i("second",js.toString());

                        secondGridBannerModel.setImageurl(js.get("imageurl").getAsString());
                        secondGridBannerModel.setName(js.get("name").getAsString());
                        secondGridBannerModel.setPrice(js.get("price").getAsString());
                        //secondGridBannerModel.setSpecial_price(js.get("special_price").getAsString());
                        //secondGridBannerModel.setPercentage(js.get("percentage").getAsDouble());


                        secondGridBannerModels.add(secondGridBannerModel);

                        secondGridBannerAdapter = new SecondGridBannerAdapter(secondGridBannerModels);
                        binding.secondGridBannerRecyclerview.setAdapter(secondGridBannerAdapter);
                        binding.secondGridBannerRecyclerview.setHasFixedSize(true);

                        binding.secondGridBannerRecyclerview.getViewTreeObserver().addOnPreDrawListener(
                                new ViewTreeObserver.OnPreDrawListener() {

                                    @Override
                                    public boolean onPreDraw() {
                                        binding.secondGridBannerRecyclerview.getViewTreeObserver().removeOnPreDrawListener(this);

                                        for (int i = 0; i < binding.secondGridBannerRecyclerview.getChildCount(); i++) {
                                            View v = binding.secondGridBannerRecyclerview.getChildAt(i);
                                            v.setAlpha(0f);
                                            v.animate().alpha(1f)
                                                    .setDuration(1000)
                                                    .start();
                                        }

                                        return true;
                                    }
                                });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = dateFormat.parse(END);
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //

                        String time = String.format("%02d", Days) + String.format("%02d", Hours) + String.format("%02d", Minutes) + String.format("%02d", Seconds);
                        binding.secondGridBannerOnDeskTxt.setText(time);
                    } else {

                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

