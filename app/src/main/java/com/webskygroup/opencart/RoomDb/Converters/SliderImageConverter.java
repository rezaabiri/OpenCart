package com.webskygroup.opencart.RoomDb.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webskygroup.opencart.models.SliderModels.SliderImageModel;

import java.lang.reflect.Type;

public class SliderImageConverter {
    @TypeConverter
    public String toJson(SliderImageModel sliderImageModel) {
        if (sliderImageModel == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<SliderImageModel>() {}.getType();
        String json = gson.toJson(sliderImageModel, type);
        return json;
    }

    @TypeConverter
    public SliderImageModel toDataClass(String sImageModel) {
        if (sImageModel == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<SliderImageModel>() {}.getType();
        SliderImageModel sliderImageModel = gson.fromJson(sImageModel, type);
        return sliderImageModel;
    }
}
