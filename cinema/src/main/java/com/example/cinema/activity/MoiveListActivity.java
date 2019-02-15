package com.example.cinema.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.FilmAdapter;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.BeingMoviePresenter;
import com.example.cinema.presenter.IsFollowPresenter;
import com.example.cinema.presenter.NoFilmFollowPresenter;
import com.example.cinema.presenter.PopularMoviePresenter;
import com.example.cinema.presenter.SoonMoviePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

public class MoiveListActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.cimema_text)
    TextView cimemaText;
    private FilmAdapter twoPopularAdapter;
    private RecyclerView recycleView;
    private PopularMoviePresenter popularMoviePresenter;
    private BeingMoviePresenter beingMoviePresenter;
    private SoonMoviePresenter soonMoviePresenter;
    private RadioButton moivelistbuttonone;
    private RadioButton moivelistbuttontwo;
    private RadioButton moivelistbuttonthree;
    private List<UserInfoBean> userInfoBeans;
    private NoFilmFollowPresenter noFilmFollowPresenter;
    private IsFollowPresenter isFollowPresenter;
    private int userId;
    private String sessionId;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive_list);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(MoiveListActivity.this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        recycleView = findViewById(R.id.moivelistrecycleview);
        initData();
        //返回上一级页面
        findViewById(R.id.moivelistreturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManagerone = new LinearLayoutManager(this);
        recycleView.setLayoutManager(linearLayoutManagerone);

        popularMoviePresenter = new PopularMoviePresenter(new FilmCall());
        beingMoviePresenter = new BeingMoviePresenter(new FilmCall());
        soonMoviePresenter = new SoonMoviePresenter(new FilmCall());

        RadioGroup moivelistgroup = findViewById(R.id.moivelistgroup);
        moivelistbuttonone = findViewById(R.id.moivelistbuttonone);
        moivelistbuttontwo = findViewById(R.id.moivelistbuttontwo);
        moivelistbuttonthree = findViewById(R.id.moivelistbuttonthree);

        //选择进入哪个页面
        String skrone = getIntent().getStringExtra("skrone");
        if (skrone.equals("1")) {
            moivelistbuttonone.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttonone.setTextColor(Color.WHITE);
            moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
            moivelistbuttontwo.setTextColor(Color.BLACK);
            moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonthree.setTextColor(Color.BLACK);
            //热门电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            popularMoviePresenter.reqeust(userId, sessionId, 1, 10);

        } else if (skrone.equals("2")) {
            moivelistbuttontwo.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttontwo.setTextColor(Color.WHITE);
            moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonone.setTextColor(Color.BLACK);
            moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonthree.setTextColor(Color.BLACK);
            //正在热映电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            beingMoviePresenter.reqeust(userId, sessionId, 1, 10);
        } else if (skrone.equals("3")) {
            moivelistbuttonthree.setBackgroundResource(R.drawable.btn_gradient);
            moivelistbuttonthree.setTextColor(Color.WHITE);
            moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
            moivelistbuttonone.setTextColor(Color.BLACK);
            moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
            moivelistbuttontwo.setTextColor(Color.BLACK);
            //即将上映电影列表数据
            twoPopularAdapter = new FilmAdapter(this);
            recycleView.setAdapter(twoPopularAdapter);
            soonMoviePresenter.reqeust(userId, sessionId, 1, 10);
        }

        //切换
        moivelistgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.moivelistbuttonone:
                        moivelistbuttonone.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttonone.setTextColor(Color.WHITE);
                        moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttontwo.setTextColor(Color.BLACK);
                        moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonthree.setTextColor(Color.BLACK);
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        popularMoviePresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                    case R.id.moivelistbuttontwo:
                        moivelistbuttontwo.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttontwo.setTextColor(Color.WHITE);
                        moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonone.setTextColor(Color.BLACK);
                        moivelistbuttonthree.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonthree.setTextColor(Color.BLACK);
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        beingMoviePresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                    case R.id.moivelistbuttonthree:
                        moivelistbuttonthree.setBackgroundResource(R.drawable.btn_gradient);
                        moivelistbuttonthree.setTextColor(Color.WHITE);
                        moivelistbuttonone.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttonone.setTextColor(Color.BLACK);
                        moivelistbuttontwo.setBackgroundResource(R.drawable.myborder);
                        moivelistbuttontwo.setTextColor(Color.BLACK);
                        twoPopularAdapter = new FilmAdapter(MoiveListActivity.this);
                        recycleView.setAdapter(twoPopularAdapter);
                        soonMoviePresenter.reqeust(userId, sessionId, 1, 10);
                        break;
                }
            }
        });


        isFollowPresenter = new IsFollowPresenter(new Guanzhu());
        //关注影片
        userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        sessionId = userInfoBeans.get(0).getSessionId();
        twoPopularAdapter.setFilmAdapterOk(new FilmAdapter.GuanzhuOk() {
            @Override
            public void GuanzhuOnclickOk(int sid) {
                isFollowPresenter.reqeust(userId, sessionId, sid);
            }
        });
        noFilmFollowPresenter = new NoFilmFollowPresenter(new Guanzhu());
        //取消关注影片
        twoPopularAdapter.setFilmAdapterNo(new FilmAdapter.GuanzhuNo() {
            @Override
            public void GuanzhuOnclickNo(int sid) {
                if (userInfoBeans.size() != 0) {
                    noFilmFollowPresenter.reqeust(userId, sessionId, sid);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MoiveListActivity.this);
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(MoiveListActivity.this, LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
            }
        });

    }


    //热门电影
    class FilmCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                twoPopularAdapter.addItem(moiveBeans);
                twoPopularAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //关注影片
    class Guanzhu implements DataCall<Result> {

        @Override
        public void success(Result result) {
            Toast.makeText(MoiveListActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private void initData() {
        mLocationClient = new LocationClient(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            if (!location.equals("")) {
                mLocationClient.stop();
            }
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            cimemaText.setText(addr);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularMoviePresenter.reqeust(userId, sessionId, 1, 10);
        beingMoviePresenter.reqeust(userId, sessionId, 1, 10);
        soonMoviePresenter.reqeust(userId, sessionId, 1, 10);
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
