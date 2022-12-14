package com.webskygroup.opencart.models.SliderModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Banner implements Parcelable {

    @SerializedName("title")
    public String title;
    @SerializedName("link")
    public String link;
    @SerializedName("category")
    public String category;
    @SerializedName("imageurl")
    public String imageurl;
    @SerializedName("size")
    public String size;
    @SerializedName("sort")
    public int sort;

    protected Banner(Parcel in) {
        title = in.readString();
        link = in.readString();
        category = in.readString();
        imageurl = in.readString();
        size = in.readString();
        sort = in.readInt();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(category);
        dest.writeString(imageurl);
        dest.writeString(size);
        dest.writeInt(sort);
    }
}
