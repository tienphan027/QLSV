package com.developer.duongnguyen.appquanlisinhvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.duongnguyen.appquanlisinhvien.Activity.ChiTietMonActivity;
import com.developer.duongnguyen.appquanlisinhvien.Retrofit.ItemClickListener;
import com.developer.duongnguyen.appquanlisinhvien.Model.MonHoc;
import com.developer.duongnguyen.appquanlisinhvien.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewMonHocAdapter extends RecyclerView.Adapter<RecyclerViewMonHocAdapter.MonHocViewHolder> {

    private Context context;
    ArrayList<MonHoc> monhocList;
    ItemClickListener itemClickListener;

    public RecyclerViewMonHocAdapter(Context context, ArrayList<MonHoc> monhocList) {
        this.context = context;
        this.monhocList = monhocList;
    }

    public class MonHocViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        TextView tv_MaMon , tv_TenMon , tv_Diemtb , tv_Trangthai;
        CircleImageView mCircleImageView;

        ItemClickListener itemClickListener;

        public MonHocViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_MaMon         = itemView.findViewById(R.id.mTextView_MaMon);
            tv_TenMon        = itemView.findViewById(R.id.mTextView_TenMon);
            tv_Diemtb        = itemView.findViewById(R.id.mTextView_Diemtb);
            tv_Trangthai     = itemView.findViewById(R.id.mTextView_TrangThai);
            mCircleImageView = itemView.findViewById(R.id.mCircleImageView);

//            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Chi tiết môn học", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ChiTietMonActivity.class);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

    @NonNull
    @Override
    public RecyclerViewMonHocAdapter.MonHocViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_monhoc, viewGroup , false);

        MonHocViewHolder monHocViewHolder = new MonHocViewHolder(mView);

        return monHocViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMonHocAdapter.MonHocViewHolder monHocViewHolder, int i) {

        Picasso.get().load(monhocList.get(i).getHinhanh()).into(monHocViewHolder.mCircleImageView);
        monHocViewHolder.tv_MaMon.setText("Mã Môn: "+monhocList.get(i).getMamon());
        monHocViewHolder.tv_TenMon.setText("Tên Môn: "+monhocList.get(i).getTenmon());
        monHocViewHolder.tv_Diemtb.setText("Điểm TB: "+monhocList.get(i).getDiemtb());
        monHocViewHolder.tv_Trangthai.setText("Trạng Thái: "+monhocList.get(i).getTrangthai());

        // SET EVENT CLICK FOR ITEM OF RECYCLERVIEW
//        monHocViewHolder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return monhocList.size();
    }
}
