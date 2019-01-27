package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.qfdqc.views.seattable.SeatTable;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        SeatTable seat_view = (SeatTable) findViewById(R.id.seat_view);
        seat_view.setScreenName("8号厅荧幕");//设置屏幕名称
        seat_view.setMaxSelected(3);//设置最多选中

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

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seat_view.setData(10,15);
    }
}
