package com.example.cinema.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.cinema.adapter.FilmAdapter;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.BeingMoviePresenter;
import com.example.cinema.presenter.IsFollowPresenter;
import com.example.cinema.presenter.NoFilmFollowPresenter;
import com.example.cinema.presenter.PopularMoviePresenter;
import com.example.cinema.presenter.SoonMoviePresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class MoiveListActivity extends AppCompatActivity implements CustomAdapt {

    private FilmAdapter twoPopularAdapter;
    private RecyclerView recycleView;
    private PopularMoviePresenter popularMoviePresenter;
    private BeingMoviePresenter beingMoviePresenter;
    private SoonMoviePresenter soonMoviePresenter;
    private RadioButton moivelistbuttonone;
    private RadioButton moivelistbuttontwo;
    private RadioButton moivelistbuttonthree;
    private List<UserInfoBean> userInfoBeans;
    private NoFilmFollowPresenter noFilmFollowPresenter;
    private IsFollowPresenter isFollowPresenter;
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_list);

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(MoiveListActivity.this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        recycleView = findViewById(R.id.moivelistrecycleview);
        //返回上一级页面
        findViewById(R.id.moivelistreturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManagerone = new LinearLayoutManager(this);
        recycleView.setLayoutManager(linearLayoutManagerone);

        popularMoviePresenter = new PopularMoviePresenter(new FilmCall());
        beingMoviePresenter = new BeingMoviePresenter(new FilmCall());
        soonMoviePresenter = new SoonMoviePresenter(new FilmCall());

        RadioGroup moivelistgroup = findViewById(R.id.moivelistgroup);
        moivelistbuttonone = findViewById(R.id.moivelistbuttonone);
        moivelistbuttontwo = findViewById(R.id.moivelistbuttontwo);
        moivelistbuttonthree = findViewById(R.id.moivelistbuttonthree);

        //选择进入哪个页面
        String skrone = getIntent().getStringExtra("skrone");
        if(skrone.equals("1"))
        {
            moivelistbuttonone.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttonone.setTextColor(Color.WHITE);
            moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
            moivelistbuttontwo.setTextColor(Color.BLACK);
            moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonthree.setTextColor(Color.BLACK);
            //热门电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            popularMoviePresenter.reqeust(0,"",1,10);

        }
        else if(skrone.equals("2"))
        {
            moivelistbuttontwo.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttontwo.setTextColor(Color.WHITE);
            moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonone.setTextColor(Color.BLACK);
            moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonthree.setTextColor(Color.BLACK);
            //正在热映电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            beingMoviePresenter.reqeust(0,"",1,10);
        }
        else if(skrone.equals("3"))
        {
            moivelistbuttonthree.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttonthree.setTextColor(Color.WHITE);
            moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonone.setTextColor(Color.BLACK);
            moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
            moivelistbuttontwo.setTextColor(Color.BLACK);
            //即将上映电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            soonMoviePresenter.reqeust(0,"",1,10);
        }

        //切换
        moivelistgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.moivelistbuttonone:
                        moivelistbuttonone.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttonone.setTextColor(Color.WHITE);
                        moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttontwo.setTextColor(Color.BLACK);
                        moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonthree.setTextColor(Color.BLACK);
                        twoPopularAdapter.remove();
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        popularMoviePresenter.reqeust(0,"",1,10);
                        break;
                    case R.id.moivelistbuttontwo:
                        moivelistbuttontwo.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttontwo.setTextColor(Color.WHITE);
                        moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonone.setTextColor(Color.BLACK);
                        moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonthree.setTextColor(Color.BLACK);
                        twoPopularAdapter.remove();
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        beingMoviePresenter.reqeust(0,"",1,10);
                        break;
                    case R.id.moivelistbuttonthree:
                        moivelistbuttonthree.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttonthree.setTextColor(Color.WHITE);
                        moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonone.setTextColor(Color.BLACK);
                        moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttontwo.setTextColor(Color.BLACK);
                        twoPopularAdapter.remove();
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        soonMoviePresenter.reqeust(0,"",1,10);
                        break;
                }
            }
        });


        isFollowPresenter = new IsFollowPresenter(new Guanzhu());
        //关注影片
        userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        sessionId = userInfoBeans.get(0).getSessionId();
        twoPopularAdapter.setFilmAdapterOk(new FilmAdapter.GuanzhuOk() {
            @Override
            public void GuanzhuOnclickOk(int sid) {

                isFollowPresenter.reqeust(userId, sessionId,sid);
            }
        });
        noFilmFollowPresenter = new NoFilmFollowPresenter(new Guanzhu());
        //取消关注影片
        twoPopularAdapter.setFilmAdapterNo(new FilmAdapter.GuanzhuNo() {
            @Override
            public void GuanzhuOnclickNo(int sid) {
                if(userInfoBeans.size() != 0)
                {
                    noFilmFollowPresenter.reqeust(userId, sessionId,sid);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoiveListActivity.this);
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(MoiveListActivity.this, LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
            }
        });

    }



    //热门电影
    class FilmCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                twoPopularAdapter.addItem(moiveBeans);
                twoPopularAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //关注影片
    class Guanzhu implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            Toast.makeText(MoiveListActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
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
