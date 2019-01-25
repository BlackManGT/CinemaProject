package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.CinemaDetalisActivity;
import com.example.cinema.bean.CinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter {

    private Context context;

    public CinemaAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<CinemaBean> list = new ArrayList<>();
    public void addItem(List<CinemaBean> cinemaBeans) {
        if(cinemaBeans!=null)
        {
            list.addAll(cinemaBeans);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_cinemaadapter, null);
        CinemaVH cinemaVH = new CinemaVH(view);
        return cinemaVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final CinemaBean cinemaBean = list.get(i);
        CinemaVH cinemaVH = (CinemaVH) viewHolder;

        cinemaVH.cinemasdvsone.setImageURI(Uri.parse(cinemaBean.getLogo()));
        cinemaVH.cinematextviewone.setText(cinemaBean.getName());
        cinemaVH.cinematextviewtwo.setText(cinemaBean.getAddress());
        cinemaVH.cinematextviewthree.setText(cinemaBean.getCommentTotal()+"km");
        cinemaVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CinemaDetalisActivity.class);
                intent.putExtra("id", cinemaBean.getId() + "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.clear();
    }

    //创建ViewHolder
    class CinemaVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView cinemasdvsone;
        public TextView cinematextviewone;
        public TextView cinematextviewtwo;
        public TextView cinematextviewthree;
        public SimpleDraweeView cinemasdvstwo;
        public CinemaVH(@NonNull View itemView) {
            super(itemView);
            cinemasdvsone = itemView.findViewById(R.id.cinemasdvsone);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
            cinematextviewthree = itemView.findViewById(R.id.cinematextviewthree);
            cinemasdvstwo = itemView.findViewById(R.id.cinemasdvstwo);

        }
    }
}
