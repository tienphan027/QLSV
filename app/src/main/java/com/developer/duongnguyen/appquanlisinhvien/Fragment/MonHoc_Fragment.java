package com.developer.duongnguyen.appquanlisinhvien.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Adapter.RecyclerViewMonHocAdapter;
import com.developer.duongnguyen.appquanlisinhvien.Activity.ChiTietMonActivity;
import com.developer.duongnguyen.appquanlisinhvien.Model.MonHocList;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonHoc_Fragment extends Fragment {

    private EditText                  edt_Search;
    private Button                    btnRefresh;
    private View                      mView;
    private Context                   context;
    private Toolbar                   mToolbar;
    private MonHocList                mhList;
    private RecyclerView              mRecyclerView;
    private ProgressDialog            progressbar;
    private RecyclerViewMonHocAdapter adapter;

    public MonHoc_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.monhoc_fragment, container, false);

        progressbar = new ProgressDialog(getActivity());
        btnRefresh = mView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar();
                GetMonHoc();
            }
        });

        mRecyclerView = mView.findViewById(R.id.mRecyclerView);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Chi tiết môn học", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ChiTietMonActivity.class);
                context.startActivity(intent);
            }
        });

        InitView();

        GetSupportToolbar();

        SearchMonHoc();

        GetMonHoc();

        return mView;
    }

    private void SearchMonHoc() {
        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Get id SinhVien
                Intent mIntent = getActivity().getIntent();
                String Id = mIntent.getStringExtra("ID");

                // Init API
                IApi mApi = ApiUtils.getAPI();
                Call<MonHocList> callback = mApi.SearchMonHoc(s, Id);

                callback.enqueue(new Callback<MonHocList>() {
                    @Override
                    public void onResponse(Call<MonHocList> call, Response<MonHocList> response) {
                        mhList = response.body();

                        adapter = new RecyclerViewMonHocAdapter(getContext(), mhList.getMonhocList());

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        mRecyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MonHocList> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void GetSupportToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar();
    }

    private void InitView() {
        mToolbar = mView.findViewById(R.id.ToolBar);
        edt_Search = mView.findViewById(R.id.EditText_Search);
    }

    private void GetMonHoc() {
            // Get id SinhVien
            Intent mIntent = getActivity().getIntent();
            String Id = mIntent.getStringExtra("ID");

            // Init API
            IApi mApi = ApiUtils.getAPI();
            Call<MonHocList> callback = mApi.GetMonHoc(Id);

            mRecyclerView = mView.findViewById(R.id.mRecyclerView);

            // Handle API
            callback.enqueue(new Callback<MonHocList>() {
                @Override
                public void onResponse(Call<MonHocList> call, Response<MonHocList> response) {
                    mhList = response.body();

                    adapter = new RecyclerViewMonHocAdapter(getContext(), mhList.getMonhocList());

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    mRecyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MonHocList> call, Throwable t) {
                    Log.d("ERROR MH" , t.getMessage());
                }
            });
    }

    public void progressbar(){
        progressbar.setMessage("Đang tải chờ xí nhé...!");
        progressbar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressbar.setIndeterminate(true);
        progressbar.show();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressbar.dismiss();
            }
        }.start();
    }

}
