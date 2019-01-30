package com.example.cinema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.SysMsgListBean;

import java.util.ArrayList;
import java.util.List;

public class SysMsgAdapter extends RecyclerView.Adapter {

    private Context context;

    public SysMsgAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<SysMsgListBean> list = new ArrayList<>();

    public void addItem(List<SysMsgListBean> sysMsgListBeans) {
        if(sysMsgListBeans!=null)
        {
            list.addAll(sysMsgListBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_sysmsg, null);
        SysMsgVH sysMsgVH = new SysMsgVH(view);
        return sysMsgVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SysMsgVH sysMsgVH = (SysMsgVH) viewHolder;
        SysMsgListBean sysMsgListBean = list.get(i);
        sysMsgVH.sysmsg_name.setText(sysMsgListBean.getTitle());
        sysMsgVH.sysmsg_msg.setText(sysMsgListBean.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建VH
    class SysMsgVH extends RecyclerView.ViewHolder {

        public TextView sysmsg_name;
        public TextView sysmsg_msg;
        public SysMsgVH(@NonNull View itemView) {
            super(itemView);
            sysmsg_name = itemView.findViewById(R.id.sysmsg_name);
            sysmsg_msg = itemView.findViewById(R.id.sysmsg_msg);
        }
    }
}
