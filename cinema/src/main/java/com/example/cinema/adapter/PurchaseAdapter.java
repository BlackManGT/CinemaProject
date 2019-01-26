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
import com.example.cinema.activity.CinemaDetalisActivity;
import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.PurchaseBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter {

    private Context context;

    public PurchaseAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<PurchaseBean> list = new ArrayList<>();
    public void addItem(List<PurchaseBean> purchaseBeans) {
        if(purchaseBeans!=null)
        {
            list.addAll(purchaseBeans);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_purchaseadapter, null);
        PurchaseVH purchaseVH = new PurchaseVH(view);
        return purchaseVH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PurchaseBean purchaseBean = list.get(i);
        PurchaseVH purchaseVH = (PurchaseVH) viewHolder;
        purchaseVH.purchase_sdv.setImageURI(Uri.parse(purchaseBean.getLogo()));
        purchaseVH.purchase_textviewone.setText(purchaseBean.getName());
        purchaseVH.purchase_textviewtwo.setText(purchaseBean.getAddress());
        purchaseVH.purchase_textviewthree.setText(purchaseBean.getDistance()+"km");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建ViewHolder
    class PurchaseVH extends RecyclerView.ViewHolder {

        public SimpleDraweeView purchase_sdv;
        public TextView purchase_textviewone;
        public TextView purchase_textviewtwo;
        public TextView purchase_textviewthree;
        public PurchaseVH(@NonNull View itemView) {
            super(itemView);
            purchase_sdv = itemView.findViewById(R.id.purchase_sdv);
            purchase_textviewone = itemView.findViewById(R.id.purchase_textviewone);
            purchase_textviewtwo = itemView.findViewById(R.id.purchase_textviewtwo);
            purchase_textviewthree = itemView.findViewById(R.id.purchase_textviewthree);

        }
    }
}
