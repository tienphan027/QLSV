package com.developer.duongnguyen.appquanlisinhvien.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SinhVien {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("hinhanh")
    @Expose
    private String hinhanh;
    @SerializedName("hoten")
    @Expose
    private String hoten;
    @SerializedName("fk_id_nganh")
    @Expose
    private String fkIdNganh;
    @SerializedName("tennganh")
    @Expose
    private String tennganh;
    @SerializedName("mssv")
    @Expose
    private String mssv;
    @SerializedName("gioitinh")
    @Expose
    private String gioitinh;
    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

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

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getFkIdNganh() {
        return fkIdNganh;
    }

    public void setFkIdNganh(String fkIdNganh) {
        this.fkIdNganh = fkIdNganh;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTennganh() {
        return tennganh;
    }
}
