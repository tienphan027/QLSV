package com.developer.duongnguyen.appquanlisinhvien.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MonHocList {
    @SerializedName("monhoc")
    @Expose
    private ArrayList<MonHoc> monhocList;

    public ArrayList<MonHoc> getMonhocList() {
        return monhocList;
    }

    public void setMonhocList(ArrayList<MonHoc> monhocList) {
        this.monhocList = monhocList;
    }
}
