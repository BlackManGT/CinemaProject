package com.example.cinema.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.example.cinema.adapter.PurchaseAdapter;
import com.example.cinema.bean.PurchaseBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.PurchasePresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class PurchaseActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private PurchaseAdapter purchaseAdapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //传过来的电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        PurchasePresenter purchasePresenter = new PurchasePresenter(new PurchaseCall());

        Button purchase_return = findViewById(R.id.purchase_return);
        purchase_return.setOnClickListener(this);

        TextView purchase_name = findViewById(R.id.purchase_name);
        purchase_name.setText(name);

        RecyclerView purchase_recycleview = findViewById(R.id.purchase_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        purchase_recycleview.setLayoutManager(linearLayoutManager);
        purchaseAdapter = new PurchaseAdapter(this);
        purchase_recycleview.setAdapter(purchaseAdapter);

        purchasePresenter.reqeust(id);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.purchase_return:
                finish();
                break;
        }
    }

    class PurchaseCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<PurchaseBean> purchaseBeans = (List<PurchaseBean>) result.getResult();
                purchaseAdapter.addItem(purchaseBeans);
                purchaseAdapter.addId(id);
                purchaseAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
