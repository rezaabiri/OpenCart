package com.webskygroup.opencart.models.SliderModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SliderImageModel {

    @SerializedName("click_target")
    public String click_target;
    @SerializedName("banners")
    public List<Banner> banners;
    @SerializedName("banner_design")
    public String banner_design;
    @SerializedName("result")
    public String result;

    public String getClick_target() {
        return click_target;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public String getBanner_design() {
        return banner_design;
    }

    public String getResult() {
        return result;
    }
}
