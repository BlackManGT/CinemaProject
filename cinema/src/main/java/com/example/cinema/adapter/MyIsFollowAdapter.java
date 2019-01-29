package com.example.cinema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.MyIsFollowListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyIsFollowAdapter extends RecyclerView.Adapter {
    private Context context;

    public MyIsFollowAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MyIsFollowListBean> list = new ArrayList<>();

    public void addItem(List<MyIsFollowListBean> myIsFollowListBeans) {
        if(myIsFollowListBeans!=null)
        {
            list.clear();
            list.addAll(myIsFollowListBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_myisfollowadapter, null);
        MyFollowVh myFollowVh = new MyFollowVh(view);
        return myFollowVh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyIsFollowListBean myIsFollowListBean = list.get(i);
        MyFollowVh myFollowVh = (MyFollowVh) viewHolder;
        myFollowVh.myfollow_sdv.setImageURI(myIsFollowListBean.getImageUrl());
        myFollowVh.myfollow_textviewone.setText(myIsFollowListBean.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(myIsFollowListBean.getReleaseTime());
        myFollowVh.myfollow_textviewtwo.setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建VH
    class MyFollowVh extends RecyclerView.ViewHolder {

        public SimpleDraweeView myfollow_sdv;
        public TextView myfollow_textviewone;
        public TextView myfollow_textviewtwo;
        public MyFollowVh(@NonNull View itemView) {
            super(itemView);
            myfollow_sdv = itemView.findViewById(R.id.myfollow_sdv);
            myfollow_textviewone = itemView.findViewById(R.id.myfollow_textviewone);
            myfollow_textviewtwo = itemView.findViewById(R.id.myfollow_textviewtwo);
        }
    }
}
