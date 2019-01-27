package com.example.cinema.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.example.cinema.adapter.CinemaFlowAdapter;
import com.example.cinema.adapter.CinemaRecycleAdapter;
import com.example.cinema.bean.CinemaById;
import com.example.cinema.bean.CinemaDetalisBean;
import com.example.cinema.bean.CinemaRecy;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaByIdPresenter;
import com.example.cinema.presenter.CinemaDetalisPresenter;
import com.example.cinema.presenter.CinemaRecyPresenter;
import com.example.cinema.view.SpaceItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
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
    private int p =4;;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detalis);
        ButterKnife.bind(this);

        cinema_detalis_sdvone = findViewById(R.id.cinema_detalis_sdvone);
        cinema_detalis_textviewone = findViewById(R.id.cinema_detalis_textviewone);
        cinema_detalis_textviewtwo = findViewById(R.id.cinema_detalis_textviewtwo);


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
        cinemaRecy.addItemDecoration(new SpaceItemDecoration(10));

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

                cinemaByIds = result.getResult();
                p=cinemaByIds.get(0).getId();
                cinemaRecyPresenter.reqeust(id,p);
                Log.d("id2", "success: "+p);
                cinemaFlowAdapter.addItem(cinemaByIds);
                cinemaFlowAdapter.notifyDataSetChanged();
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
}
