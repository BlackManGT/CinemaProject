package com.example.cinema.activity;

import android.app.Dialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.axlecho.sakura.PlayerView;
import com.bw.movie.R;
import com.example.cinema.adapter.StillsAdapter;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.IDMoiveDetalisonePresenter;
import com.example.cinema.presenter.IDMoiveDetalisoneTwoPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class DetalisHomePageActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private TextView detalishomepagename;
    private SimpleDraweeView detalishomepagesdvtwo;
    private IDMoiveDetalisoneTwoPresenter idMoiveDetalisoneTwoPresenter;
    private int id;
    private SimpleDraweeView detalishomepagesdvone;
    private View detalis;
    private SimpleDraweeView dialog_detalis_sdvone;
    private TextView dialog_detalis_leixing;
    private TextView dialog_detalis_daoyan;
    private TextView dialog_detalis_shichang;
    private TextView dialog_detalis_chandi;
    private SimpleDraweeView dialog_detalis_sdvtwo;
    private TextView plot;
    private Dialog bottomDialog;
    private View notice;
    private PlayerView ontice_playerviewone;
    private PlayerView ontice_playerviewtwo;
    private PlayerView ontice_playerviewthree;
    private View stills;
    private StillsAdapter stillsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis_home_page);

        bottomDialog = new Dialog(DetalisHomePageActivity.this, R.style.BottomDialog);

        idMoiveDetalisoneTwoPresenter = new IDMoiveDetalisoneTwoPresenter(new Dialog_DetalisCall());

        detalishomepagesdvone = findViewById(R.id.detalishomepagesdvone);
        Button detalishomepagebutton = findViewById(R.id.detalishomepagebutton);
        detalishomepagename = findViewById(R.id.detalishomepagename);
        detalishomepagesdvtwo = findViewById(R.id.detalishomepagesdvtwo);
        detalishomepagebutton.setOnClickListener(this);

        //得到电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        IDMoiveDetalisonePresenter idMoiveDetalisonePresenter = new IDMoiveDetalisonePresenter(new IDMoiveDetalisOneCall());
        idMoiveDetalisonePresenter.reqeust(0,"", id);

        //点击电影详情/预告/剧照/影评
        detalis = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_detalis, null);
        dialog_detalis_sdvone = detalis.findViewById(R.id.dialog_detalis_sdvone);
        dialog_detalis_leixing = detalis.findViewById(R.id.dialog_detalis_leixing);
        dialog_detalis_daoyan = detalis.findViewById(R.id.dialog_detalis_daoyan);
        dialog_detalis_shichang = detalis.findViewById(R.id.dialog_detalis_shichang);
        dialog_detalis_chandi = detalis.findViewById(R.id.dialog_detalis_chandi);
        dialog_detalis_sdvtwo = detalis.findViewById(R.id.dialog_detalis_sdvtwo);
        dialog_detalis_sdvtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        plot = detalis.findViewById(R.id.plot);
        
        //预告
        notice = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_notice, null);
        notice.findViewById(R.id.notice_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ontice_playerviewone = notice.findViewById(R.id.ontice_playerviewone);
        ontice_playerviewtwo = notice.findViewById(R.id.ontice_playerviewtwo);
        ontice_playerviewthree = notice.findViewById(R.id.ontice_playerviewthree);

        //剧照
        stills = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_stills, null);
        stills.findViewById(R.id.stills_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        RecyclerView stills_recycleview = stills.findViewById(R.id.stills_recycleview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        stills_recycleview.setLayoutManager(staggeredGridLayoutManager);
        stillsAdapter = new StillsAdapter(this);
        stills_recycleview.setAdapter(stillsAdapter);

        RadioGroup detalishomegroup = findViewById(R.id.detalishomegroup);
        detalishomegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.detalisbuttonone:
                        bottomDialog.setContentView(detalis);
                        ViewGroup.LayoutParams layoutParams = detalis.getLayoutParams();
                        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                        detalis.setLayoutParams(layoutParams);
                        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                        bottomDialog.setCanceledOnTouchOutside(true);
                        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                        bottomDialog.show();
                        idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
                        break;
                    case R.id.detalisbuttontwo:
                        bottomDialog.setContentView(notice);
                        ViewGroup.LayoutParams layoutParamstwo = notice.getLayoutParams();
                        layoutParamstwo.width = getResources().getDisplayMetrics().widthPixels;
                        notice.setLayoutParams(layoutParamstwo);
                        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                        bottomDialog.setCanceledOnTouchOutside(true);
                        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                        bottomDialog.show();
                        idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
                        break;
                    case R.id.detalisbuttonthree:
                        bottomDialog.setContentView(stills);
                        ViewGroup.LayoutParams layoutParamsthree = stills.getLayoutParams();
                        layoutParamsthree.width = getResources().getDisplayMetrics().widthPixels;
                        stills.setLayoutParams(layoutParamsthree);
                        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                        bottomDialog.setCanceledOnTouchOutside(true);
                        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                        bottomDialog.show();
                        idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
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

    //详情//预告
    class Dialog_DetalisCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                IDMoiveDetalisTwo idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();
                //详情
                dialog_detalis_sdvone.setImageURI(Uri.parse(idMoiveDetalisTwo.getImageUrl()));
                dialog_detalis_leixing.setText("类型："+idMoiveDetalisTwo.getMovieTypes());
                dialog_detalis_daoyan.setText("导演："+idMoiveDetalisTwo.getDirector());
                dialog_detalis_shichang.setText("时长："+idMoiveDetalisTwo.getDuration());
                dialog_detalis_chandi.setText("产地："+idMoiveDetalisTwo.getPlaceOrigin());
                plot.setText(idMoiveDetalisTwo.getSummary());


                //                IDMoiveDetalisTwo idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();
//                List<IDMoiveDetalisTwo.ShortFilmListBean> shortFilmList = idMoiveDetalisTwo.getShortFilmList();
//                //预告片
//                List<String> posterList = idMoiveDetalisTwo.getPosterList();
//                String videoUrl = shortFilmList.get(2).getVideoUrl();
//                ontice_playerviewthree.setVideoUrl(videoUrl);



//                //剧照
//                stillsAdapter.addItem(posterList);
//                stillsAdapter.notifyDataSetChanged();
//                Toast.makeText(DetalisHomePageActivity.this, "66666666666", Toast.LENGTH_SHORT).show();

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
