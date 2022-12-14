package com.webskygroup.opencart.models.HorizontalHomeCategory;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {
    @SerializedName("name")
    public String category_name;
    @SerializedName("category_id")
    public String category_id;
    @SerializedName("imageurl")
    public String imageurl;

    public RootCategory rootCategory;

    public RootCategory getRootCategory() {
        return rootCategory;
    }



    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public static class RootCategory {
        public List<Category> categories;
        @SerializedName("show_category")
        public String show_category;
        @SerializedName("show_category_inhome")
        public int show_category_inhome;
        @SerializedName("first_category_id")
        public String first_category_id;

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public String getShow_category() {
            return show_category;
        }

        public void setShow_category(String show_category) {
            this.show_category = show_category;
        }

        public int getShow_category_inhome() {
            return show_category_inhome;
        }

        public void setShow_category_inhome(int show_category_inhome) {
            this.show_category_inhome = show_category_inhome;
        }

        public String getFirst_category_id() {
            return first_category_id;
        }

        public void setFirst_category_id(String first_category_id) {
            this.first_category_id = first_category_id;

        }
    }

}