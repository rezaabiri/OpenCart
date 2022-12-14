package com.webskygroup.opencart.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;
import com.webskygroup.opencart.RoomDb.Entites.SliderImageEntity;
import com.webskygroup.opencart.RoomDb.RoomDao;
import com.webskygroup.opencart.models.SliderModels.SliderImageModel;
import com.webskygroup.opencart.retrofit.RequestApi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppRepository {
    RequestApi requestApi;
    RoomDao roomDao;
    SharedPreferences sharedPreferences;

    public AppRepository(RequestApi requestApi, RoomDao roomDao) {

        this.requestApi = requestApi;
        this.roomDao = roomDao;

    }

    public Future<Observable<SliderImageModel>> SliderListFutureCall(String language, String id_component){
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Callable<Observable<SliderImageModel>> myNetworkCallable = new Callable<Observable<SliderImageModel>>() {
            @Override
            public Observable<SliderImageModel> call() throws Exception {
                return requestApi.getSliderImageRf(language, id_component);
            }
        };

        final Future<Observable<SliderImageModel>> futureObservable = new Future<Observable<SliderImageModel>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b){
                    executor.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executor.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executor.isTerminated();
            }

            @Override
            public Observable<SliderImageModel> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<SliderImageModel> get(long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCallable).get(l, timeUnit);
            }
        };

        return futureObservable;
    }

    public void InsertSliderImages(SliderImageModel sliderImageModel){
        Completable.fromAction(() -> roomDao.insert(new SliderImageEntity(sliderImageModel)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("slider","completed");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public Flowable<SliderImageEntity> getSliderImages() {
        return roomDao.getAllImageSlider();
    }


     public void ReceiveSessionID(Context context){
         sharedPreferences = context.getSharedPreferences("session_id",Context.MODE_PRIVATE );

         requestApi.getSessionId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject jsonObject) throws Throwable {
                        sharedPreferences.edit().putString("session_id",jsonObject.get("session_id").getAsString()).apply();
                        Log.i("repo",jsonObject.get("session_id").getAsString());
                    }
                },throwable -> Log.e("tag", "err" + throwable));
    }

    // login request
    public void LoginRequest(String username, String password, String session_id, String language, iLoginResponse iLoginResponse){
        RequestApi loginResponse = requestApi;
        Observable<JsonObject> initiateLogin = loginResponse.loginRequest(username, password, session_id, language);
        initiateLogin.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iLoginResponse.OnResponse(jsonObject);
            }
        }, iLoginResponse::OnFailure);

    }


    // login interface
    public interface iLoginResponse {
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }


    // register request
    public void RegisterRequest(String firstname, String lastname, String telephone, String email, String password, String language, iSignUpResponse iSignUpResponse){
        RequestApi registerResponse = requestApi;
        Observable<JsonObject> initiateRegister = registerResponse.registerRequest(firstname,lastname,telephone,email,password, language);
        initiateRegister.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iSignUpResponse.OnResponse(jsonObject);
            }
        }, iSignUpResponse::OnFailure);

    }

    // register interface
    public interface iSignUpResponse {
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }


    // module request
    public void ModulesRequest(String language, iModulesResponse iModulesResponse){
        RequestApi getModulesResponse = requestApi;
        Observable<JsonObject> initiateModules = getModulesResponse.getModules(language);
        initiateModules.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iModulesResponse.OnResponse(jsonObject);
            }
        }, iModulesResponse::OnFailure);

    }

    // modules interface
    public interface iModulesResponse {
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }

    // horizontal home category request
    public void HorHomeCategory(String language, String IdComponent, iHorHomeCategoryResponse iHorHomeCategoryResponse){
        RequestApi horHomeCategory = requestApi;
        Observable<JsonObject> initiateHomeCategory = horHomeCategory.HorHomeCategory(language, IdComponent);
        initiateHomeCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iHorHomeCategoryResponse.OnResponse(jsonObject);
            }
        }, iHorHomeCategoryResponse::OnFailure);
    }


    // horizontal home category interface
    public interface iHorHomeCategoryResponse {
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }

    // first grid banner request
    public void FirstGridBannerRequest(String language, String IdComponent, iFirstGridBannerResponse iFirstGridBannerResponse){
        RequestApi gridBanner = requestApi;
        Observable<JsonObject> initiateFirstGridBanner = gridBanner.getGridBanner(language, IdComponent);
        initiateFirstGridBanner.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iFirstGridBannerResponse.OnResponse(jsonObject);
            }
        }, iFirstGridBannerResponse::OnFailure);
    }


    // grid banner interface
    public interface iFirstGridBannerResponse {
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }

    // first horizontal products
    public void FirstHorizontalProducts(String currency, String language, String customer_id, String id_component, String sort, String page, iFirstHorProduct iFirstHorProduct){
        RequestApi firstHorizontalProduct = requestApi;
        Observable<JsonObject> initiateFirstHorizontalProduct = firstHorizontalProduct.getFirstHorizontalProduct(currency, language, customer_id, id_component, sort, page);
        initiateFirstHorizontalProduct.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iFirstHorProduct.OnResponse(jsonObject);
            }
        }, iFirstHorProduct::OnFailure );
    }

    // first horizontal product interface
    public interface iFirstHorProduct{
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }

    // second grid banner
    public void SecondGridBanner(String currency, String language, String customer_id, String id_component, String page, iSecondGridBanner iSecondGridBanner){
        RequestApi secondGridBanner = requestApi;
        Observable<JsonObject> initiateSecondGridBanner = secondGridBanner.getSecondGridBanner(currency, language, customer_id, id_component, page);
        initiateSecondGridBanner.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iSecondGridBanner.OnResponse(jsonObject);
            }
        }, iSecondGridBanner::OnFailure );
    }

    // second grid banner
    public interface iSecondGridBanner{
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }

    // get product details
    public void ProductDetails(String currency, String language, String customer_id, String product_id, iProductDetails iProductDetails){
        RequestApi productDetails = requestApi;
        Observable<JsonObject> initiateProductDetail = productDetails.getProductDetails(currency, language, customer_id, product_id);
        initiateProductDetail.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonObject>() {
            @Override
            public void accept(JsonObject jsonObject) throws Throwable {
                iProductDetails.OnResponse(jsonObject);
            }
        }, iProductDetails::OnFailure );
    }

    // product details interface
    public interface iProductDetails{
        void OnResponse(JsonObject jsonObject);
        void OnFailure(Throwable t);
    }


}
