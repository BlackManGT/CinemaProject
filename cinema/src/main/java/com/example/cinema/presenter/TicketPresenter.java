package com.example.cinema.presenter;

import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.IBaseView;
import com.example.cinema.netutil.NetWorkManager;

import io.reactivex.Observable;

public class TicketPresenter extends BasePresenter {
    public TicketPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IBaseView iBaseView = NetWorkManager.getInstance().create(IBaseView.class);
        return iBaseView.findTicket((String)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
