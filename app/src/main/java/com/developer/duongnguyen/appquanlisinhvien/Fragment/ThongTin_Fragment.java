package com.developer.duongnguyen.appquanlisinhvien.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.duongnguyen.appquanlisinhvien.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ThongTin_Fragment extends Fragment {

    CircleImageView img_HinhAnh;
    TextView tv_Hoten, tv_Mssv, tv_GioiTinh, tv_NgaySinh, tv_Email, tv_TenNganh;

    View mView;

    public ThongTin_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.thongtin_fragment, container, false);

        InitView();

        GetDataAndShowInfo();

        return mView;
    }

    private void GetDataAndShowInfo() {
        Intent mIntent = getActivity().getIntent();

        String Id       = mIntent.getStringExtra("ID");
        String Hinhanh  = mIntent.getStringExtra("HINHANH");
        String Hoten    = mIntent.getStringExtra("HOTEN");
        String Mssv     = mIntent.getStringExtra("MSSV");
        String GioiTinh = mIntent.getStringExtra("GIOITINH");
        String NgaySinh = mIntent.getStringExtra("NGAYSINH");
        String Email    = mIntent.getStringExtra("EMAIL");
        String Tennganh = mIntent.getStringExtra("TENNGANH");

        Picasso.get().load(Hinhanh).into(img_HinhAnh);
        tv_Hoten.setText(Hoten);
        tv_Mssv.setText(Mssv);
        tv_GioiTinh.setText(GioiTinh);
        tv_NgaySinh.setText(NgaySinh);
        tv_Email.setText(Email);
        tv_TenNganh.setText(Tennganh);
    }

    private void InitView() {
        img_HinhAnh = mView.findViewById(R.id.mCircleImageView);
        tv_Hoten = mView.findViewById(R.id.mTextViewHoten);
        tv_Mssv = mView.findViewById(R.id.mTextViewMSSV);
        tv_GioiTinh = mView.findViewById(R.id.mTextViewGioiTinh);
        tv_NgaySinh = mView.findViewById(R.id.mTextViewNgaySinh);
        tv_Email = mView.findViewById(R.id.mTextViewEmail);
        tv_TenNganh = mView.findViewById(R.id.mTextViewNganh);
    }

}
