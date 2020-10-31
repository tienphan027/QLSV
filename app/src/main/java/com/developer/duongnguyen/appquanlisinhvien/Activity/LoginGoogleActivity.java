package com.developer.duongnguyen.appquanlisinhvien.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.developer.duongnguyen.appquanlisinhvien.Model.SinhVien;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginGoogleActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnNext;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private String  Email, Password, email, personName, personPhotoUrl;

    ArrayList<SinhVien> svList;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        btnSignIn       = findViewById(R.id.btn_sign_in);
        btnSignOut      = findViewById(R.id.btn_sign_out);
        btnNext         = findViewById(R.id.btnNext);
        llProfileLayout = findViewById(R.id.llProfile);
        imgProfilePic   = findViewById(R.id.imgProfilePic);
        txtName         = findViewById(R.id.txtName);
        txtEmail        = findViewById(R.id.txtEmail);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

            // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
        }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
        new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                updateUI(false);
            }
        });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
        new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                updateUI(false);
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
        // Signed in successfully, show authenticated UI.
        GoogleSignInAccount acct = result.getSignInAccount();

        Log.e(TAG, "display name: " + acct.getDisplayName());

        personName = acct.getDisplayName();
        personPhotoUrl = acct.getPhotoUrl().toString();
        email = acct.getEmail();

        Log.e(TAG, "Name: " + personName + ", email: " + email
        + ", Image: " + personPhotoUrl);

        txtName.setText(personName);
        txtEmail.setText(email);
        Glide.with(getApplicationContext()).load(personPhotoUrl)
            .thumbnail(0.5f)
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgProfilePic);

        updateUI(true);
        } else {
        // Signed out, show unauthenticated UI.
        updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
            signIn();
            break;

            case R.id.btn_sign_out:
            signOut();
            break;

            case R.id.btnNext:
                Toast.makeText(LoginGoogleActivity.this, "Login", Toast.LENGTH_SHORT).show();
                Email    = email;
                Password = "tien";
                LoginFunction();
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
        // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
        // and the GoogleSignInResult will be available instantly.
        Log.d(TAG, "Got cached sign-in");
        GoogleSignInResult result = opr.get();
        handleSignInResult(result);
        } else {
        // If the user has not previously signed in on this device or the sign-in has expired,
        // this asynchronous branch will attempt to sign in the user silently.  Cross-device
        // single sign-on will occur in this branch.
        showProgressDialog();
        opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
            @Override
            public void onResult(GoogleSignInResult googleSignInResult) {
                hideProgressDialog();
                handleSignInResult(googleSignInResult);
            }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
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
                    Toast.makeText(LoginGoogleActivity.this, "Kiểm tra kết nối", Toast.LENGTH_SHORT).show();
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
        Intent goto_Home = new Intent(LoginGoogleActivity.this , HomeActivity.class);

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

        Toast.makeText(LoginGoogleActivity.this, "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
    }

    private void CreateAndShowDialog() {
        mDialog = new SpotsDialog.Builder().setContext(LoginGoogleActivity.this).build();
        mDialog.show();
    }

}