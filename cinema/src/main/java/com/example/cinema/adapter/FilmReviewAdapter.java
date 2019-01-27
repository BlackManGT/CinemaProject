package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.example.cinema.bean.FilmReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmReviewAdapter extends RecyclerView.Adapter {

    private Context context;

    public FilmReviewAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<FilmReviewBean> list = new ArrayList<>();
    public void addItem(List<FilmReviewBean> filmReviewBeans) {
        if(filmReviewBeans!=null)
        {
            list.addAll(filmReviewBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_filmreviewmoive, null);
        FilmReviewVH filmReviewVH = new FilmReviewVH(view);
        return filmReviewVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        FilmReviewBean filmReviewBean = list.get(i);

        FilmReviewVH filmReviewVH = (FilmReviewVH) viewHolder;
        filmReviewVH.filmreview_heard.setImageURI(Uri.parse(filmReviewBean.getCommentHeadPic()));
        filmReviewVH.filmreview_name.setText(filmReviewBean.getCommentUserName());
        filmReviewVH.filmreview_pinglun.setText(filmReviewBean.getCommentContent());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(filmReviewBean.getCommentTime());
        filmReviewVH.filmreview_time.setText(simpleDateFormat.format(date));

        

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建ViewHolder
    class FilmReviewVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView filmreview_heard;
        public TextView filmreview_name;
        public TextView filmreview_pinglun;
        public TextView filmreview_time;
        public Button commentbutton;
        public FilmReviewVH(@NonNull View itemView) {
            super(itemView);
            filmreview_heard = itemView.findViewById(R.id.filmreview_heard);
            filmreview_name = itemView.findViewById(R.id.filmreview_name);
            filmreview_pinglun = itemView.findViewById(R.id.filmreview_pinglun);
            filmreview_time = itemView.findViewById(R.id.filmreview_time);
        }
    }
}
