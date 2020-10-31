package com.developer.duongnguyen.appquanlisinhvien.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonHoc {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("mamon")
    @Expose
    private String mamon;
    @SerializedName("tenmon")
    @Expose
    private String tenmon;
    @SerializedName("diemtb")
    @Expose
    private String diemtb;
    @SerializedName("trangthai")
    @Expose
    private String trangthai;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(String diemtb) {
        this.diemtb = diemtb;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
