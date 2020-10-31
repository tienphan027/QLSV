package com.developer.duongnguyen.appquanlisinhvien.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LichHoc {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thungay")
    @Expose
    private String thungay;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("mamon")
    @Expose
    private String mamon;
    @SerializedName("tenmon")
    @Expose
    private String tenmon;
    @SerializedName("tenphonghoc")
    @Expose
    private String tenphonghoc;
    @SerializedName("tenslot")
    @Expose
    private String tenslot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThungay() {
        return thungay;
    }

    public void setThungay(String thungay) {
        this.thungay = thungay;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getTenphonghoc() {
        return tenphonghoc;
    }

    public void setTenphonghoc(String tenphonghoc) {
        this.tenphonghoc = tenphonghoc;
    }

    public String getTenslot() {
        return tenslot;
    }

    public void setTenslot(String tenslot) {
        this.tenslot = tenslot;
    }

}
