package com.example.cinema.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.bw.movie.R;


public class StartupPageActivity extends AppCompatActivity {

    int CountNum = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        handler.sendEmptyMessageDelayed(100,1000);
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 100)
            {
                if(CountNum <= 0)
                {
                    Intent intent = new Intent(StartupPageActivity.this,HomePageActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                CountNum--;
                handler.sendEmptyMessageDelayed(100,1000);
            }
        }
    };
}
