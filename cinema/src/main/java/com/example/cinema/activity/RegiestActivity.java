package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cinema.R;

import me.jessyan.autosize.internal.CustomAdapt;

public class RegiestActivity extends AppCompatActivity implements CustomAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
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
