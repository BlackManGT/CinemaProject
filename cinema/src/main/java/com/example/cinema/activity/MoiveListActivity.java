package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.example.cinema.adapter.BeingAdapter;
import com.example.cinema.adapter.PopularAdapter;
import com.example.cinema.adapter.SoonAdapter;
import com.example.cinema.adapter.TwoPopularAdapter;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.homefragment.FilmFragment;
import com.example.cinema.presenter.BeingMoviePresenter;
import com.example.cinema.presenter.PopularMoviePresenter;
import com.example.cinema.presenter.SoonMoviePresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class MoiveListActivity extends AppCompatActivity implements CustomAdapt {

    private TwoPopularAdapter twoPopularAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_list);

        RecyclerView recycleView = findViewById(R.id.moivelistrecycleview);

        LinearLayoutManager linearLayoutManagerone = new LinearLayoutManager(this);
        recycleView.setLayoutManager(linearLayoutManagerone);

        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new TwoPopularCall());
//        BeingMoviePresenter beingMoviePresenter = new BeingMoviePresenter(new BeingCall());
//        SoonMoviePresenter soonMoviePresenter = new SoonMoviePresenter(new SoonCall());

        //热门电影列表数据
        twoPopularAdapter = new TwoPopularAdapter(this);
        recycleView.setAdapter(twoPopularAdapter);

        popularMoviePresenter.reqeust(0,"",1,10);
//        beingMoviePresenter.reqeust(0,"",1,10);
//        soonMoviePresenter.reqeust(0,"",1,10);


    }



    //热门电影
    class TwoPopularCall implements DataCall<Result>
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
//    //正在上映
//    class BeingCall implements DataCall<Result>
//    {
//        @Override
//        public void success(Result result) {
//            if(result.getStatus().equals("0000"))
//            {
//                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
//                soonAdapter.addItem(moiveBeans);
//                soonAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void fail(ApiException e) {
//
//        }
//    }
//    //即将上映
//    class SoonCall implements DataCall<Result>
//    {
//        @Override
//        public void success(Result result) {
//            if(result.getStatus().equals("0000"))
//            {
//                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
//                beingAdapter.addItem(moiveBeans);
//                beingAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void fail(ApiException e) {
//
//        }
//    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}
