package com.example.cinema.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class MovieFlowAdapter extends RecyclerView.Adapter {
    private Context context;

    public MovieFlowAdapter(Context context) {
        this.context = context;
    }

    private onItemClick clickCb;

    public MovieFlowAdapter(onItemClick clickCb) {
        this.clickCb = clickCb;
    }

    private int[] mColors = {R.drawable.tu3,R.drawable.tu1,R.drawable.tu2};


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_movieadapter,null);
        MovieVH movieVH = new MovieVH(view);
        return movieVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MovieVH movieVH = (MovieVH) viewHolder;
        Glide.with(context).load(mColors[i % mColors.length]).into(movieVH.img);
//        movieVH.img.setImageURI(Uri.parse(String.valueOf(mColors[i % mColors.length])));
        movieVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    class MovieVH extends RecyclerView.ViewHolder {
        public ImageView img;
        public MovieVH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface onItemClick {
        void clickItem(int position);
    }
}
