package com.example.cinema.presenter;

import com.example.cinema.core.DataCall;
import com.example.cinema.core.IBaseView;
import com.example.cinema.netutil.NetWorkManager;

import io.reactivex.Observable;

public class NearbyMoivePresenter extends BasePresenter{
    public NearbyMoivePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IBaseView iBaseView = NetWorkManager.getInstance().create(IBaseView.class);
        return iBaseView.Nearby((int) args[0],(String) args[1],(String) args[2],(String) args[3],(int) args[4],(int) args[5]);
    }
}
