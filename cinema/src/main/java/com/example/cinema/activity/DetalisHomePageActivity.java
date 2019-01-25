package com.example.cinema.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.IDMoiveDetalisonePresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetalisHomePageActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private TextView detalishomepagename;
    private SimpleDraweeView detalishomepagesdvtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis_home_page);

        Button detalishomepagebutton = findViewById(R.id.detalishomepagebutton);
        detalishomepagename = findViewById(R.id.detalishomepagename);
        detalishomepagesdvtwo = findViewById(R.id.detalishomepagesdvtwo);
        detalishomepagebutton.setOnClickListener(this);

        //得到电影id
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        IDMoiveDetalisonePresenter idMoiveDetalisonePresenter = new IDMoiveDetalisonePresenter(new IDMoiveDetalisOneCall());
        idMoiveDetalisonePresenter.reqeust(0,"",id);


        //点击电影详情/预告/剧照/影评
        RadioGroup detalishomegroup = findViewById(R.id.detalishomegroup);
        detalishomegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.detalisbuttonone:
                        break;
                    case R.id.detalisbuttontwo:
                        break;
                    case R.id.detalisbuttonthree:
                        break;
                    case R.id.detalisbuttonfour:
                        break;
                }
            }
        });

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.detalishomepagebutton:
                finish();
                break;
        }
    }

    class IDMoiveDetalisOneCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                 IDMoiveDetalisOne idMoiveDetalisOne = (IDMoiveDetalisOne) result.getResult();
                 detalishomepagename.setText(idMoiveDetalisOne.getName());
                 detalishomepagesdvtwo.setImageURI(Uri.parse(idMoiveDetalisOne.getImageUrl()));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


}
