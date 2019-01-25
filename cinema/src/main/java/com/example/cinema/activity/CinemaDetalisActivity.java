package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bw.movie.R;
import com.example.cinema.bean.CinemaDetalisBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaDetalisPresenter;

public class CinemaDetalisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detalis);

        //获取传过来的电影ID
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        CinemaDetalisPresenter cinemaDetalisPresenter = new CinemaDetalisPresenter(new CinemaDetalisCall());

        cinemaDetalisPresenter.reqeust(0,"",id);

    }

    class CinemaDetalisCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                CinemaDetalisBean cinemaDetalisBean = (CinemaDetalisBean) result.getResult();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
