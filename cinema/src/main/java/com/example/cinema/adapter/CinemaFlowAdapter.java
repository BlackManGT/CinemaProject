package com.example.cinema.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.example.cinema.bean.CinemaById;
import com.example.cinema.bean.MoiveBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CinemaFlowAdapter extends RecyclerView.Adapter {
    private Context context;

    public CinemaFlowAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<CinemaById> list = new ArrayList<>();
    public void addItem(List<CinemaById> moiveBeans) {
        if(moiveBeans!=null)
        {
            list.addAll(moiveBeans);
        }
    }

    private onItemClick clickCb;

    public CinemaFlowAdapter(onItemClick clickCb) {
        this.clickCb = clickCb;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_movieadapter,null);
        MovieVH movieVH = new MovieVH(view);
        return movieVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        CinemaById cinemaById = list.get(i);
        MovieVH movieVH = (MovieVH) viewHolder;
        Glide.with(context).load(cinemaById.getImageUrl()).into(movieVH.img);
        movieVH.populartextviewone.setBackgroundColor(0x55000000);
        movieVH.populartextviewone.setText(cinemaById.getName());
        movieVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCb != null) {
                    clickCb.clickItem(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MovieVH extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView populartextviewone;
        public MovieVH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            populartextviewone = (TextView) itemView.findViewById(R.id.populartextviewone);
        }
    }

    public interface onItemClick {
        void clickItem(int position);
    }
}
