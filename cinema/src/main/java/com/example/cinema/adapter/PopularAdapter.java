package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.DetalisHomePageActivity;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter {

    private Context context;

    public PopularAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MoiveBean> list = new ArrayList<>();
    public void addItem(List<MoiveBean> popularMovieBeans) {
        if(popularMovieBeans!=null)
        {
            list.addAll(popularMovieBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_popularmovie, null);
        PopularVH popularVH = new PopularVH(view);
        return popularVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MoiveBean moiveBean = list.get(i);
        PopularVH popularVH = (PopularVH) viewHolder;

        popularVH.popularsdv.setImageURI(moiveBean.getImageUrl());
        popularVH.populartextview.setText(moiveBean.getName());
        popularVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetalisHomePageActivity.class);
                intent.putExtra("id",moiveBean.getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建ViewHolder
    class PopularVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView popularsdv;
        public TextView populartextview;
        public PopularVH(@NonNull View itemView) {
            super(itemView);
            popularsdv = itemView.findViewById(R.id.popularsdv);
            populartextview = itemView.findViewById(R.id.populartextview);
        }
    }
}
