package com.example.cinema.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.example.cinema.adapter.FilmAdapter;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.BeingMoviePresenter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_list);

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

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
