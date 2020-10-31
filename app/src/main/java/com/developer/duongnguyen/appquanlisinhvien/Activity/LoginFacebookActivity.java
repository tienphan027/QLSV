package com.developer.duongnguyen.appquanlisinhvien.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Model.SinhVien;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.facebook.appevents.AppEventsLogger;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFacebookActivity extends AppCompatActivity {



    Button              btnNext;
    Button              btnLogout;
    String              stName;
    String              stEmail;
    String              stID;
    String              Email;
    String              Password;
    TextView            textName;
    TextView            textEmail;
    LoginButton         loginButton;
    CallbackManager     callbackManager;
    ProfilePictureView  profilePictureView;
    ArrayList<SinhVien> svList;
    AlertDialog         mDialog;


    private static final int HOME_ACTIVITIES_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        setView();
        btnLogout.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        textName.setVisibility(View.INVISIBLE);
        textEmail.setVisibility(View.INVISIBLE);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        setLogin_Button();
        setLogout_Button();

    }

    private void setLogout_Button() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                btnLogout.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
                textName.setVisibility(View.INVISIBLE);
                textEmail.setVisibility(View.INVISIBLE);
                textName.setText("");
                textEmail.setText("");
                profilePictureView.setProfileId(null);
                loginButton.setVisibility(View.VISIBLE);

            }
        });
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
                    Toast.makeText(LoginFacebookActivity.this, "Kiểm tra kết nối", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    return;
                }
            });
        } else {
            Toast.makeText(this, "Đăng Nhập Thất Bại! Xin Thử Lại.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void LoginSuccessAndPassData() {
        Intent goto_Home = new Intent(LoginFacebookActivity.this , HomeActivity.class);

        goto_Home.putExtra("ID"       , svList.get(0).getId());
        goto_Home.putExtra("HINHANH"  , svList.get(0).getHinhanh());
        goto_Home.putExtra("HOTEN"    , svList.get(0).getHoten());
        goto_Home.putExtra("MSSV"     , svList.get(0).getMssv());
        goto_Home.putExtra("GIOITINH" , svList.get(0).getGioitinh());
        goto_Home.putExtra("NGAYSINH" , svList.get(0).getNgaysinh());
        goto_Home.putExtra("EMAIL"    , svList.get(0).getEmail());
        goto_Home.putExtra("TENNGANH" , svList.get(0).getTennganh());

        startActivity(goto_Home);


        mDialog.dismiss();

        Toast.makeText(LoginFacebookActivity.this, "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
    }

    private void CreateAndShowDialog() {
        mDialog = new SpotsDialog.Builder().setContext(LoginFacebookActivity.this).build();
        mDialog.show();
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                loginButton.setVisibility(View.INVISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                textName.setVisibility(View.VISIBLE);
                textEmail.setVisibility(View.VISIBLE);
                result();

            }

            @Override
            public void onCancel() {
                Log.d("LOGIN_CANCEL", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LOGIN_ERROR", "Error");
            }
        });

    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON",response.getJSONObject().toString() );
                        try {
                            stID    = object.getString("id");
                            stName  = object.getString("name");
                            stEmail = object.getString("email");
                            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                            textName.setText(stName);
                            textEmail.setText(stEmail);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
        Log.d("LOGIN_SUCCESS", "Success");
        loginButton.setVisibility(View.INVISIBLE); //<- IMPORTANT
//        Intent intent = new Intent(LoginFacebookActivity.this, HomeActivity.class);
//        startActivity(intent);
//        finish();//<- IMPORTANT
    }

    public void setView(){
        profilePictureView = (ProfilePictureView) findViewById(R.id.imageAvatar);
        loginButton        = (LoginButton) findViewById(R.id.login_button);
        textName           = (TextView) findViewById(R.id.textName);
        textEmail          = (TextView) findViewById(R.id.textEmail);
        btnNext            = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginFacebookActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Email = stEmail;
                Password = stID;
                LoginFunction();
            }
        });
        btnLogout          = (Button) findViewById(R.id.btnLogout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HOME_ACTIVITIES_REQUEST_CODE) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginFacebookActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
