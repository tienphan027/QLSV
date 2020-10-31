package com.developer.duongnguyen.appquanlisinhvien.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Model.SinhVien;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

public class MainActivity extends AppCompatActivity {

    EditText edt_Email;
    EditText edt_Password;
    Button   btn_Login;
    Button   btn_QuenMK;
    Button   btnFacebook;
    Button   btnGoogle;
    String   Email;
    String   Password;

    ArrayList<SinhVien> svList;
    AlertDialog mDialog;
    GoogleApiClient mGoogleApiClient;


    int doubleBackToExitPressed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        OptionalPendingResult<GoogleSignInResult> isGoogleIn = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        boolean isFacebookIn = accessToken != null && !accessToken.isExpired();
        if(isFacebookIn){
            Toast.makeText(MainActivity.this, "Chưa Logout Facebook kìa ^.^", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginFacebookActivity.class);
            startActivity(intent);
        }
        if (isGoogleIn.isDone()) {
            Toast.makeText(MainActivity.this, "Chưa Logout Google kìa ^.^", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, LoginGoogleActivity.class);
            startActivity(intent);
        }

        InitView();

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edt_Email.getText().toString();
                Password = edt_Password.getText().toString();

                LoginFunction();

            }
        });

        btn_QuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_QuenMK = new Intent(MainActivity.this , QuenMatKhauActivity.class);
                startActivity(goto_QuenMK);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginFacebookActivity.class);
                startActivity(intent);
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginGoogleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InitView() {
        edt_Email    = findViewById(R.id.EditText_Email);
        edt_Password = findViewById(R.id.EditText_Password);
        btn_Login    = findViewById(R.id.Button_Login);
        btn_QuenMK   = findViewById(R.id.Button_QuenMK);
        btnFacebook  = findViewById(R.id.btnFacebook);
        btnGoogle    = findViewById(R.id.btnGoogle);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }

    private void LoginFunction() {
        if (Email.length() > 0 && Password.length() > 0)
        {
            CreateAndShowDialog();

            // Init API
            IApi mApi = ApiUtils.getAPI();
            Call<List<SinhVien>> callback = mApi.SinhVienLogin(Email , Password);
            // Handle With API
            callback.enqueue(new Callback<List<SinhVien>>() {
                @Override
                public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                    svList = (ArrayList<SinhVien>) response.body();

                    if (svList.size() > 0)
                    {
                        LoginSuccessAndPassData();
                    }
                }

                @Override
                public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                    Log.d("ERROR" , t.getMessage());
                    Toast.makeText(MainActivity.this, "Bạn Nhập Sai Email Hoặc Password", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    return;
                }
            });
        }
        else if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Bạn Chưa Nhập Email Hoặc Password.", Toast.LENGTH_SHORT).show();
            return;

        }
        else
        {
            Toast.makeText(this, "Đăng Nhập Thất Bại! Xin Thử Lại.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void LoginSuccessAndPassData() {
        Intent goto_Home = new Intent(MainActivity.this , HomeActivity.class);

        goto_Home.putExtra("ID"       , svList.get(0).getId());
        goto_Home.putExtra("HINHANH"  , svList.get(0).getHinhanh());
        goto_Home.putExtra("HOTEN"    , svList.get(0).getHoten());
        goto_Home.putExtra("MSSV"     , svList.get(0).getMssv());
        goto_Home.putExtra("GIOITINH" , svList.get(0).getGioitinh());
        goto_Home.putExtra("NGAYSINH" , svList.get(0).getNgaysinh());
        goto_Home.putExtra("EMAIL"    , svList.get(0).getEmail());
        goto_Home.putExtra("TENNGANH" , svList.get(0).getTennganh());

        startActivity(goto_Home);

        // Make Email and Password null
        edt_Email.setText("");
        edt_Password.setText("");

        mDialog.dismiss();

        Toast.makeText(MainActivity.this, "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
    }

    private void CreateAndShowDialog() {
        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();
        mDialog.show();
    }


}
