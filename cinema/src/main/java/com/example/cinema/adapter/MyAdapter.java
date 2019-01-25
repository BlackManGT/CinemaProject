package com.example.cinema.adapter;
 
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.example.cinema.bean.TicketBean;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Created by 。 on 2018/12/14.
 */
 
public class MyAdapter{// extends RecyclerView.Adapter{
//    private List<TicketBean> list = new ArrayList<>();
//    private Context context;
//    private ClickListener clickListener;
//    private LongClickListener longClickListener;
//
//    public MyTicketAdapter(List<TicketBean>  list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(viewType==0){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wait_money, parent, false);
//            return new MyViewHolder(view);
//        }else if(viewType==1){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false);
//            return new ViewHolder2(view);
//        }
//
//        return ;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        int itemViewType = holder.getItemViewType();
//        switch (itemViewType){
//            case 0:
//                MyViewHolder myViewHolder = (MyViewHolder) holder;
//                myViewHolder.textView1.setText(list.get(position).getTitle());
//                Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(myViewHolder.imageView1);
//                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        clickListener.onItmeClickListener(v,position);
//                    }
//                });
//                myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        longClickListener.onLongItmeClickListener(v,position);
//                        return true;
//                    }
//                });
//                break;
//            case 1:
//                ViewHolder2 holder2 = (ViewHolder2) holder;
//                holder2.textView2.setText(list.get(position).getTitle());
//                Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(holder2.imageView21);
//                Glide.with(context).load(list.get(position).getThumbnail_pic_s02()).into(holder2.imageView22);
//                Glide.with(context).load(list.get(position).getThumbnail_pic_s03()).into(holder2.imageView23);
//                holder2.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        clickListener.onItmeClickListener(v,position);
//                    }
//                });
//                holder2.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        longClickListener.onLongItmeClickListener(v,position);
//                        return true;
//                    }
//                });
//                break;
//        }
//    }
//    public TicketBean getBean(int position){
//        TicketBean user = list.get(position);
//        return user;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        int status = list.get(position).getStatus();
//
//        if(status==1){
//            return 0;
//        }else{
//            return 1;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder{
//
//
//        private final TextView one_title;
//        private final TextView one_dingdan;
//        private final TextView one_yingting;
//        private final TextView one_film;
//        private final TextView one_time;
//        private final TextView one_count;
//        private final TextView one_money;
//        private final Button one_button;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            one_title = itemView.findViewById(R.id.one_title);
//            one_dingdan = itemView.findViewById(R.id.one_dingdan);
//            one_film = itemView.findViewById(R.id.one_film);
//            one_yingting = itemView.findViewById(R.id.one_yingting);
//            one_time = itemView.findViewById(R.id.one_time);
//            one_count = itemView.findViewById(R.id.one_count);
//            one_money = itemView.findViewById(R.id.one_money);
//            one_button = itemView.findViewById(R.id.one_button);
//
//        }
//    }
//    class ViewHolder2 extends RecyclerView.ViewHolder{
//
//        private final TextView textView2;
//        private final ImageView imageView21;
//        private final ImageView imageView22;
//        private final ImageView imageView23;
//
//        public ViewHolder2(View itemView) {
//            super(itemView);
//            textView2 = itemView.findViewById(R.id.two_view);
//            imageView21 = itemView.findViewById(R.id.two_image1);
//            imageView22 = itemView.findViewById(R.id.two_image2);
//            imageView23 = itemView.findViewById(R.id.two_image3);
//        }
//    }
//    public interface ClickListener{
//        void onItmeClickListener(View view,int position);
//    }
//    public void setOnItmeClickListener(ClickListener clickListener){
//        this.clickListener = clickListener;
//    }
//    public interface LongClickListener{
//        void onLongItmeClickListener(View view,int position);
//    }
//    public void setOnLongItmeClickListener(LongClickListener longClickListener){
//        this.longClickListener = longClickListener;
//    }
 
}
