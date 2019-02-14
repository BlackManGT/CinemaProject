package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.CPBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CinemaPinglunAdapter extends RecyclerView.Adapter {

    private Context context;

    public CinemaPinglunAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<CPBean> list = new ArrayList<>();
    public void addItem(List<CPBean> cpBeans) {
        if(cpBeans!=null)
        {
            list.addAll(cpBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_cinemapinglun, null);
        GzVh gzVh = new GzVh(view);
        return gzVh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CPBean cpBean = list.get(i);


        GzVh gzVh = (GzVh) viewHolder;
        gzVh.cinema_heard.setImageURI(Uri.parse(cpBean.getCommentHeadPic()));

        gzVh.cinema_name.setText(cpBean.getCommentUserName());
        gzVh.cinema_pinglun.setText(cpBean.getCommentContent());
        gzVh.text_number.setText(cpBean.getHotComment()+"");

        gzVh.cinema_reply.setText(cpBean.getHotComment()+"");
        gzVh.text_number.setText(cpBean.getGreatNum()+"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(cpBean.getCommentTime());
        gzVh.cinema_time.setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    //创建Vh
    class GzVh extends RecyclerView.ViewHolder {
        public SimpleDraweeView cinema_heard;
        public TextView cinema_name;
        public TextView cinema_pinglun;
        public TextView cinema_time;
        public TextView cinema_reply;
        public ImageView checkBox;
        public TextView text_number;
        public GzVh(@NonNull View itemView) {
            super(itemView);
            cinema_heard = itemView.findViewById(R.id.cinema_heard);
            cinema_name = itemView.findViewById(R.id.cinema_name);
            cinema_pinglun = itemView.findViewById(R.id.cinema_pinglun);
            cinema_time = itemView.findViewById(R.id.cinema_time);
            cinema_reply = itemView.findViewById(R.id.cinema_reply);
            checkBox = itemView.findViewById(R.id.dianzancheckbox);
            text_number = itemView.findViewById(R.id.cinema_text_number);
        }
    }
}
