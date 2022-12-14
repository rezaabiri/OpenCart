package com.webskygroup.opencart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;

import com.webskygroup.opencart.ContextWrapper;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.ActivityMainBinding;
import com.webskygroup.opencart.models.SliderModels.SliderImageModel;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    ActivityMainBinding activityMainBinding;
    NavHostFragment navHostFragment;
    public static NavController navController;
    AppBarConfiguration appBarConfiguration;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;
    SharedPreferences preferences;
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupNavigationComponent();

        setupViewModel();
        CacheSliderImages();

        appViewModel.getSessionID(this);
        preferences = this.getSharedPreferences("language_app",MODE_PRIVATE);
        preferences.edit().putString("lang_prefs_name","fa").apply();
        preferences.edit().putString("currency","USD").apply();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color_main_1));
        }

    }


    private void setupViewModel() {
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        compositeDisposable = new CompositeDisposable();
    }

    private void CacheSliderImages(){
        Observable.interval(5, TimeUnit.SECONDS)
                .flatMap(n -> appViewModel.sliderFutureCallVm("ar", "90").get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SliderImageModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SliderImageModel sliderImageModel) {
                        appViewModel.InsertSliderImagesVm(sliderImageModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("err", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "onComplete: ");
                    }
                });
    }

    private void setupNavigationComponent () {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.categoryFragment, R.id.cartFragment, R.id.profileFragment).build();

        setupSmoothBottomMenu();

    }
    private void setupSmoothBottomMenu() {
        PopupMenu popupMenu = new PopupMenu( this, null);
        popupMenu.inflate(R.menu.bottom_navigation_items);
        Menu menu = popupMenu.getMenu();
        activityMainBinding.bottomBar.setupWithNavController(menu, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || navController.navigateUp();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextWrapper.wrap(newBase));
    }

}