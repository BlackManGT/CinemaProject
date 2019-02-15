package com.example.cinema.homefragment;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.cinema.activity.LoginActivity;
import com.example.cinema.adapter.CinemaAdapter;
import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaMoviePresenter;
import com.example.cinema.presenter.MyFollowCinemaPresenter;
import com.example.cinema.presenter.MyFollowCinemaPresenterTwo;
import com.example.cinema.presenter.NearbyMoivePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;

public class CinemaFragment extends Fragment implements View.OnClickListener, CustomAdapt {

    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.recommend)
    Button recommend;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.cinemarecycleview)
    RecyclerView cinemarecycleview;
    Unbinder unbinder;
    @BindView(R.id.cinemasdv1)
    SimpleDraweeView cinemasdv1;
    @BindView(R.id.cimema_text1)
    TextView cimemaText;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.seacrch_editext1)
    EditText seacrchEditext1;
    @BindView(R.id.seacrch_text1)
    TextView seacrchText1;
    @BindView(R.id.seacrch_linear21)
    LinearLayout seacrchLinear21;
    private CinemaAdapter cinemaAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CinemaMoviePresenter cinemaPresenter;
    private RecyclerView recycleView;
    private NearbyMoivePresenter nearbyMoivePresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private View view;
    private List<UserInfoBean> userInfoBeans;
    private MyFollowCinemaPresenter myFollowCinemaPresenter;
    private MyFollowCinemaPresenterTwo myFollowCinemaPresenterTwo;
    private int userId;
    private String sessionId;
    private boolean animatort = false;
    private boolean animatorf = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cinema, container, false);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        AutoSizeConfig.getInstance().setCustomFragment(true);
        Button recommend = view.findViewById(R.id.recommend);
        Button nearby = view.findViewById(R.id.nearby);
        recommend.setOnClickListener(this);
        nearby.setOnClickListener(this);
        recycleView = view.findViewById(R.id.cinemarecycleview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);

        cinemaPresenter = new CinemaMoviePresenter(new CinemaCall());
        nearbyMoivePresenter = new NearbyMoivePresenter(new CinemaCall());

        //默认推荐影院
        cinemaAdapter = new CinemaAdapter(getActivity());
        recycleView.setAdapter(cinemaAdapter);
        cinemaPresenter.reqeust(0, "", 1, 10);
        unbinder = ButterKnife.bind(this, view);
        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recommend.setTextColor(Color.WHITE);
        nearby.setBackgroundResource(R.drawable.myborder);
        nearby.setTextColor(Color.BLACK);
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear21, "translationX", 30f, 460f);
        animator.setDuration(0);
        animator.start();
        initData();
        //影院关注

        myFollowCinemaPresenter = new MyFollowCinemaPresenter(new myFollowCinemaCall());
        //接口回调
        cinemaAdapter.setCinemaAdapter(new CinemaAdapter.Follow() {
            @Override
            public void FollowOnclick(int sid) {
                if (userInfoBeans.size() != 0) {
                    userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
                    sessionId = userInfoBeans.get(0).getSessionId();
                    myFollowCinemaPresenter.reqeust(userId, sessionId, sid);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
            }
        });

        //取消关注
        myFollowCinemaPresenterTwo = new MyFollowCinemaPresenterTwo(new myFollowCinemaCall());
        //取消接口回调
        cinemaAdapter.setCinemaTwoAdapter(new CinemaAdapter.FollowTwo() {
            @Override
            public void FollowOnclickTwo(int sid) {
                if (userInfoBeans.size() != 0) {
                    myFollowCinemaPresenterTwo.reqeust(userId, sessionId, sid);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
            }
        });
        return view;
    }

    private void initData() {
        mLocationClient = new LocationClient(getActivity());
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

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recommend) {
            recommend.setBackgroundResource(R.drawable.btn_gradient);
            recommend.setTextColor(Color.WHITE);
            nearby.setBackgroundResource(R.drawable.myborder);
            nearby.setTextColor(Color.BLACK);
            recycleView.setAdapter(cinemaAdapter);
            cinemaPresenter.reqeust(userId, sessionId, 1, 10);
        }
        if (v.getId() == R.id.nearby) {
            nearby.setBackgroundResource(R.drawable.btn_gradient);
            nearby.setTextColor(Color.WHITE);
            recommend.setBackgroundResource(R.drawable.myborder);
            recommend.setTextColor(Color.BLACK);
            recycleView.setAdapter(cinemaAdapter);
            nearbyMoivePresenter.reqeust(userId, sessionId, "116.30551391385724", "40.04571807462411", 1, 10);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        MobclickAgent.onResume(getContext());
        cinemaPresenter.reqeust(userId, sessionId, 1, 10);
        nearbyMoivePresenter.reqeust(userId, sessionId, "116.30551391385724", "40.04571807462411", 1, 10);
    }

    @OnClick({R.id.imageView1, R.id.seacrch_text1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView1:
                if (animatort) {
                    return;
                }
                animatort = true;
                animatorf = false;
                //这是显示出现的动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear21, "translationX", 510f, 30f);
                animator.setDuration(1500);
                animator.start();
                break;
            case R.id.seacrch_text1:
                if (animatorf) {
                    return;
                }
                animatorf = true;
                animatort = false;
                //这是隐藏进去的动画
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(seacrchLinear21, "translationX", 30f, 460f);
                animator2.setDuration(1500);
                animator2.start();
                break;
        }
    }

    class CinemaCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<CinemaBean> cinemaBeans = (List<CinemaBean>) result.getResult();
                cinemaAdapter.addItem(cinemaBeans);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
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
            String addr = location.getAddrStr();    //获取详细地址信息
            cimemaText.setText(addr);
        }
    }

    //关注
    class myFollowCinemaCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), "" + result.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }


}
