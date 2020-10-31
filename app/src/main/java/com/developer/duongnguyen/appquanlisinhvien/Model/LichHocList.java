package com.developer.duongnguyen.appquanlisinhvien.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LichHocList {

    @SerializedName("lichhoc")
    @Expose
    private ArrayList<LichHoc> lichhocList;

    public ArrayList<LichHoc> getLichhocList() {
        return lichhocList;
    }

    public void setLichhocList(ArrayList<LichHoc> lichhocList) {
        this.lichhocList = lichhocList;
    }
}
