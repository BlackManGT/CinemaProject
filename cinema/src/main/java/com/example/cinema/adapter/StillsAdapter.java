package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.DetalisHomePageActivity;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class StillsAdapter extends RecyclerView.Adapter {

    private Context context;

    public StillsAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<String> list = new ArrayList<>();
    public void addItem(List<String> posterList) {
        if(posterList!=null)
        {
            list.addAll(posterList);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_stilsmoive, null);
        StilsVH stilsVH = new StilsVH(view);
        return stilsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        StilsVH stilsVH = (StilsVH) viewHolder;
        stilsVH.stills_sdview.setImageURI(Uri.parse(list.get(i)));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    //创建ViewHolder
    class StilsVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView stills_sdview;
        public StilsVH(@NonNull View itemView) {
            super(itemView);
            stills_sdview = itemView.findViewById(R.id.stills_sdview);
        }
    }
}
