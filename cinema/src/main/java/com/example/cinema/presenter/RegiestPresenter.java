package com.example.cinema.presenter;

import com.example.cinema.core.DataCall;
import com.example.cinema.core.IBaseView;
import com.example.cinema.netutil.NetWorkManager;

import io.reactivex.Observable;

public class RegiestPresenter extends BasePresenter {
    public RegiestPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IBaseView iBaseView = NetWorkManager.getInstance().create(IBaseView.class);
        return iBaseView.regiest((String)args[0],(String)args[1],(String)args[2],(String) args[3],
                (int) args[4],(String)args[5],(String)args[6],(String)args[7],(String)args[8],(String)args[9]
                ,(String)args[10]);
    }
}
