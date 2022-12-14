package com.webskygroup.opencart.RoomDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.webskygroup.opencart.RoomDb.Entites.SliderImageEntity;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SliderImageEntity sliderImageEntity);

    @Query("SELECT * FROM Slider")
    Flowable<SliderImageEntity> getAllImageSlider();
}
