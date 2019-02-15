package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.FilmReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmReviewAdapter extends RecyclerView.Adapter {

    private Context context;
    private FilmReviewBean filmReviewBean;
    private int isGreat;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        filmReviewBean = list.get(i);


        final FilmReviewVH filmReviewVH = (FilmReviewVH) viewHolder;
        filmReviewVH.filmreview_heard.setImageURI(Uri.parse(filmReviewBean.getCommentHeadPic()));

        filmReviewVH.filmreview_name.setText(filmReviewBean.getCommentUserName());
        filmReviewVH.filmreview_pinglun.setText(filmReviewBean.getCommentContent());
        filmReviewVH.filmreview_reply.setText(filmReviewBean.getHotComment()+"");

        filmReviewVH.filmreview_reply.setText(filmReviewBean.getHotComment()+"");
        filmReviewVH.text_number.setText(filmReviewBean.getGreatNum()+"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(filmReviewBean.getCommentTime());
        filmReviewVH.filmreview_time.setText(simpleDateFormat.format(date));
        isGreat = filmReviewBean.getIsGreat();
        if (isGreat ==1){
            filmReviewVH.checkBox.setImageResource(R.drawable.com_icon_praise_selected);
        }else {
            filmReviewVH.checkBox.setImageResource(R.drawable.com_icon_praise_default);
        }
        filmReviewVH.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(i).setIsGreat(1);
                if (isGreat !=1){
                    list.get(i).setGreatNum(list.get(i).getGreatNum()+1);
                }
                filmReviewVH.checkBox.setImageResource(R.drawable.com_icon_praise_selected);
                greatOnClick.setonClick(list.get(i).getCommentId());
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.size();
        notifyDataSetChanged();
    }



    //创建ViewHolder
    class FilmReviewVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView filmreview_heard;
        public TextView filmreview_name;
        public TextView filmreview_pinglun;
        public TextView filmreview_time;
        public TextView filmreview_reply;
        private final ImageView checkBox;
        private final TextView text_number;

        public FilmReviewVH(@NonNull View itemView) {
            super(itemView);
            filmreview_heard = itemView.findViewById(R.id.filmreview_heard);
            filmreview_name = itemView.findViewById(R.id.filmreview_name);
            filmreview_pinglun = itemView.findViewById(R.id.filmreview_pinglun);
            filmreview_time = itemView.findViewById(R.id.filmreview_time);
            filmreview_reply = itemView.findViewById(R.id.filmreview_reply);
            checkBox = itemView.findViewById(R.id.dianzancheckbox);
            text_number = itemView.findViewById(R.id.film_text_number);
        }
    }
    private GreatOnClick greatOnClick;
    public interface GreatOnClick{
        void setonClick(int commentid);
    }
    public void setGreatOnClick(GreatOnClick greatOnClick){
        this.greatOnClick = greatOnClick;
    }
    public void clearList(){
        list.clear();
    }
}
