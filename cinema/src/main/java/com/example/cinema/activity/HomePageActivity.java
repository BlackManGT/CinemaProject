package com.example.cinema.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cinema.R;
import com.example.cinema.homefragment.CinemaFragment;
import com.example.cinema.homefragment.FilmFragment;
import com.example.cinema.homefragment.MyFragment;

import me.jessyan.autosize.internal.CustomAdapt;

public class HomePageActivity extends AppCompatActivity implements CustomAdapt {

    private FragmentManager manager;
    private FilmFragment filmFragment;
    private CinemaFragment cinemaFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        filmFragment = new FilmFragment();
        cinemaFragment = new CinemaFragment();
        myFragment = new MyFragment();
        transaction.add(R.id.homefragment, filmFragment).show(filmFragment);
        transaction.add(R.id.homefragment, cinemaFragment).hide(cinemaFragment);
        transaction.add(R.id.homefragment, myFragment).hide(myFragment);

        transaction.commit();


        RadioGroup homeradiogroup = findViewById(R.id.homeradiogroup);
        homeradiogroup.check(homeradiogroup.getChildAt(0).getId());
        homeradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId)
                {
                    case R.id.homeradiobuttonone:
                        transaction1.show(filmFragment).hide(cinemaFragment).hide(myFragment);
                        break;
                    case R.id.homeradiobuttontwo:
                        transaction1.show(cinemaFragment).hide(filmFragment).hide(myFragment);
                        break;
                    case R.id.homeradiobuttonthree:
                        transaction1.show(myFragment).hide(cinemaFragment).hide(filmFragment);
                        break;
                }
                transaction1.commit();
            }
        });
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
