package com.webskygroup.opencart.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.webskygroup.opencart.RoomDb.Converters.SliderImageConverter;
import com.webskygroup.opencart.RoomDb.Entites.SliderImageEntity;

@TypeConverters({SliderImageConverter.class})
@Database(entities = {SliderImageEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public static final String Db_Name = "AppDb";
    public static AppDataBase instance;
    public abstract RoomDao roomDao();

    public static synchronized AppDataBase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class, Db_Name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
