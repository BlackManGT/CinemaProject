package com.example.cinema.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import com.bw.movie.R;
import com.example.cinema.adapter.FilmReviewAdapter;
import com.example.cinema.adapter.NoticeAdapter;
import com.example.cinema.adapter.StillsAdapter;
import com.example.cinema.bean.FilmReviewBean;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.FilmReviewPresenter;
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
    private View stills;
    private StillsAdapter stillsAdapter;
    private RecyclerView notice_recycleview;
    private NoticeAdapter noticeAdapter;
    private RecyclerView stills_recycleview;
    private FilmReviewPresenter filmReviewPresenter;
    private View filmreview;
    private FilmReviewAdapter filmReviewAdapter;
    private Button detalisbuttonone;
    private Button detalisbuttontwo;
    private Button detalisbuttonthree;
    private Button detalisbuttonfour;
    private IDMoiveDetalisOne idMoiveDetalisOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis_home_page);

        //得到电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        IDMoiveDetalisonePresenter idMoiveDetalisonePresenter = new IDMoiveDetalisonePresenter(new IDMoiveDetalisOneCall());
        idMoiveDetalisonePresenter.reqeust(0,"", id);
        //按钮
        detalisbuttonone = findViewById(R.id.detalisbuttonone);
        detalisbuttontwo = findViewById(R.id.detalisbuttontwo);
        detalisbuttonthree = findViewById(R.id.detalisbuttonthree);
        detalisbuttonfour = findViewById(R.id.detalisbuttonfour);
        detalisbuttonone.setOnClickListener(this);
        detalisbuttontwo.setOnClickListener(this);
        detalisbuttonthree.setOnClickListener(this);
        detalisbuttonfour.setOnClickListener(this);

        //购票按钮
        Button purchase = findViewById(R.id.purchase);
        purchase.setOnClickListener(this);

        bottomDialog = new Dialog(DetalisHomePageActivity.this, R.style.BottomDialog);

        idMoiveDetalisoneTwoPresenter = new IDMoiveDetalisoneTwoPresenter(new Dialog_DetalisCall());
        //影评
        filmReviewPresenter = new FilmReviewPresenter(new FilmReviewCall());



        detalishomepagesdvone = findViewById(R.id.detalishomepagesdvone);
        Button detalishomepagebutton = findViewById(R.id.detalishomepagebutton);
        detalishomepagename = findViewById(R.id.detalishomepagename);
        detalishomepagesdvtwo = findViewById(R.id.detalishomepagesdvtwo);
        detalishomepagebutton.setOnClickListener(this);

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
        notice_recycleview = notice.findViewById(R.id.notice_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notice_recycleview.setLayoutManager(linearLayoutManager);
        noticeAdapter = new NoticeAdapter(this);
        notice_recycleview.setAdapter(noticeAdapter);

        //剧照
        stills = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_stills, null);
        stills.findViewById(R.id.stills_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        stills_recycleview = stills.findViewById(R.id.stills_recycleview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        stills_recycleview.setLayoutManager(staggeredGridLayoutManager);
        stillsAdapter = new StillsAdapter(this);
        stills_recycleview.setAdapter(stillsAdapter);

        //影评
        filmreview = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_filmreview, null);
        RecyclerView filmreview_recycleview = filmreview.findViewById(R.id.filmreview_recycleview);
        LinearLayoutManager linearLayoutManagerfilmreview = new LinearLayoutManager(this);
        filmreview_recycleview.setLayoutManager(linearLayoutManagerfilmreview);
        filmReviewAdapter = new FilmReviewAdapter(this);
        filmreview_recycleview.setAdapter(filmReviewAdapter);
        filmreview.findViewById(R.id.filmreview_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        //添加评论

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.detalishomepagebutton:
                finish();
                break;
            case R.id.detalisbuttonone:
                bottomDialog.setContentView(detalis);
                ViewGroup.LayoutParams layoutParamsDetalis = detalis.getLayoutParams();
                layoutParamsDetalis.width = getResources().getDisplayMetrics().widthPixels;
                detalis.setLayoutParams(layoutParamsDetalis);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
                break;
            case R.id.detalisbuttontwo:
                bottomDialog.setContentView(notice);
                ViewGroup.LayoutParams layoutParamsnotice= notice.getLayoutParams();
                layoutParamsnotice.width = getResources().getDisplayMetrics().widthPixels;
                notice.setLayoutParams(layoutParamsnotice);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
                break;
            case R.id.detalisbuttonthree:
                bottomDialog.setContentView(stills);
                ViewGroup.LayoutParams layoutParamsStills = stills.getLayoutParams();
                layoutParamsStills.width = getResources().getDisplayMetrics().widthPixels;
                stills.setLayoutParams(layoutParamsStills);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                idMoiveDetalisoneTwoPresenter.reqeust(0,"", id);
                break;
            case R.id.detalisbuttonfour:
                bottomDialog.setContentView(filmreview);
                ViewGroup.LayoutParams layoutParamsthreefilmreview = filmreview.getLayoutParams();
                layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                filmreview.setLayoutParams(layoutParamsthreefilmreview);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                filmReviewPresenter.reqeust(0,"",id,1,10);
                break;
            case R.id.purchase:
                Intent intent = new Intent(DetalisHomePageActivity.this,PurchaseActivity.class);
                intent.putExtra("id",id+"");
                intent.putExtra("name",idMoiveDetalisOne.getName());
                startActivity(intent);
                break;
        }
    }

    class IDMoiveDetalisOneCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                idMoiveDetalisOne = (IDMoiveDetalisOne) result.getResult();
                 detalishomepagename.setText(idMoiveDetalisOne.getName());
                 detalishomepagesdvtwo.setImageURI(Uri.parse(idMoiveDetalisOne.getImageUrl()));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //详情//预告/剧照
    class Dialog_DetalisCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                IDMoiveDetalisTwo idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();
                List<IDMoiveDetalisTwo.ShortFilmListBean> shortFilmList = idMoiveDetalisTwo.getShortFilmList();
                List<String> posterList = idMoiveDetalisTwo.getPosterList();
                Log.e("============",shortFilmList.size()+"");
                //详情
                dialog_detalis_sdvone.setImageURI(Uri.parse(idMoiveDetalisTwo.getImageUrl()));
                dialog_detalis_leixing.setText("类型："+idMoiveDetalisTwo.getMovieTypes());
                dialog_detalis_daoyan.setText("导演："+idMoiveDetalisTwo.getDirector());
                dialog_detalis_shichang.setText("时长："+idMoiveDetalisTwo.getDuration());
                dialog_detalis_chandi.setText("产地："+idMoiveDetalisTwo.getPlaceOrigin());
                plot.setText(idMoiveDetalisTwo.getSummary());

                //预告片
                noticeAdapter.addItem(shortFilmList);
                //剧照
                stillsAdapter.addItem(posterList);


                noticeAdapter.notifyDataSetChanged();
                stillsAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //影评
    class FilmReviewCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<FilmReviewBean> filmReviewBeans = (List<FilmReviewBean>) result.getResult();
                filmReviewAdapter.addItem(filmReviewBeans);
                filmReviewAdapter.notifyDataSetChanged();
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
