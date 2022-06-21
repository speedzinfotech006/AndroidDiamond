package com.dnk.shairugems.Class;

import androidx.annotation.NonNull;

public class DashboardClass {

    String mainData;
    String subData;
    int img;

    public DashboardClass(String mainData, String subData, int img) {
        this.mainData = mainData;
        this.subData = subData;
        this.img = img;
    }

    public String getMainData() {
        return mainData;
    }

    public void setMainData(String mainData) {
        this.mainData = mainData;
    }

    public String getSubData() {
        return subData;
    }

    public void setSubData(String subData) {
        this.subData = subData;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "DashboardClass{" +
                "mainData='" + mainData + '\'' +
                ", subData='" + subData + '\'' +
                ", img=" + img +
                '}';
    }
}
