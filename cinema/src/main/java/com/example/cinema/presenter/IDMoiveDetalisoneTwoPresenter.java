package com.example.cinema.presenter;

import com.example.cinema.core.DataCall;
import com.example.cinema.core.IBaseView;
import com.example.cinema.netutil.NetWorkManager;

import io.reactivex.Observable;

public class IDMoiveDetalisoneTwoPresenter extends BasePresenter{
    public IDMoiveDetalisoneTwoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IBaseView iBaseView = NetWorkManager.getInstance().create(IBaseView.class);
        return iBaseView.IDMoivedetalisTwo((int) args[0],(String) args[1],(int) args[2]);
    }
}
