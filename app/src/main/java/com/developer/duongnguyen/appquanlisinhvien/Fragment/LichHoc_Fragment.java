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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Adapter.RecyclerViewLichHocAdapter;
import com.developer.duongnguyen.appquanlisinhvien.Model.LichHocList;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ApiUtils;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.IApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichHoc_Fragment extends Fragment {

    private Button                     btnRefresh;
    private Context                    context;
    private View                       mView;
    private Toolbar                    mToolbar;
    private LichHocList                lhList;
    private RecyclerView               mRecyclerView;
    private ProgressDialog             progressbar;
    private RecyclerViewLichHocAdapter adapter;


    public LichHoc_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.lichhoc_fragment, container, false);

        progressbar = new ProgressDialog(getActivity());
        btnRefresh = mView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar();
                GetLichHoc();
            }
        });

        mToolbar = mView.findViewById(R.id.mToolBarSearchLH);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar();

        GetLichHoc();

        return mView;
    }

    private void GetLichHoc() {

        // Get id SinhVien
        Intent mIntent = getActivity().getIntent();
        String Id = mIntent.getStringExtra("ID");

        // Init API
        IApi mApi = ApiUtils.getAPI();
        Call<LichHocList> callback = mApi.GetLichHoc(Id);

        mRecyclerView = mView.findViewById(R.id.mRecyclerView);

        // Handle With API
        callback.enqueue(new Callback<LichHocList>() {
            @Override
            public void onResponse(Call<LichHocList> call, Response<LichHocList> response) {
                lhList = response.body();

                adapter = new RecyclerViewLichHocAdapter(getContext(), lhList.getLichhocList());

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                mRecyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LichHocList> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
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
