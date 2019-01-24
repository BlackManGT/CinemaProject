package com.example.cinema.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import java.util.List;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class FilmFragment extends Fragment implements MovieFlowAdapter.onItemClick,CustomAdapt,View.OnClickListener {

    private RecyclerCoverFlow movieflow;
    private MovieFlowAdapter movieFlowAdapter;
    private PopularAdapter popularAdapter;
    private BeingAdapter beingAdapter;
    private SoonAdapter soonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);

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
                Toast.makeText(getActivity(), ""+(position+1)+"/"+movieflow.getLayoutManager().getItemCount(),Toast.LENGTH_SHORT).show();
            }
        });

        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(new PopularCall());
        BeingMoviePresenter beingMoviePresenter = new BeingMoviePresenter(new BeingCall());
        SoonMoviePresenter soonMoviePresenter = new SoonMoviePresenter(new SoonCall());

        RecyclerView popularRecycleView = view.findViewById(R.id.popular);
        RecyclerView beingRecycleView = view.findViewById(R.id.being);
        RecyclerView soonRecycleView = view.findViewById(R.id.soon);


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

        popularMoviePresenter.reqeust(0,"",1,10);
        beingMoviePresenter.reqeust(0,"",1,10);
        soonMoviePresenter.reqeust(0,"",1,10);
        return view;
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
        switch (v.getId())
        {
            case R.id.populars:
                Intent intentone = new Intent(getActivity(),MoiveListActivity.class);
                intentone.putExtra("skrone","1");
                startActivity(intentone);
                break;
            case R.id.Beings:
                Intent intenttwo = new Intent(getActivity(),MoiveListActivity.class);
                intenttwo.putExtra("skrone","2");
                startActivity(intenttwo);
                break;
            case R.id.Soons:
                Intent intentthree = new Intent(getActivity(),MoiveListActivity.class);
                intentthree.putExtra("skrone","3");
                startActivity(intentthree);
                break;
        }
    }

    //热门电影
    class PopularCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                movieFlowAdapter.addItem(moiveBeans);

                popularAdapter.addItem(moiveBeans);
                popularAdapter.notifyDataSetChanged();
                movieFlowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //正在上映
    class BeingCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                beingAdapter.addItem(moiveBeans);
                beingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //即将上映
    class SoonCall implements DataCall<Result>
    {
        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                soonAdapter.addItem(moiveBeans);
                soonAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void clickItem(int pos) {
        movieflow.smoothScrollToPosition(pos);
    }
}
