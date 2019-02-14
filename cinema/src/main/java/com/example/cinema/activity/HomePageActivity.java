package com.example.cinema.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.homefragment.CinemaFragment;
import com.example.cinema.homefragment.FilmFragment;
import com.example.cinema.homefragment.MyFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class HomePageActivity extends AppCompatActivity implements CustomAdapt {
    /**
     * 点击返回按钮两次退出
     */
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @BindView(R.id.homeradiobuttonone)
    RadioButton homeradiobuttonone;
    @BindView(R.id.homeradiobuttontwo)
    RadioButton homeradiobuttontwo;
    @BindView(R.id.homeradiobuttonthree)
    RadioButton homeradiobuttonthree;
    private FragmentManager manager;
    private FilmFragment filmFragment;
    private CinemaFragment cinemaFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        ButterKnife.bind(this);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        filmFragment = new FilmFragment();
        cinemaFragment = new CinemaFragment();
        myFragment = new MyFragment();
        transaction.add(R.id.homefragment, filmFragment).show(filmFragment);
        transaction.add(R.id.homefragment, cinemaFragment).hide(cinemaFragment);
        transaction.add(R.id.homefragment, myFragment).hide(myFragment);

        transaction.commit();

        final RadioGroup homeradiogroup = findViewById(R.id.homeradiogroup);
        homeradiogroup.check(homeradiogroup.getChildAt(0).getId());
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeradiobuttonone, "scaleX",1.17f);
        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeradiobuttontwo, "scaleX",1.0f );
        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeradiobuttonthree, "scaleX",1.0f );

        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeradiobuttonone,"scaleY",1.17f);
        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeradiobuttontwo,"scaleY",1.0f);
        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeradiobuttonthree,"scaleY",1.0f);

        //存入集合
        set.playTogether(o1,o2,o3,o4,o5,o6);
        //开始执行
        set.start();


        homeradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.homeradiobuttonone:
                        AnimatorSet set = new AnimatorSet();
                        ObjectAnimator o1 = ObjectAnimator.ofFloat(homeradiobuttonone, "scaleX",1.17f);
                        ObjectAnimator o2 = ObjectAnimator.ofFloat(homeradiobuttontwo, "scaleX",1.0f );
                        ObjectAnimator o3 = ObjectAnimator.ofFloat(homeradiobuttonthree, "scaleX",1.0f );

                        ObjectAnimator o4 = ObjectAnimator.ofFloat(homeradiobuttonone,"scaleY",1.17f);
                        ObjectAnimator o5 = ObjectAnimator.ofFloat(homeradiobuttontwo,"scaleY",1.0f);
                        ObjectAnimator o6 = ObjectAnimator.ofFloat(homeradiobuttonthree,"scaleY",1.0f);

                        //存入集合
                        set.playTogether(o1,o2,o3,o4,o5,o6);
                        //开始执行
                        set.start();
                        //开始执行
                        transaction1.show(filmFragment).hide(cinemaFragment).hide(myFragment);

                        break;
                    case R.id.homeradiobuttontwo:
                        AnimatorSet set1 = new AnimatorSet();
                        ObjectAnimator o11 = ObjectAnimator.ofFloat(homeradiobuttonone, "scaleX",1.0f);
                        ObjectAnimator o21 = ObjectAnimator.ofFloat(homeradiobuttontwo, "scaleX",1.17f );
                        ObjectAnimator o31 = ObjectAnimator.ofFloat(homeradiobuttonthree, "scaleX",1.0f );

                        ObjectAnimator o41 = ObjectAnimator.ofFloat(homeradiobuttonone,"scaleY",1.0f);
                        ObjectAnimator o51 = ObjectAnimator.ofFloat(homeradiobuttontwo,"scaleY",1.17f);
                        ObjectAnimator o61 = ObjectAnimator.ofFloat(homeradiobuttonthree,"scaleY",1.0f);

                        //存入集合
                        set1.playTogether(o11,o21,o31,o41,o51,o61);
                        //开始执行
                        set1.start();
                        transaction1.show(cinemaFragment).hide(filmFragment).hide(myFragment);
                        break;
                    case R.id.homeradiobuttonthree:
                        AnimatorSet set2 = new AnimatorSet();
                        ObjectAnimator o12 = ObjectAnimator.ofFloat(homeradiobuttonone, "scaleX",1.0f);
                        ObjectAnimator o22 = ObjectAnimator.ofFloat(homeradiobuttontwo, "scaleX",1.0f );
                        ObjectAnimator o32 = ObjectAnimator.ofFloat(homeradiobuttonthree, "scaleX",1.17f );

                        ObjectAnimator o42 = ObjectAnimator.ofFloat(homeradiobuttonone,"scaleY",1.0f);
                        ObjectAnimator o52 = ObjectAnimator.ofFloat(homeradiobuttontwo,"scaleY",1.0f);
                        ObjectAnimator o62 = ObjectAnimator.ofFloat(homeradiobuttonthree,"scaleY",1.17f);

                        //存入集合
                        set2.playTogether(o12,o22,o32,o42,o52,o62);
                        //开始执行
                        set2.start();
                        transaction1.show(myFragment).hide(cinemaFragment).hide(filmFragment);
                        break;
                }
                transaction1.commit();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
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


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
