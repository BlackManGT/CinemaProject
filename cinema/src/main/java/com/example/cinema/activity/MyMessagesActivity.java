package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import me.jessyan.autosize.internal.CustomAdapt;

public class MyMessagesActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        SimpleDraweeView mymessagesreturn = findViewById(R.id.mymessagesreturn);
        mymessagesreturn.setOnClickListener(this);
    }

    //返回上一个页面
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mymessagesreturn:
                finish();
                break;
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
