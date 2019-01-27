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
import com.example.cinema.presenter.FeedBackPresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener,CustomAdapt {

    private EditText feedback_edittext;
    private FeedBackPresenter feedBackPresenter;
    private List<UserInfoBean> userInfoBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        //提交内容
        feedback_edittext = findViewById(R.id.feedback_edittext);
        //提交按钮
        Button feedback_tijiao = findViewById(R.id.feedback_tijiao);
        feedback_tijiao.setOnClickListener(this);
        //返回上级目录
        Button feedback_return = findViewById(R.id.feedback_return);
        feedback_return.setOnClickListener(this);

        feedBackPresenter = new FeedBackPresenter(new FeedBackCall());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.feedback_tijiao://提交
                int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
                String sessionId = userInfoBeans.get(0).getSessionId();
                String s = feedback_edittext.getText().toString();
                feedBackPresenter.reqeust(userId,sessionId,s);
                break;
            case R.id.feedback_return:
                finish();
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

    //反馈成功
    class  FeedBackCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(FeedBackActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
