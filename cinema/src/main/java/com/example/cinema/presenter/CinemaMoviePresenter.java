package com.example.cinema.presenter;

import com.example.cinema.core.DataCall;
import com.example.cinema.core.IBaseView;
import com.example.cinema.netutil.NetWorkManager;

import io.reactivex.Observable;

public class CinemaMoviePresenter extends BasePresenter{
    public CinemaMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IBaseView iBaseView = NetWorkManager.getInstance().create(IBaseView.class);
        return iBaseView.Cinema((int) args[0],(String) args[1],(int) args[2],(int) args[3]);
    }
}
