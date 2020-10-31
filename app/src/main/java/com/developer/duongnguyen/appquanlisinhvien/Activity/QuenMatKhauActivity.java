package com.developer.duongnguyen.appquanlisinhvien.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuenMatKhauActivity extends AppCompatActivity {

    EditText edt_Email ,edt_NewPassword;
    Button btn_XacNhan;

    String Email , Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        InitView();

        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edt_Email.getText().toString();
                Password = edt_NewPassword.getText().toString();

                // Init API
                IApi mApi = ApiUtils.getAPI();
                Call<String> callback = mApi.ChangePassword(Email , Password);

                //Handle API
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();

                        if (result.contains("Success"))
                        {
                            Intent goto_Login = new Intent(QuenMatKhauActivity.this , MainActivity.class);
                            startActivity(goto_Login);
                            Toast.makeText(QuenMatKhauActivity.this, "Đổi Mật Khẩu Thành Công!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("ERRORCHANGEPASS" , t.getMessage());
                    }
                });

            }
        });
    }

    private void InitView() {
        edt_Email = findViewById(R.id.EditText_Email);
        edt_NewPassword = findViewById(R.id.EditText_Password);
        btn_XacNhan = findViewById(R.id.Button_XacNhan);
    }
}
