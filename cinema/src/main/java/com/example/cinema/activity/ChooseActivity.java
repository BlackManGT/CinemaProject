package com.example.cinema.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.CinemaRecy;
import com.qfdqc.views.seattable.SeatTable;

import java.io.Serializable;

public class ChooseActivity extends AppCompatActivity {

    private TextView textname;
    private RelativeLayout rl_bot;
    private TextView txt_choose_price;
    private double price = 0.0;
    private CinemaRecy cinemaname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        SeatTable seat_view = (SeatTable) findViewById(R.id.seat_view);
        txt_choose_price = findViewById(R.id.txt_choose_price);
        textname = findViewById(R.id.txt_session);
        rl_bot = findViewById(R.id.rl_bot);
        Intent intent = getIntent();
        cinemaname = (CinemaRecy) intent.getSerializableExtra("cinemaname");
        String name = intent.getStringExtra("name");
        Log.d("aaa", "onCreate: "+ cinemaname.toString());
        seat_view.setScreenName(cinemaname.getScreeningHall());//设置屏幕名称
        seat_view.setMaxSelected(2);//设置最多选中
        textname.setText(name);
        if (price == 0.0){
            rl_bot.setVisibility(View.GONE);
        }

        seat_view.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                rl_bot.setVisibility(View.VISIBLE);
            }

            @Override
            public void unCheck(int row, int column) {
                rl_bot.setVisibility(View.GONE);
                price+=cinemaname.getPrice();
                txt_choose_price.setText(price+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seat_view.setData(10,15);
    }
}
