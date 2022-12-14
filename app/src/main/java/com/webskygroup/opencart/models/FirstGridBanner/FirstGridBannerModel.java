package com.webskygroup.opencart.models.FirstGridBanner;

import java.util.ArrayList;

public class FirstGridBannerModel {
    public String title;
    public String link;
    public String category;
    public String imageurl;
    public String size;
    public int sort;
    public GridBannerRoot gridBannerRoot;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public GridBannerRoot getGridBannerRoot() {
        return gridBannerRoot;
    }

    public void setGridBannerRoot(GridBannerRoot gridBannerRoot) {
        this.gridBannerRoot = gridBannerRoot;
    }

    public static class GridBannerRoot{
        public String click_target;
        public ArrayList<FirstGridBannerModel> banners;
        public String banner_design;
        public String result;

        public String getClick_target() {
            return click_target;
        }

        public void setClick_target(String click_target) {
            this.click_target = click_target;
        }

        public ArrayList<FirstGridBannerModel> getBanners() {
            return banners;
        }

        public void setBanners(ArrayList<FirstGridBannerModel> banners) {
            this.banners = banners;
        }

        public String getBanner_design() {
            return banner_design;
        }

        public void setBanner_design(String banner_design) {
            this.banner_design = banner_design;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }


    }

}

