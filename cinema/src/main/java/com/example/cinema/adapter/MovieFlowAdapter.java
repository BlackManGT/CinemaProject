package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.example.cinema.activity.DetalisHomePageActivity;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MovieFlowAdapter extends RecyclerView.Adapter {
    private Context context;

    public MovieFlowAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MoiveBean> list = new ArrayList<>();
    public void addItem(List<MoiveBean> moiveBeans) {
        if(moiveBeans!=null)
        {
            list.addAll(moiveBeans);
        }
    }

    private onItemClick clickCb;

    public MovieFlowAdapter(onItemClick clickCb) {
        this.clickCb = clickCb;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_movieadapter,null);
        MovieVH movieVH = new MovieVH(view);
        return movieVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MoiveBean moiveBean = list.get(i);
        MovieVH movieVH = (MovieVH) viewHolder;
        movieVH.img.setImageURI(moiveBean.getImageUrl());
        movieVH.populartextviewone.setText(moiveBean.getName());
        movieVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCb != null) {
                    clickCb.clickItem(i);
                }
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



    class MovieVH extends RecyclerView.ViewHolder {
        public SimpleDraweeView img;
        public TextView populartextviewone;
        public MovieVH(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.img);
            populartextviewone = (TextView) itemView.findViewById(R.id.populartextviewone);
        }
    }

    public interface onItemClick {
        void clickItem(int position);
    }
}
