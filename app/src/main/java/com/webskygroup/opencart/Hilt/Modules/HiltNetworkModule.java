package com.webskygroup.opencart.Hilt.Modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webskygroup.opencart.RoomDb.RoomDao;
import com.webskygroup.opencart.fragments.SignInFragment;
import com.webskygroup.opencart.repositories.AppRepository;
import com.webskygroup.opencart.retrofit.RequestApi;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class HiltNetworkModule {

    String Base_url = "https://opencart-ir.com";

    @Provides
    @Singleton
    OkHttpClient okHttpClient(){
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    @Provides
    @Singleton
    Retrofit ProvideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    @Provides
    @Singleton
    RequestApi ProvideRequestApi(Retrofit retrofit) {
        return retrofit.create(RequestApi.class);
    }

    @Provides
    @Singleton
    AppRepository ProvideAppRepository(RequestApi requestApi, RoomDao roomDao){
        return new AppRepository(requestApi, roomDao);
    }


}
