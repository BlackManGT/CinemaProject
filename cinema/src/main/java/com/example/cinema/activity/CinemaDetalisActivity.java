package com.example.cinema.activity;

import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.CinemaDetalisAdapter;
import com.example.cinema.adapter.CinemaFlowAdapter;
import com.example.cinema.adapter.CinemaPinglunAdapter;
import com.example.cinema.adapter.CinemaRecycleAdapter;
import com.example.cinema.bean.CDBean;
import com.example.cinema.bean.CPBean;
import com.example.cinema.bean.CinemaById;
import com.example.cinema.bean.CinemaDetalisBean;
import com.example.cinema.bean.CinemaRecy;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CDPresenter;
import com.example.cinema.presenter.CPPresenter;
import com.example.cinema.presenter.CinemaByIdPresenter;
import com.example.cinema.presenter.CinemaDetalisPresenter;
import com.example.cinema.presenter.CinemaRecyPresenter;
import com.example.cinema.view.SpaceItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDetalisActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.cinema_recy2)
    RecyclerView cinemaRecy;
    private RecyclerCoverFlow horse;
    private SimpleDraweeView cinema_detalis_sdvone;
    private TextView cinema_detalis_textviewone;
    private TextView cinema_detalis_textviewtwo;
    private CinemaByIdPresenter cinemaByIdPresenter;
    private CinemaFlowAdapter cinemaFlowAdapter;
    private CinemaRecyPresenter cinemaRecyPresenter;
    private CinemaRecycleAdapter cinemaRecycleAdapter;
    private List<CinemaById> cinemaByIds;
    private int p = 0;
    private int id;
    private RelativeLayout cinemarelativeLayout;
    private Dialog bottomDialog;
    private View dialog_cinemadetalispinglun;
    private List<UserInfoBean> userInfoBeans;
    private CinemaDetalisAdapter cinemaDetalisAdapter;
    private RecyclerView cinemarecycleViewOne;
    private RecyclerView cinemarecycleViewTwo;
    private int userId;
    private String sessionId;
    private CPPresenter cpPresenter;
    private CinemaPinglunAdapter cinemaGuanzhuAdapter;
    private View lineone;
    private View linetwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cinema_detalis);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        cinema_detalis_sdvone = findViewById(R.id.cinema_detalis_sdvone);
        cinema_detalis_textviewone = findViewById(R.id.cinema_detalis_textviewone);
        cinema_detalis_textviewtwo = findViewById(R.id.cinema_detalis_textviewtwo);

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();
        userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        sessionId = userInfoBeans.get(0).getSessionId();


        //获取传过来的电影ID
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        CinemaDetalisPresenter cinemaDetalisPresenter = new CinemaDetalisPresenter(new CinemaDetalisCall());
        cinemaDetalisPresenter.reqeust(0, "", id);
        cinemaByIdPresenter = new CinemaByIdPresenter(new MyCall());
        cinemaFlowAdapter = new CinemaFlowAdapter(this);
        horse = findViewById(R.id.cinema_detalis_horse);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变
        horse.setAdapter(cinemaFlowAdapter);
        cinemaByIdPresenter.reqeust(id);
        horse.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                p = cinemaByIds.get(position).getId();
                Log.d("id23", "onItemSelected: "+p);
                cinemaRecycleAdapter.clearList();
                cinemaRecyPresenter.reqeust(id,p);
            }
        });
        cinemaRecycleAdapter = new CinemaRecycleAdapter(this);
        cinemaRecyPresenter = new CinemaRecyPresenter(new My());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        cinemaRecy.setLayoutManager(manager);
        cinemaRecy.setAdapter(cinemaRecycleAdapter);
        cinemaRecy.addItemDecoration(new SpaceItemDecoration(40));

        //点击弹框
        cinemarelativeLayout = findViewById(R.id.cinemaRelativeLayout);
        bottomDialog = new Dialog(CinemaDetalisActivity.this, R.style.BottomDialog);
        dialog_cinemadetalispinglun = View.inflate(this, R.layout.dialog_cinemadetalispinglun, null);
        dialog_cinemadetalispinglun.findViewById(R.id.cinemasdvsss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        RadioGroup cinemaradiogroup = dialog_cinemadetalispinglun.findViewById(R.id.cinemaradiogroup);
        cinemarelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.setContentView(dialog_cinemadetalispinglun);
                ViewGroup.LayoutParams layoutParamsDetalis = dialog_cinemadetalispinglun.getLayoutParams();
                layoutParamsDetalis.width = getResources().getDisplayMetrics().widthPixels;
                dialog_cinemadetalispinglun.setLayoutParams(layoutParamsDetalis);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
            }
        });
        cinemarecycleViewOne = dialog_cinemadetalispinglun.findViewById(R.id.cinemarecycleViewOne);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cinemarecycleViewOne.setLayoutManager(linearLayoutManager);
        //创建设置适配器
        cinemaDetalisAdapter = new CinemaDetalisAdapter(this);
        cinemarecycleViewOne.setAdapter(cinemaDetalisAdapter);
        CDPresenter cdPresenter = new CDPresenter(new CDCall());
        cdPresenter.reqeust(userId, sessionId,id);

        //点击切换内容
        cinemarecycleViewTwo = dialog_cinemadetalispinglun.findViewById(R.id.cinemarecycleViewTwo);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        cinemarecycleViewTwo.setLayoutManager(linearLayoutManager1);
        cpPresenter = new CPPresenter(new CPCall());


        lineone = dialog_cinemadetalispinglun.findViewById(R.id.lineone);
        linetwo = dialog_cinemadetalispinglun.findViewById(R.id.linetwo);
        cinemaradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.cinemaradiobuttonone:
                        cinemarecycleViewOne.setVisibility(View.VISIBLE);
                        cinemarecycleViewTwo.setVisibility(View.GONE);
                        lineone.setVisibility(View.VISIBLE);
                        linetwo.setVisibility(View.GONE);
                        break;
                    case R.id.cinemaradiobuttontwo:
                        cinemarecycleViewOne.setVisibility(View.GONE);
                        cinemarecycleViewTwo.setVisibility(View.VISIBLE);
                        lineone.setVisibility(View.GONE);
                        linetwo.setVisibility(View.VISIBLE);
                        //创建适配器
                        cinemaGuanzhuAdapter = new CinemaPinglunAdapter(CinemaDetalisActivity.this);
                        cinemarecycleViewTwo.setAdapter(cinemaGuanzhuAdapter);
                        cpPresenter.reqeust(userId,sessionId,id,1,10);
                        break;
                }
            }
        });

    }
    class My implements DataCall<Result<List<CinemaRecy>>>{

        @Override
        public void success(Result<List<CinemaRecy>> result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(CinemaDetalisActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                List<CinemaRecy> result1 = result.getResult();
                cinemaRecycleAdapter.addList(result1);
                cinemaRecycleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class MyCall implements DataCall<Result<List<CinemaById>>> {

        @Override
        public void success(Result<List<CinemaById>> result) {
            if (result.getStatus().equals("0000")) {
                List<String> list = new ArrayList<>();
                cinemaByIds = result.getResult();
                p=cinemaByIds.get(0).getId();
                cinemaRecyPresenter.reqeust(id,p);
                Log.d("id2", "success: "+p);
                cinemaFlowAdapter.addItem(cinemaByIds);
                cinemaFlowAdapter.notifyDataSetChanged();
                for (int i = 0; i < cinemaByIds.size() ; i++) {
                    list.add(cinemaByIds.get(i).getName());
                }
                cinemaRecycleAdapter.addName(list);
            }
        }

        @Override
        public void fail(ApiException e) {

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


    //电影院信息
    class CinemaDetalisCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                CinemaDetalisBean cinemaDetalisBean = (CinemaDetalisBean) result.getResult();
                cinema_detalis_sdvone.setImageURI(Uri.parse(cinemaDetalisBean.getLogo()));
                cinema_detalis_textviewone.setText(cinemaDetalisBean.getName());
                cinema_detalis_textviewtwo.setText(cinemaDetalisBean.getAddress());
                Log.i("abc", "success: " + new Gson().toJson(result));

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //影院详情信息
    class CDCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            CDBean cdBean = (CDBean) result.getResult();
            cinemaDetalisAdapter.addItem(cdBean);
            cinemaDetalisAdapter.notifyDataSetChanged();

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //影院详情信息
    class CPCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            Toast.makeText(CinemaDetalisActivity.this, "评论"+result.getMessage(), Toast.LENGTH_SHORT).show();
            List<CPBean> cpBeans = (List<CPBean>) result.getResult();
            cinemaGuanzhuAdapter.addItem(cpBeans);
            cinemaGuanzhuAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
