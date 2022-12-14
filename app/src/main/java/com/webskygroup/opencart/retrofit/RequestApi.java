package com.webskygroup.opencart.retrofit;


import com.google.gson.JsonObject;
import com.webskygroup.opencart.models.SliderModels.SliderImageModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestApi {

    String TOKEN="754006375859";

    //get slider images
    @POST("/appnew/index.php?route=extension/api32/extension/module/banner")
    @FormUrlEncoded
    Observable<SliderImageModel> getSliderImageRf(@Field("language") String language,
                                                  @Field("id_component") String id_component);

    //get session id
    @GET("/appnew/index.php?route=extension/api32/common/session&token="+TOKEN)
    Observable<JsonObject> getSessionId();

    // login request
    @POST("/appnew/index.php?route=extension/api32/account/login&token="+TOKEN)
    @FormUrlEncoded
    Observable<JsonObject> loginRequest(@Field("username") String username,
                                        @Field("password") String password,
                                        @Field("session_id") String session_id,
                                        @Field("language") String language);

    //register request
    @POST("/appnew/index.php?route=extension/api32/account/register&token="+TOKEN)
    @FormUrlEncoded
    Observable<JsonObject> registerRequest(@Field("firstname") String firstname,
                                           @Field("lastname") String lastname,
                                           @Field("telephone") String telephone,
                                           @Field("email") String email,
                                           @Field("password") String password,
                                           @Field("language") String language);

    //get modules
    @POST("/appnew/index.php?route=extension/api32/common/module&token="+TOKEN)
    @FormUrlEncoded
    Observable<JsonObject> getModules(@Field("language") String language);

    //get horizontal home category
    @POST("/appnew/index.php?route=extension/api32/extension/module/category_top&token="+TOKEN)
    @FormUrlEncoded
    Observable<JsonObject> HorHomeCategory(@Field("language") String language,
                                           @Field("id_component") String id_component);

    @POST("/appnew/index.php?route=extension/api32/extension/module/banner")
    @FormUrlEncoded
    Observable<JsonObject> getGridBanner(@Field("language") String language,
                                         @Field("id_component") String id_component);

    @POST("/appnew/index.php?route=extension/api32/extension/module/special")
    @FormUrlEncoded
    Observable<JsonObject> getFirstHorizontalProduct(@Field("currency") String currency,
                                                     @Field("language") String language,
                                                     @Field("customer_id") String customer_id,
                                                     @Field("id_component") String id_component,
                                                     @Field("sort") String sort,
                                                     @Field("page") String page);

    @POST("appnew/index.php?route=extension/api32/extension/module/latest")
    @FormUrlEncoded
    Observable<JsonObject> getSecondGridBanner(@Field("currency") String currency,
                                                     @Field("language") String language,
                                                     @Field("customer_id") String customer_id,
                                                     @Field("id_component") String id_component,
                                                     @Field("page") String page);


    @POST("/appnew/index.php?route=extension/api32/product/product&token="+TOKEN)
    @FormUrlEncoded
    Observable<JsonObject> getProductDetails(@Field("currency") String currency,
                                               @Field("language") String language,
                                               @Field("customer_id") String customer_id,
                                               @Field("product_id") String id_component);

}
