package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TwoPopularAdapter extends RecyclerView.Adapter {

    private Context context;

    public TwoPopularAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<MoiveBean> list = new ArrayList<>();
    public void addItem(List<MoiveBean> popularMovieBeans) {
        if(popularMovieBeans!=null)
        {
            list.addAll(popularMovieBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_twopopularmovie, null);
        TwoPopularVH twoPopularVH = new TwoPopularVH(view);
        return twoPopularVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MoiveBean moiveBean = list.get(i);
        TwoPopularVH twoPopularVH = (TwoPopularVH) viewHolder;
        twoPopularVH.twopopularsdvtwo.setImageURI(Uri.parse(moiveBean.getImageUrl()));
        twoPopularVH.twopopulartextviewone.setText(moiveBean.getName());
        twoPopularVH.twopopulartextviewtwo.setText(moiveBean.getSummary());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //创建ViewHolder
    class TwoPopularVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView twopopularsdvtwo;
        public TextView twopopulartextviewone;
        public TextView twopopulartextviewtwo;
        public TwoPopularVH(@NonNull View itemView) {
            super(itemView);
            twopopularsdvtwo = itemView.findViewById(R.id.twopopularsdvtwo);
            twopopulartextviewone = itemView.findViewById(R.id.twopopulartextviewone);
            twopopulartextviewtwo = itemView.findViewById(R.id.twopopulartextviewtwo);
        }
    }
}
