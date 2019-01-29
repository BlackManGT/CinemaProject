package com.example.cinema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

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
        String s = list.get(i);

        Glide.with(context).load(s).into(stilsVH.stills_imageview);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    //创建ViewHolder
    class StilsVH extends RecyclerView.ViewHolder {

        public ImageView stills_imageview;
        public StilsVH(@NonNull View itemView) {
            super(itemView);
            stills_imageview = itemView.findViewById(R.id.stills_imageview);
        }
    }
}
