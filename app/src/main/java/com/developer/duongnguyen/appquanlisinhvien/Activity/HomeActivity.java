package com.developer.duongnguyen.appquanlisinhvien.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Fragment.LichHoc_Fragment;
import com.developer.duongnguyen.appquanlisinhvien.Fragment.MonHoc_Fragment;
import com.developer.duongnguyen.appquanlisinhvien.Fragment.ThongTin_Fragment;
import com.developer.duongnguyen.appquanlisinhvien.Fragment.TraNoMon_Fragment;
import com.developer.duongnguyen.appquanlisinhvien.R;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView mBottomView;
    FrameLayout          mFrameLayout;
    ThongTin_Fragment    ThongTin_Fragment;
    MonHoc_Fragment      MonHoc_Fragment;
    LichHoc_Fragment     LichHoc_Fragment;
    TraNoMon_Fragment    TraNoMon_Fragment;

    private String nameSubject = "Đăng ký trả nợ môn";
    private String nameUser    = "Hãy điền đầy đủ thông tin bao gồm Họ tên, MSSV, Môn học lại và Học kỳ học lại";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        InitView();

        CreateNewFragment();

        BottomMenuSelected();
        // Show Fragment Thong Tin First
        setFragment(ThongTin_Fragment);

    }

    private void BottomMenuSelected() {
        mBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.nav_thongtin :
                        setFragment(ThongTin_Fragment);
                        return true;

                    case R.id.nav_monhoc :
                        setFragment(MonHoc_Fragment);
                        return true;

                    case R.id.nav_lichhoc :
                        setFragment(LichHoc_Fragment);
                        return true;

                    case R.id.nav_tranomon :
                        ShowDialogTraNoMon();
                        return true;

                    case R.id.nav_dangxuat :
                        ShowDialogDangXuat();
                        return true;

                    default :
                        return false;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout , fragment);
        fragmentTransaction.commit();
    }

    private void CreateNewFragment() {
        ThongTin_Fragment = new ThongTin_Fragment();
        MonHoc_Fragment   = new MonHoc_Fragment();
        LichHoc_Fragment  = new LichHoc_Fragment();
        TraNoMon_Fragment = new TraNoMon_Fragment();
    }

    private void InitView() {
        mBottomView = findViewById(R.id.BottomNavigationView);
        mFrameLayout = findViewById(R.id.FrameLayout);
    }

    private void ShowDialogTraNoMon() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_tnm);
        dialog.setCancelable(false);
        TextView tvCancel  = dialog.findViewById(R.id.btnCancel);
        TextView tvGoEmail = dialog.findViewById(R.id.btnGoEmail);
        TextView tvGoPhone = dialog.findViewById(R.id.btnGoPhone);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        tvGoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Gửi Email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","Tienpps06433@fpt.edu.vn", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, nameSubject);
                intent.putExtra(Intent.EXTRA_TEXT, nameUser);
                startActivity(intent);
            }
        });
        tvGoPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Gọi điện", Toast.LENGTH_SHORT).show();
                String phone = "+02873088800";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void ShowDialogDangXuat() {
        Button btn_Co;
        Button btn_Khong;
        AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(this);
        LayoutInflater inflater	= this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.custom_alert_dialog,
                (ViewGroup)findViewById(R.layout.activity_main));
        btn_Co      = dialogView.findViewById(R.id.Button_Co);
        btn_Khong   = dialogView.findViewById(R.id.Button_Khong);
        dialogBuilder.setView(dialogView);
        final AlertDialog mAlertDialog = dialogBuilder.create();
        mAlertDialog.show();
        btn_Co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_Login = new Intent(HomeActivity.this , MainActivity.class);
                startActivity(goto_Login);
                mAlertDialog.dismiss();
            }
        });

        btn_Khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });


    }
}
