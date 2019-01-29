package com.example.cinema.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.MyIsFollowAdapter;
import com.example.cinema.bean.MyIsFollowListBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaAttentionPresenter;
import com.example.cinema.presenter.MyIsFollowListPresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class MyFollowActivity extends AppCompatActivity implements CustomAdapt {

    private RadioButton guanzhu_radiobuttonone;
    private RadioButton guanzhu_radiobuttontwo;
    private MyIsFollowAdapter myIsFollowAdapter;
    private RecyclerView guanzhu_recycleview;
    private int userId;
    private String sessionId;
    private CinemaAttentionPresenter cinemaAttentionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu);

        cinemaAttentionPresenter = new CinemaAttentionPresenter(new MyList());

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        List<UserInfoBean> userInfoBeans = userInfoBeanDao.loadAll();
        userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        sessionId = userInfoBeans.get(0).getSessionId();

        guanzhu_recycleview = findViewById(R.id.guanzhu_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        guanzhu_recycleview.setLayoutManager(linearLayoutManager);
        MyIsFollowListPresenter myIsFollowListPresenter = new MyIsFollowListPresenter(new MyList());
        myIsFollowAdapter = new MyIsFollowAdapter(this);
        guanzhu_recycleview.setAdapter(myIsFollowAdapter);
        myIsFollowListPresenter.reqeust(userId, sessionId,1,10);

        findViewById(R.id.guanzhu_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RadioGroup guanzhu_radiogroup = findViewById(R.id.guanzhu_radiogroup);
        guanzhu_radiobuttonone = findViewById(R.id.guanzhu_radiobuttonone);
        guanzhu_radiobuttontwo = findViewById(R.id.guanzhu_radiobuttontwo);
        guanzhu_radiobuttonone.setBackgroundResource(R.drawable.btn_gradient);
        guanzhu_radiobuttonone.setTextColor(Color.WHITE);
        guanzhu_radiobuttontwo.setBackgroundResource(R.drawable.myborder);
        guanzhu_radiobuttontwo.setTextColor(Color.BLACK);
        guanzhu_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.guanzhu_radiobuttonone://电影关注列表
                        guanzhu_radiobuttonone.setBackgroundResource(R.drawable.btn_gradient);
                        guanzhu_radiobuttonone.setTextColor(Color.WHITE);
                        guanzhu_radiobuttontwo.setBackgroundResource(R.drawable.myborder);
                        guanzhu_radiobuttontwo.setTextColor(Color.BLACK);
                        MyIsFollowListPresenter myIsFollowListPresenter = new MyIsFollowListPresenter(new MyList());
                        myIsFollowAdapter = new MyIsFollowAdapter(MyFollowActivity.this);
                        guanzhu_recycleview.setAdapter(myIsFollowAdapter);
                        myIsFollowListPresenter.reqeust(userId, sessionId,1,10);
                        break;
                    case R.id.guanzhu_radiobuttontwo://影院关注列表
                        guanzhu_radiobuttonone.setBackgroundResource(R.drawable.myborder);
                        guanzhu_radiobuttonone.setTextColor(Color.BLACK);
                        guanzhu_radiobuttontwo.setBackgroundResource(R.drawable.btn_gradient);
                        guanzhu_radiobuttontwo.setTextColor(Color.WHITE);
                        guanzhu_recycleview.setAdapter(myIsFollowAdapter);
                        cinemaAttentionPresenter.reqeust(userId, sessionId,1,10);
                    break;
                }
            }
        });

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //成功得到数据
    class MyList implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(MyFollowActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                List<MyIsFollowListBean> myIsFollowListBeans = (List<MyIsFollowListBean>) result.getResult();
                myIsFollowAdapter.addItem(myIsFollowListBeans);
                myIsFollowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
