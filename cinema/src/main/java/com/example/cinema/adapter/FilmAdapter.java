package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.DetalisHomePageActivity;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter {

    private Context context;

    public FilmAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MoiveBean> list = new ArrayList<>();
    public void addItem(List<MoiveBean> popularMovieBeans) {
        if(popularMovieBeans!=null)
        {
            list.clear();
            list.addAll(popularMovieBeans);
        }
    }

    //接口
    private GuanzhuOk guanzhuOk;

    public void setFilmAdapterOk(GuanzhuOk guanzhuOk) {
        this.guanzhuOk = guanzhuOk;
    }
    private GuanzhuNo guanzhuNo;

    public void setFilmAdapterNo(GuanzhuNo guanzhuNo) {
        this.guanzhuNo = guanzhuNo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_twopopularmovie, null);
        TwoPopularVH twoPopularVH = new TwoPopularVH(view);
        return twoPopularVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MoiveBean moiveBean = list.get(i);
        final TwoPopularVH twoPopularVH = (TwoPopularVH) viewHolder;
        twoPopularVH.twopopularsdvtwo.setImageURI(Uri.parse(moiveBean.getImageUrl()));
        twoPopularVH.twopopulartextviewone.setText(moiveBean.getName());
        twoPopularVH.twopopulartextviewtwo.setText(moiveBean.getSummary());
        twoPopularVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetalisHomePageActivity.class);
                intent.putExtra("id",moiveBean.getId()+"");
                context.startActivity(intent);
            }
        });


        final int followCinema = moiveBean.getFollowMovie();
        if (followCinema==1){
            twoPopularVH.ImageViewthree.setBackgroundResource(R.drawable.com_icon_collection_selectet);
        }else {
            twoPopularVH.ImageViewthree.setBackgroundResource(R.drawable.com_icon_collection_default);
        }
        //点击关注
        twoPopularVH.ImageViewthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followCinema==1){//取消关注
                    list.get(i).setFollowMovie(2);
                    twoPopularVH.ImageViewthree.setBackgroundResource(R.drawable.com_icon_collection_default);
                    guanzhuNo.GuanzhuOnclickNo(moiveBean.getId());
                }else {
                    list.get(i).setFollowMovie(1);
                    twoPopularVH.ImageViewthree.setBackgroundResource(R.drawable.com_icon_collection_selectet);
                    guanzhuOk.GuanzhuOnclickOk(moiveBean.getId());
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }


    //创建ViewHolder
    class TwoPopularVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView twopopularsdvtwo;
        public TextView twopopulartextviewone;
        public TextView twopopulartextviewtwo;
        public ImageView ImageViewthree;
        public TwoPopularVH(@NonNull View itemView) {
            super(itemView);
            twopopularsdvtwo = itemView.findViewById(R.id.twopopularsdvtwo);
            twopopulartextviewone = itemView.findViewById(R.id.twopopulartextviewone);
            twopopulartextviewtwo = itemView.findViewById(R.id.twopopulartextviewtwo);
            ImageViewthree = itemView.findViewById(R.id.ImageViewthree);
        }
    }


    //关注内部接口
    public interface GuanzhuOk
    {
        void GuanzhuOnclickOk(int sid);
    }
    //取消关注接口
    public interface GuanzhuNo
    {
        void GuanzhuOnclickNo(int sid);
    }
}
