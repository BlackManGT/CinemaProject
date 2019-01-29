package com.example.cinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
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
        final MoiveBean moiveBean = list.get(i);
        TwoPopularVH twoPopularVH = (TwoPopularVH) viewHolder;
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
        public TwoPopularVH(@NonNull View itemView) {
            super(itemView);
            twopopularsdvtwo = itemView.findViewById(R.id.twopopularsdvtwo);
            twopopulartextviewone = itemView.findViewById(R.id.twopopulartextviewone);
            twopopulartextviewtwo = itemView.findViewById(R.id.twopopulartextviewtwo);
        }
    }
}
