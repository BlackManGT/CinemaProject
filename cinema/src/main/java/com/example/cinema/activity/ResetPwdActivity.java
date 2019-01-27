package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class ResetPwdActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private Button resetpwd_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        Button resetpwd_return = findViewById(R.id.resetpwd_return);
        resetpwd_return.setOnClickListener(this);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.resetpwd_return:
                finish();
                break;
        }
    }
}
