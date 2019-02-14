package com.example.cinema.adapter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.CDBean;

import java.util.ArrayList;

public class CinemaDetalisAdapter extends RecyclerView.Adapter {
    private Context context;

    public CinemaDetalisAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<CDBean> list = new ArrayList<>();

    public void addItem(CDBean cdBean) {
        if(cdBean!=null)
        {
            list.add(cdBean);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_cinemadetalis, null);
        DetalisVh detalisVh = new DetalisVh(view);
        return detalisVh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CDBean cdBean = list.get(0);
        DetalisVh detalisVh = (DetalisVh) viewHolder;
        detalisVh.detalistextone.setText(cdBean.getAddress()+"");
        detalisVh.detalistexttwo.setText(cdBean.getPhone()+"");
        detalisVh.detalistextthree.setText(cdBean.getBusinessHoursContent()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建VH
    class DetalisVh extends RecyclerView.ViewHolder {

        public TextView detalistextone;
        public TextView detalistexttwo;
        public TextView detalistextthree;
        public TextView ditiears;
        public DetalisVh(@NonNull View itemView) {
            super(itemView);
            detalistextone = itemView.findViewById(R.id.detalistextone);
            detalistexttwo = itemView.findViewById(R.id.detalistexttwo);
            detalistextthree = itemView.findViewById(R.id.detalistextthree);
            ditiears = itemView.findViewById(R.id.ditiears);
        }
    }
}
