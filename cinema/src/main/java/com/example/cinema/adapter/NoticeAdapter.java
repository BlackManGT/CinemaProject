package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class NoticeAdapter extends RecyclerView.Adapter {

    private Context context;

    public NoticeAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<IDMoiveDetalisTwo.ShortFilmListBean> list = new ArrayList<>();
    public void addItem(List<IDMoiveDetalisTwo.ShortFilmListBean> posterList) {
        if(posterList!=null)
        {
            list.addAll(posterList);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_noticemoive, null);
        StilsVH stilsVH = new StilsVH(view);
        return stilsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        IDMoiveDetalisTwo.ShortFilmListBean shortFilmListBean = list.get(i);
        String imageUrl = shortFilmListBean.getImageUrl();
        String videoUrl = shortFilmListBean.getVideoUrl();
        StilsVH stilsVH = (StilsVH) viewHolder;
        stilsVH.notice_jzv.setUp(videoUrl,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        Glide.with(context).load(imageUrl).into(stilsVH.notice_jzv.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    //创建ViewHolder
    class StilsVH extends RecyclerView.ViewHolder {

        public JZVideoPlayerStandard notice_jzv;
        public StilsVH(@NonNull View itemView) {
            super(itemView);
            notice_jzv = itemView.findViewById(R.id.notice_jzv);
        }
    }
}
