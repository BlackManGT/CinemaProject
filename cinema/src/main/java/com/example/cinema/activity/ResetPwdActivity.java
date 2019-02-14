package com.example.cinema.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.example.cinema.core.EncryptUtil;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.ResetPwdPresenter;

import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class ResetPwdActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private Button resetpwd_return;
    private ResetPwdPresenter resetPwdPresenter;
    private EditText resetpwd_oldpwd;
    private EditText resetpwd_newpwdone;
    private EditText resetpwd_newpwdtwo;
    private List<UserInfoBean> userInfoBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        Button resetpwd_return = findViewById(R.id.resetpwd_return);
        resetpwd_return.setOnClickListener(this);

        resetPwdPresenter = new ResetPwdPresenter(new ResetCall());
        resetpwd_oldpwd = findViewById(R.id.resetpwd_oldpwd);
        resetpwd_newpwdone = findViewById(R.id.resetpwd_newpwdone);
        resetpwd_newpwdtwo = findViewById(R.id.resetpwd_newpwdtwo);


    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.resetpwd_return:
                int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
                String sessionId = userInfoBeans.get(0).getSessionId();
                String oldpwd = resetpwd_oldpwd.getText().toString();
                String newpwdone = resetpwd_newpwdone.getText().toString();
                String newpwdtwo = resetpwd_newpwdtwo.getText().toString();
                String oldencrypt = EncryptUtil.encrypt(oldpwd);
                String newencryptone = EncryptUtil.encrypt(newpwdone);
                String newencrypttwo = EncryptUtil.encrypt(newpwdtwo);
                resetPwdPresenter.reqeust(userId,sessionId,oldencrypt,newencryptone,newencrypttwo);
                break;
        }
    }

    class ResetCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(ResetPwdActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
