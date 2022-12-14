package com.webskygroup.opencart.RoomDb.Entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.webskygroup.opencart.models.SliderModels.SliderImageModel;

@Entity(tableName = "Slider")
public class SliderImageEntity {

    @PrimaryKey
    public int uid;


    @ColumnInfo(name = "Slider")
    public SliderImageModel sliderImageModel;

    public SliderImageEntity(SliderImageModel sliderImageModel) {
        this.sliderImageModel = sliderImageModel;
    }

    public int getUid() {
        return uid;
    }

    public SliderImageModel getSliderImageModel() {
        return sliderImageModel;
    }
}
