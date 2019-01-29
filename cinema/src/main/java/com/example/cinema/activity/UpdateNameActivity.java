package com.example.cinema.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.UpdateNamePresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class UpdateNameActivity extends AppCompatActivity implements CustomAdapt {

    private EditText update_name;
    private UpdateNamePresenter updateNamePresenter;
    private List<UserInfoBean> userInfoBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);

        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        //P层
        updateNamePresenter = new UpdateNamePresenter(new UpdateNameCall());

        update_name = findViewById(R.id.update_name);
        Button update_sure = findViewById(R.id.update_sure);
        //确认修改按钮
        update_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
                String sessionId = userInfoBeans.get(0).getSessionId();
                int sex = userInfoBeans.get(0).getSex();
                String name = update_name.getText().toString();
                updateNamePresenter.reqeust(userId,sessionId,name,sex,"493000539@qq.com");
            }
        });
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //成功修改
    class UpdateNameCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(UpdateNameActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
