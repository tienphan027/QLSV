package com.developer.duongnguyen.appquanlisinhvien.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.duongnguyen.appquanlisinhvien.Model.LichHoc;
import com.developer.duongnguyen.appquanlisinhvien.Model.MonHoc;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewLichHocAdapter extends RecyclerView.Adapter<RecyclerViewLichHocAdapter.LichHocViewHolder> {

    Context context;
    ArrayList<LichHoc> lichhocList;

    public RecyclerViewLichHocAdapter(Context context, ArrayList<LichHoc> lichhocList) {
        this.context = context;
        this.lichhocList = lichhocList;
    }

    public static class LichHocViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_ThuNgay , tv_MaMon , tv_TenMon , tv_PhongHoc , tv_Slot;

        public LichHocViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tv_ThuNgay  = itemView.findViewById(R.id.mTextView_ThuNgay);
            tv_MaMon    = itemView.findViewById(R.id.mTextView_MaMon);
            tv_TenMon   = itemView.findViewById(R.id.mTextView_TenMon);
            tv_PhongHoc = itemView.findViewById(R.id.mTextView_PhongHoc);
            tv_Slot     = itemView.findViewById(R.id.mTextView_Slot);

        }
    }

    @NonNull
    @Override
    public RecyclerViewLichHocAdapter.LichHocViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_lichhoc, viewGroup , false);

        LichHocViewHolder lichHocViewHolder = new LichHocViewHolder(mView);

        return lichHocViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewLichHocAdapter.LichHocViewHolder monHocViewHolder, int i) {

        monHocViewHolder.tv_ThuNgay.setText("Thứ Ngày: "+lichhocList.get(i).getThungay());
        monHocViewHolder.tv_MaMon.setText("Mã Môn: "+lichhocList.get(i).getMamon());
        monHocViewHolder.tv_TenMon.setText("Tên Môn: "+lichhocList.get(i).getTenmon());
        monHocViewHolder.tv_PhongHoc.setText("Phòng Học: "+lichhocList.get(i).getTenphonghoc());
        monHocViewHolder.tv_Slot.setText("Slot: "+lichhocList.get(i).getTenslot());

    }

    @Override
    public int getItemCount() {
        return lichhocList.size();
    }
}
