package com.example.cinema.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.CinemaRecycleAdapter;
import com.example.cinema.bean.CinemaById;
import com.example.cinema.bean.CinemaRecy;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaByIdPresenter;
import com.example.cinema.presenter.CinemaRecyPresenter;
import com.example.cinema.presenter.IDMoiveDetalisoneTwoPresenter;
import com.example.cinema.view.SpaceItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class SchedulingActivity extends AppCompatActivity implements CustomAdapt {

    private CinemaRecycleAdapter cinemaRecycleAdapter;
    private SimpleDraweeView scheduling_sdv;
    private TextView scheduling_textviewone;
    private TextView scheduling_textviewtwo;
    private TextView scheduling_textviewthree;
    private TextView scheduling_textviewfour;
    private TextView scheduling_textviewfive;
    private IDMoiveDetalisTwo idMoiveDetalisTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        List<UserInfoBean> userInfoBeans = userInfoBeanDao.loadAll();
        int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        String sessionId = userInfoBeans.get(0).getSessionId();
        scheduling_sdv = findViewById(R.id.scheduling_sdv);
        scheduling_textviewone = findViewById(R.id.scheduling_textviewone);
        scheduling_textviewtwo = findViewById(R.id.scheduling_textviewtwo);
        scheduling_textviewthree = findViewById(R.id.scheduling_textviewthree);
        scheduling_textviewfour = findViewById(R.id.scheduling_textviewfour);
        scheduling_textviewfive = findViewById(R.id.scheduling_textviewfive);

        IDMoiveDetalisoneTwoPresenter idMoiveDetalisoneTwoPresenter = new IDMoiveDetalisoneTwoPresenter(new DetalisCinemaCall());

        CinemaRecyPresenter cinemaRecyPresenter = new CinemaRecyPresenter(new SchedulingCall());

        CinemaByIdPresenter cinemaByIdPresenter = new CinemaByIdPresenter(new MyCall());

        //获取传过来的影院ID/影院地址/电影id
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        int cinemaid = Integer.parseInt(getIntent().getStringExtra("id"));
        int filmid = Integer.parseInt(getIntent().getStringExtra("filmid"));

        TextView scheduling_name = findViewById(R.id.scheduling_name);
        scheduling_name.setText(name);
        TextView scheduling_address = findViewById(R.id.scheduling_address);
        scheduling_address.setText(address);
        //返回上级目录
        findViewById(R.id.scheduling_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView scheduling_recycleview = findViewById(R.id.scheduling_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scheduling_recycleview.setLayoutManager(linearLayoutManager);
        cinemaRecycleAdapter = new CinemaRecycleAdapter(this);
        scheduling_recycleview.setAdapter(cinemaRecycleAdapter);
        scheduling_recycleview.addItemDecoration(new SpaceItemDecoration(10));
        cinemaRecyPresenter.reqeust(cinemaid,filmid);
        idMoiveDetalisoneTwoPresenter.reqeust(userId,sessionId,filmid);
        cinemaByIdPresenter.reqeust(filmid);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    class SchedulingCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<CinemaRecy> cinemaRecies = (List<CinemaRecy>) result.getResult();
                cinemaRecycleAdapter.addList(cinemaRecies);
                cinemaRecycleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class DetalisCinemaCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();
                scheduling_sdv.setImageURI(idMoiveDetalisTwo.getImageUrl());
                scheduling_textviewone.setText(idMoiveDetalisTwo.getName());
                scheduling_textviewtwo.setText("类型："+ idMoiveDetalisTwo.getMovieTypes());
                scheduling_textviewthree.setText("导演："+ idMoiveDetalisTwo.getDirector());
                scheduling_textviewfour.setText("时长："+ idMoiveDetalisTwo.getDuration());
                scheduling_textviewfive.setText("产地："+ idMoiveDetalisTwo.getPlaceOrigin());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //获取名字
    class MyCall implements DataCall<Result<List<CinemaById>>> {

        @Override
        public void success(Result<List<CinemaById>> result) {
            if (result.getStatus().equals("0000")) {
                List<String> list = new ArrayList<>();
                List<CinemaById> cinemaByIds = result.getResult();
                for (int i = 0; i < cinemaByIds.size(); i++) {
                    String name = cinemaByIds.get(i).getName();
                    list.add(name);
                }
                cinemaRecycleAdapter.addName(list);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
