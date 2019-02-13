package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.MyIsFollowListTwoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class MyIsFollowTwoAdapter extends RecyclerView.Adapter {
    private Context context;

    public MyIsFollowTwoAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MyIsFollowListTwoBean> list = new ArrayList<>();

    public void addItem(List<MyIsFollowListTwoBean> myIsFollowListBeans) {
        if(myIsFollowListBeans!=null)
        {
            list.addAll(myIsFollowListBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_myisfollowtwoadapter, null);
        MyFollowVh myFollowVh = new MyFollowVh(view);
        return myFollowVh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyIsFollowListTwoBean myIsFollowListBean = list.get(i);
        MyFollowVh myFollowVh = (MyFollowVh) viewHolder;
        myFollowVh.myfollow_sdv1.setImageURI(Uri.parse(myIsFollowListBean.getLogo()));
        myFollowVh.myfollow_textviewone1.setText(myIsFollowListBean.getName());
        myFollowVh.myfollow_textviewtwo1.setText(myIsFollowListBean.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove() {
        list.clear();
        notifyDataSetChanged();
    }


    //创建VH
    class MyFollowVh extends RecyclerView.ViewHolder {

        public SimpleDraweeView myfollow_sdv1;
        public TextView myfollow_textviewone1;
        public TextView myfollow_textviewtwo1;
        public MyFollowVh(@NonNull View itemView) {
            super(itemView);
            myfollow_sdv1 = itemView.findViewById(R.id.myfollow_sdv1);
            myfollow_textviewone1 = itemView.findViewById(R.id.myfollow_textviewone1);
            myfollow_textviewtwo1 = itemView.findViewById(R.id.myfollow_textviewtwo1);
        }
    }
}
