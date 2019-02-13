package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.CinemaDetalisActivity;
import com.example.cinema.bean.CinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter {


    private Follow follow;
    private FollowTwo followTwo;

    public void setCinemaTwoAdapter(FollowTwo followTwo) {
        this.followTwo = followTwo;
    }

    public void  setCinemaAdapter(Follow follow) {
        this.follow = follow;
    }

    private Context context;

    public CinemaAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<CinemaBean> list = new ArrayList<>();
    public void addItem(List<CinemaBean> cinemaBeans) {
        if(cinemaBeans!=null)
        {
            list.clear();
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final CinemaBean cinemaBean = list.get(i);
        final CinemaVH cinemaVH = (CinemaVH) viewHolder;

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

        //关注
        final int followCinema = cinemaBean.getFollowCinema();
        if (followCinema==1){
            cinemaVH.cinemasdvsimageView.setBackgroundResource(R.drawable.com_icon_collection_selectet);
        }else {
            cinemaVH.cinemasdvsimageView.setBackgroundResource(R.drawable.xin);
        }
        cinemaVH.cinemasdvsimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followCinema==1){
                    list.get(i).setFollowCinema(2);
                    cinemaVH.cinemasdvsimageView.setImageResource(R.drawable.xin);
                    followTwo.FollowOnclickTwo(cinemaBean.getId());


                }else {//取消关注
                    list.get(i).setFollowCinema(1);
                    cinemaVH.cinemasdvsimageView.setImageResource(R.drawable.com_icon_collection_selectet);
                    follow.FollowOnclick(cinemaBean.getId());

                }
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //创建ViewHolder
        class CinemaVH extends RecyclerView.ViewHolder {

            public SimpleDraweeView cinemasdvsone;
            public TextView cinematextviewone;
            public TextView cinematextviewtwo;
            public TextView cinematextviewthree;
            public ImageView cinemasdvsimageView;
        public CinemaVH(@NonNull View itemView) {
            super(itemView);
            cinemasdvsone = itemView.findViewById(R.id.cinemasdvsone);
            cinematextviewone = itemView.findViewById(R.id.cinematextviewone);
            cinematextviewtwo = itemView.findViewById(R.id.cinematextviewtwo);
            cinematextviewthree = itemView.findViewById(R.id.cinematextviewthree);
            cinemasdvsimageView = itemView.findViewById(R.id.cinemasdvsimageView);

        }
    }

    //内部接口
    public interface Follow
    {
        void FollowOnclick(int sid);
    }
    public interface FollowTwo
    {
        void FollowOnclickTwo(int sid);
    }
}
