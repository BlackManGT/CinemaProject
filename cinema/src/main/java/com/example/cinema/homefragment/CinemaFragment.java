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

import com.example.cinema.R;
import com.example.cinema.adapter.CinemaAdapter;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.CinemaPresenter;

public class CinemaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        CinemaPresenter cinemaPresenter = new CinemaPresenter(new CinemaCall());

        RecyclerView recycleView = view.findViewById(R.id.cinemarecycleview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);

        CinemaAdapter cinemaAdapter = new CinemaAdapter();


        cinemaPresenter.reqeust(0,"",1,10);
        return view;
    }
    class CinemaCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
