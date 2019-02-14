package com.example.cinema.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.SysMsgAdapter;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.SysMsgListBean;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.SysMsgListPresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class SysMsgListActivity extends AppCompatActivity implements CustomAdapt {

    private SysMsgAdapter sysMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_msg_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        List<UserInfoBean> userInfoBeans = userInfoBeanDao.loadAll();

        SysMsgListPresenter sysMsgListPresenter = new SysMsgListPresenter(new SysMsgCall());

        RecyclerView sysmsg_recycleview = findViewById(R.id.sysmsg_recycleview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sysmsg_recycleview.setLayoutManager(linearLayoutManager);

        sysMsgAdapter = new SysMsgAdapter(this);
        sysmsg_recycleview.setAdapter(sysMsgAdapter);

        int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        String sessionId = userInfoBeans.get(0).getSessionId();
        sysMsgListPresenter.reqeust(userId,sessionId,1,10);


    }

    class SysMsgCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(SysMsgListActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                List<SysMsgListBean> sysMsgListBeans = (List<SysMsgListBean>) result.getResult();
                sysMsgAdapter.addItem(sysMsgListBeans);
                sysMsgAdapter.notifyDataSetChanged();
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
}
