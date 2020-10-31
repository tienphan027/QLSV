package com.developer.duongnguyen.appquanlisinhvien.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.duongnguyen.appquanlisinhvien.R;

public class TraNoMon_Fragment extends Fragment {

    View mView;


    public TraNoMon_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tranomon_fragment, container, false);

        return mView;
    }

}
