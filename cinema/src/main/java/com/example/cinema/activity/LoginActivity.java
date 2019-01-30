package com.example.cinema.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.EncryptUtil;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class LoginActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.login_phone)
    ImageView loginPhone;
    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_lock)
    ImageView loginLock;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_eyes)
    ImageView loginEyes;
    @BindView(R.id.login_remember)
    CheckBox loginRemember;
    @BindView(R.id.login_auto)
    CheckBox loginAuto;
    @BindView(R.id.login_regiest)
    TextView loginRegiest;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_wx)
    ImageView loginWx;
    private LoginPresenter loginPresenter;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(new MyCall());
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516",true);
        //将应用的appid注册到微信
        api.registerApp("wxb3852e6a6b7d9516");

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    @OnClick({R.id.login_eyes, R.id.login_regiest, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_eyes:
                if (loginPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.login_regiest:
                Intent intent = new Intent(LoginActivity.this, RegiestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String name = loginName.getText().toString();
                String s = loginPwd.getText().toString();
                String pwd = EncryptUtil.encrypt(s);
                Log.d("pwd", "onViewClicked: "+pwd);
                loginPresenter.reqeust(name, pwd);
                break;
        }
    }

    @OnClick(R.id.login_wx)
    public void onViewClicked() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_微信登录"; // 自行填写
        api.sendReq(req);
    }

    class MyCall implements DataCall<Result<LoginBean>> {

        @Override
        public void success(Result<LoginBean> result) {
            Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                LoginBean result1 = result.getResult();
                int userId = result1.getUserId();
                String sessionId = result1.getSessionId();
                Log.d("login", "success: " + sessionId);
                UserInfoBean userInfo = result1.getUserInfo();
                userInfo.setSessionId(sessionId);
                userInfo.setUserId(userId + "");
                DaoSession daoSession = DaoMaster.newDevSession(LoginActivity.this, UserInfoBeanDao.TABLENAME);
                UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
                userInfoBeanDao.insertOrReplace(userInfo);
                finish();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
