package com.example.cinema.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.bw.movie.R;


public class GuidanceActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean boot = sp.getBoolean("boot", true);
        if(boot){
            Intent intent = new Intent(GuidanceActivity.this,BootpageActivity.class);
            startActivity(intent);
            finish();
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("boot",false);
            edit.commit();
        }else{
            Intent intent = new Intent(GuidanceActivity.this,StartupPageActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
