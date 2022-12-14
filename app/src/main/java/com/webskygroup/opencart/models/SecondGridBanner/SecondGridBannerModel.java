package com.webskygroup.opencart.models.SecondGridBanner;

import java.util.ArrayList;

public class SecondGridBannerModel {

    public String id;
    public boolean wishlist;
    public String imageurl;
    public String name;
    public String description;
    public String price;
    public int special_price;
    public boolean percentage;
    public String tax;
    public SecondGridBannerRoot secondGridBannerRoot;


    public SecondGridBannerRoot getSecondGridBannerRoot() {
        return secondGridBannerRoot;
    }

    public void setSecondGridBannerRoot(SecondGridBannerRoot secondGridBannerRoot) {
        this.secondGridBannerRoot = secondGridBannerRoot;
    }



    public int getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(int special_price) {
        this.special_price = special_price;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isWishlist() {
        return wishlist;
    }

    public void setWishlist(boolean wishlist) {
        this.wishlist = wishlist;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }


    public static class SecondGridBannerRoot{
        public String result;
        public ArrayList<SecondGridBannerModel> secondGridBannerModels;
        public boolean count;
        public String icon;
        public String heading;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public ArrayList<SecondGridBannerModel> getSecondGridBannerModels() {
            return secondGridBannerModels;
        }

        public void setSecondGridBannerModels(ArrayList<SecondGridBannerModel> secondGridBannerModels) {
            this.secondGridBannerModels = secondGridBannerModels;
        }

        public boolean isCount() {
            return count;
        }

        public void setCount(boolean count) {
            this.count = count;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }
    }

}


