package com.example.cinema.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.MyAdapter;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.TicketBean;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.TicketPresenter;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyTicketActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.ticket_wait_money)
    Button ticketWaitMoney;
    @BindView(R.id.ticket_finish)
    Button ticketFinish;
    @BindView(R.id.ticket_recy)
    RecyclerView ticketRecy;
    private TicketPresenter ticketPresenter;
    private MyAdapter myAdapter;
    private int id= 1;
    private LoginBean loginBean;
    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        ticketWaitMoney.setBackgroundResource(R.drawable.btn_gradient);
        ticketWaitMoney.setTextColor(Color.WHITE);
        ticketFinish.setBackgroundResource(R.drawable.myborder);
        ticketFinish.setTextColor(Color.BLACK);
        ticketPresenter = new TicketPresenter(new MyCall());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        ticketRecy.setLayoutManager(manager);
        myAdapter = new MyAdapter(this);
        ticketRecy.setAdapter(myAdapter);
        DaoSession daoSession = DaoMaster.newDevSession(MyTicketActivity.this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        List<UserInfoBean> userInfoBeans = userInfoBeanDao.loadAll();
        userInfoBean = userInfoBeans.get(0);
        Log.d("login2", "success: "+userInfoBean.getSessionId());
        ticketPresenter.reqeust(userInfoBean.getUserId(), userInfoBean.getSessionId(),1,5,id);
    }

    @OnClick({R.id.ticket_wait_money, R.id.ticket_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_wait_money:
                ticketWaitMoney.setBackgroundResource(R.drawable.btn_gradient);
                ticketWaitMoney.setTextColor(Color.WHITE);
                ticketFinish.setBackgroundResource(R.drawable.myborder);
                ticketFinish.setTextColor(Color.BLACK);
                ticketPresenter.reqeust(userInfoBean.getUserId(), userInfoBean.getSessionId(),1,5,id);
                break;
            case R.id.ticket_finish:
                id = 2;
                ticketPresenter.reqeust(userInfoBean.getUserId(), userInfoBean.getSessionId(),1,5,id);
                ticketFinish.setBackgroundResource(R.drawable.btn_gradient);
                ticketFinish.setTextColor(Color.WHITE);
                ticketWaitMoney.setBackgroundResource(R.drawable.myborder);
                ticketWaitMoney.setTextColor(Color.BLACK);
                break;
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
    class MyCall implements DataCall<Result<List<TicketBean>>>{

        @Override
        public void success(Result<List<TicketBean>> result) {
            if(result.getStatus().equals("0000")){
                List<TicketBean> result1 = result.getResult();
                myAdapter.addList(result1);
                myAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
