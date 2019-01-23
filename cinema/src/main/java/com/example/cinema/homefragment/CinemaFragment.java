package com.example.cinema.homefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.R;
import com.example.cinema.adapter.CinemaAdapter;
import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaMoviePresenter;
import com.example.cinema.presenter.NearbyMoivePresenter;

import java.util.List;

public class CinemaFragment extends Fragment implements View.OnClickListener{

    private CinemaAdapter cinemaAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CinemaMoviePresenter cinemaPresenter;
    private RecyclerView recycleView;
    private NearbyMoivePresenter nearbyMoivePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

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
        cinemaPresenter.reqeust(0,"",1,10);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.recommend)
        {
            cinemaAdapter.remove();
            cinemaAdapter = new CinemaAdapter(getActivity());
            recycleView.setAdapter(cinemaAdapter);
            cinemaPresenter.reqeust(0,"",1,10);
        }
        if(v.getId() == R.id.nearby)
        {
            cinemaAdapter.remove();
            cinemaAdapter = new CinemaAdapter(getActivity());
            recycleView.setAdapter(cinemaAdapter);
            nearbyMoivePresenter.reqeust(0,"","116.30551391385724","40.04571807462411",1,10);
        }
    }

    class CinemaCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<CinemaBean> cinemaBeans = (List<CinemaBean>) result.getResult();
                cinemaAdapter.addItem(cinemaBeans);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
