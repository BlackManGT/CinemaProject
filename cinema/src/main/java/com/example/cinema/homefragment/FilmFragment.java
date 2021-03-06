package com.example.cinema.homefragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.example.cinema.activity.MoiveListActivity;
import com.example.cinema.adapter.BeingAdapter;
import com.example.cinema.adapter.MovieFlowAdapter;
import com.example.cinema.adapter.PopularAdapter;
import com.example.cinema.adapter.SoonAdapter;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.BeingMoviePresenter;
import com.example.cinema.presenter.PopularMoviePresenter;
import com.example.cinema.presenter.SoonMoviePresenter;
import com.example.cinema.view.CacheManager;
import com.example.cinema.view.SpaceItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class FilmFragment extends Fragment implements MovieFlowAdapter.onItemClick, CustomAdapt, View.OnClickListener {

    @BindView(R.id.imageView)
    ImageView imageView;
    Unbinder unbinder;
    @BindView(R.id.seacrch_text)
    TextView seacrchText;
    @BindView(R.id.seacrch_linear2)
    LinearLayout seacrchLinear2;
    @BindView(R.id.home_radio_group)
    RadioGroup homeRadioGroup;
    @BindView(R.id.film_text)
    TextView filmText;
    private RecyclerCoverFlow movieflow;
    private MovieFlowAdapter movieFlowAdapter;
    private PopularAdapter popularAdapter;
    private BeingAdapter beingAdapter;
    private SoonAdapter soonAdapter;
    private boolean animatort = false;
    private boolean animatorf = false;
    private CacheManager cacheManager;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        AutoSizeConfig.getInstance().setCustomFragment(true);

        RelativeLayout populars = view.findViewById(R.id.populars);
        RelativeLayout Beings = view.findViewById(R.id.Beings);
        RelativeLayout Soons = view.findViewById(R.id.Soons);
        populars.setOnClickListener(this);
        Beings.setOnClickListener(this);
        Soons.setOnClickListener(this);

        movieflow = view.findViewById(R.id.movieflow);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        movieFlowAdapter = new MovieFlowAdapter(getActivity());
        movieflow.setAdapter(movieFlowAdapter);
        movieflow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                homeRadioGroup.check(homeRadioGroup.getChildAt(position).getId());
                //Toast.makeText(getActivity(), "" + (position + 1) + "/" + movieflow.getLayoutManager().getItemCount(), Toast.LENGTH_SHORT).show();
            }
        });

        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new PopularCall());
        BeingMoviePresenter beingMoviePresenter = new BeingMoviePresenter(new BeingCall());
        SoonMoviePresenter soonMoviePresenter = new SoonMoviePresenter(new SoonCall());

        RecyclerView popularRecycleView = view.findViewById(R.id.popular);
        RecyclerView beingRecycleView = view.findViewById(R.id.being);
        RecyclerView soonRecycleView = view.findViewById(R.id.soon);
        popularRecycleView.addItemDecoration(new SpaceItemDecoration(10));
        beingRecycleView.addItemDecoration(new SpaceItemDecoration(10));
        soonRecycleView.addItemDecoration(new SpaceItemDecoration(10));

        //热门电影列表数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularRecycleView.setLayoutManager(linearLayoutManager);
        popularAdapter = new PopularAdapter(getActivity());
        popularRecycleView.setAdapter(popularAdapter);
        //正在上映
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        beingRecycleView.setLayoutManager(linearLayoutManager2);
        beingAdapter = new BeingAdapter(getActivity());
        beingRecycleView.setAdapter(beingAdapter);
        //即将上映
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        soonRecycleView.setLayoutManager(linearLayoutManager3);
        soonAdapter = new SoonAdapter(getActivity());
        soonRecycleView.setAdapter(soonAdapter);

        popularMoviePresenter.reqeust(0, "", 1, 10);
        beingMoviePresenter.reqeust(0, "", 1, 10);
        soonMoviePresenter.reqeust(0, "", 1, 10);
        unbinder = ButterKnife.bind(this, view);
        //这是刚进页面设置的动画状态
        ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 30f, 460f);
        animator.setDuration(0);
        animator.start();
        cacheManager = new CacheManager();
        initData();
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

    //点金进入//热门//上映//即将
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.populars:
                Intent intentone = new Intent(getActivity(), MoiveListActivity.class);
                intentone.putExtra("skrone", "1");
                startActivity(intentone);
                break;
            case R.id.Beings:
                Intent intenttwo = new Intent(getActivity(), MoiveListActivity.class);
                intenttwo.putExtra("skrone", "2");
                startActivity(intenttwo);
                break;
            case R.id.Soons:
                Intent intentthree = new Intent(getActivity(), MoiveListActivity.class);
                intentthree.putExtra("skrone", "3");
                startActivity(intentthree);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imageView, R.id.seacrch_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                if (animatort) {
                    return;
                }
                animatort = true;
                animatorf = false;
                //这是显示出现的动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 460f, 30f);
                animator.setDuration(1500);
                animator.start();
                break;
            case R.id.seacrch_text:
                if (animatorf) {
                    return;
                }
                animatorf = true;
                animatort = false;
                //这是隐藏进去的动画
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(seacrchLinear2, "translationX", 30f, 460f);
                animator2.setDuration(1500);
                animator2.start();
                break;
        }
    }

    //热门电影
    class PopularCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                for (int i = 0; i < moiveBeans.size(); i++) {
                    RadioButton radioButton = new RadioButton(getActivity());
                    WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                    int width = wm.getDefaultDisplay().getWidth();
                    double widths = width / moiveBeans.size();
                    radioButton.setWidth((int) widths);
                    radioButton.setButtonTintMode(PorterDuff.Mode.CLEAR);
                    radioButton.setBackgroundResource(R.drawable.radio_selector);
                    homeRadioGroup.addView(radioButton);
                }
                homeRadioGroup.check(homeRadioGroup.getChildAt(0).getId());

                movieFlowAdapter.addItem(moiveBeans);
                cacheManager.saveDataToFile(getContext(), new Gson().toJson(moiveBeans), "popularcall");
                popularAdapter.addItem(moiveBeans);
                popularAdapter.notifyDataSetChanged();
                movieFlowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "popularcall");
            Type type = new TypeToken<List<MoiveBean>>() {
            }.getType();
            List<MoiveBean> moiveBeans = new Gson().fromJson(s, type);
            movieFlowAdapter.addItem(moiveBeans);
            movieFlowAdapter.notifyDataSetChanged();
            popularAdapter.addItem(moiveBeans);
            popularAdapter.notifyDataSetChanged();
        }
    }

    //正在上映
    class BeingCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                cacheManager.saveDataToFile(getContext(), new Gson().toJson(moiveBeans), "beingcall");
                beingAdapter.addItem(moiveBeans);
                beingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "beingcall");
            Type type = new TypeToken<List<MoiveBean>>() {
            }.getType();
            List<MoiveBean> moiveBeans = new Gson().fromJson(s, type);
            beingAdapter.addItem(moiveBeans);
            beingAdapter.notifyDataSetChanged();
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if (!addr.equals("")) {
                filmText.setText(addr);
                mLocationClient.stop();
            }

        }
    }


    //即将上映
    class SoonCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                cacheManager.saveDataToFile(getContext(), new Gson().toJson(moiveBeans), "sooncall");
                soonAdapter.addItem(moiveBeans);
                soonAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "sooncall");
            Type type = new TypeToken<List<MoiveBean>>() {
            }.getType();
            List<MoiveBean> moiveBeans = new Gson().fromJson(s, type);
            soonAdapter.addItem(moiveBeans);
            soonAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void clickItem(int pos) {
        movieflow.smoothScrollToPosition(pos);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

}
