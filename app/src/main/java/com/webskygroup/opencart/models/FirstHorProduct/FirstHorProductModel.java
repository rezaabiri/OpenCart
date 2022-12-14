package com.webskygroup.opencart.models.FirstHorProduct;

import java.util.ArrayList;
import java.util.List;

public class FirstHorProductModel {
    public String id;
    public boolean wishlist;
    public String sold;
    public String quantity;
    public String imageurl;
    public String name;
    public String date_start;
    public String date_end;
    public String description;
    public String price;
    public String special_price;
    public double percentage;
    public String tax;
    public FirstHorProductRoot firstHorProductRoot;

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

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
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

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public FirstHorProductRoot getFirstHorProductRoot() {
        return firstHorProductRoot;
    }

    public void setFirstHorProductRoot(FirstHorProductRoot firstHorProductRoot) {
        this.firstHorProductRoot = firstHorProductRoot;
    }


    public static class FirstHorProductRoot{
        public String banner_heading;
        public List<FirstHorProductModel> products;
        public Object storeapp_special_design;
        public Object storeapp_special_background;
        public Object storeapp_special_background_image;
        public Object special_text_color;
        public String storeapp_special_date;
        public String sort;
        public String order;
        public boolean count;
        public Object icon;

        public String getBanner_heading() {
            return banner_heading;
        }

        public void setBanner_heading(String banner_heading) {
            this.banner_heading = banner_heading;
        }

        public List<FirstHorProductModel> getProducts() {
            return products;
        }

        public void setProducts(List<FirstHorProductModel> products) {
            this.products = products;
        }

        public Object getStoreapp_special_design() {
            return storeapp_special_design;
        }

        public void setStoreapp_special_design(Object storeapp_special_design) {
            this.storeapp_special_design = storeapp_special_design;
        }

        public Object getStoreapp_special_background() {
            return storeapp_special_background;
        }

        public void setStoreapp_special_background(Object storeapp_special_background) {
            this.storeapp_special_background = storeapp_special_background;
        }

        public Object getStoreapp_special_background_image() {
            return storeapp_special_background_image;
        }

        public void setStoreapp_special_background_image(Object storeapp_special_background_image) {
            this.storeapp_special_background_image = storeapp_special_background_image;
        }

        public Object getSpecial_text_color() {
            return special_text_color;
        }

        public void setSpecial_text_color(Object special_text_color) {
            this.special_text_color = special_text_color;
        }

        public String getStoreapp_special_date() {
            return storeapp_special_date;
        }

        public void setStoreapp_special_date(String storeapp_special_date) {
            this.storeapp_special_date = storeapp_special_date;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public boolean isCount() {
            return count;
        }

        public void setCount(boolean count) {
            this.count = count;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

    }

}


