package com.webskygroup.opencart.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.webskygroup.opencart.RoomDb.Entites.SliderImageEntity;
import com.webskygroup.opencart.models.SliderModels.SliderImageModel;
import com.webskygroup.opencart.repositories.AppRepository;

import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class AppViewModel extends AndroidViewModel {

    @Inject
    AppRepository appRepository;

    MutableLiveData<String> mLoginMutableData = new MutableLiveData<>();

    @Inject
    public AppViewModel(@NonNull Application application) {
        super(application);
        mLoginMutableData.postValue("checking");
    }


    public Future<Observable<SliderImageModel>> sliderFutureCallVm(String language, String id_component){
        return appRepository.SliderListFutureCall(language, id_component);
    }

    public void InsertSliderImagesVm(SliderImageModel sliderImageModel){
        appRepository.InsertSliderImages(sliderImageModel);
    }

    public Flowable<SliderImageEntity> getSliderImagesVmFlow() {
        return appRepository.getSliderImages();
    }


    public void getSessionID(Context context){
        appRepository.ReceiveSessionID(context);
    }

    public void LoginRequest(String username, String password, String session_id, String language, vmLoginResponse vmLoginResponse){
        appRepository.LoginRequest(username, password, session_id, language, new AppRepository.iLoginResponse() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmLoginResponse.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmLoginResponse.onFailure(t);
            }
        });
    }

    // login interface
    public interface vmLoginResponse {
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }

    public void RegisterRequest(String firstname, String lastname, String telephone, String email, String password, String language, vmRegisterResponse vmRegisterResponse){

        appRepository.RegisterRequest(firstname, lastname, telephone, email, password, language, new AppRepository.iSignUpResponse() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmRegisterResponse.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmRegisterResponse.onFailure(t);
            }
        });
    }

    // register interface
    public interface vmRegisterResponse {
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }


    public void ModuleRequest(String language, vmModulesResponse vmModulesResponse){
        appRepository.ModulesRequest(language, new AppRepository.iModulesResponse() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmModulesResponse.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmModulesResponse.onFailure(t);
            }
        });
    }

    // modules interface
    public interface vmModulesResponse {
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }

    public void HorHomeCategoryRequest(String language, String IdComponent, vmHorHomeCategoryResponse vmHorHomeCategoryResponse){
        appRepository.HorHomeCategory(language, IdComponent, new AppRepository.iHorHomeCategoryResponse() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmHorHomeCategoryResponse.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmHorHomeCategoryResponse.onFailure(t);
            }
        });
    }

    // horizontal home category
    public interface vmHorHomeCategoryResponse {
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }


    public void FirstGridBannerRequest(String language, String IdComponent, vmGridBannerResponse vmGridBannerResponse ){
        appRepository.FirstGridBannerRequest(language, IdComponent, new AppRepository.iFirstGridBannerResponse() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmGridBannerResponse.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmGridBannerResponse.onFailure(t);
            }
        });
    }

    // grid banner
    public interface vmGridBannerResponse {
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }

    // first horizontal products
    public void FirstHorizontalProducts(String currency, String language, String customer_id, String id_component, String sort, String page, vmFirstHorProduct vmFirstHorProduct){
        appRepository.FirstHorizontalProducts(currency, language, customer_id, id_component, sort, page, new AppRepository.iFirstHorProduct() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmFirstHorProduct.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmFirstHorProduct.onFailure(t);
            }
        });
    }

    // first horizontal product
    public interface vmFirstHorProduct{
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }


    // first horizontal products
    public void SecondGridBanner(String currency, String language, String customer_id, String id_component, String page, vmSecondGridBanner vmSecondGridBanner){
        appRepository.SecondGridBanner(currency, language, customer_id, id_component, page, new AppRepository.iSecondGridBanner() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmSecondGridBanner.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmSecondGridBanner.onFailure(t);
            }
        });
    }

    // first horizontal product
    public interface vmSecondGridBanner{
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }

    // product details
    public void ProductDetails(String currency, String language, String customer_id, String product_id, vmProductDetails vmProductDetails){
        appRepository.ProductDetails(currency, language, customer_id, product_id, new AppRepository.iProductDetails() {
            @Override
            public void OnResponse(JsonObject jsonObject) {
                vmProductDetails.onResponse(jsonObject);
            }

            @Override
            public void OnFailure(Throwable t) {
                vmProductDetails.onFailure(t);
            }
        });
    }

    // product details interface
    public interface vmProductDetails{
        void onResponse(JsonObject jsonObject);
        void onFailure(Throwable t);
    }



    public LiveData<String> getResultLogin(){
        return mLoginMutableData;
    }

}
