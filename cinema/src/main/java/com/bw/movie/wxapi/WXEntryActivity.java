package com.bw.movie.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.activity.HomePageActivity;
import com.example.cinema.activity.LoginActivity;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.WxPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
  private IWXAPI api;
  private WxPresenter wxPresenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 注册API
    api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
    api.handleIntent(getIntent(), this);
  }
 
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    api.handleIntent(intent, this);
  }
 
  @Override
  public void onReq(BaseReq baseReq) {
    finish();
  }
 
  @Override
  public void onResp(BaseResp resp) {
    switch (resp.errCode) {
      case BaseResp.ErrCode.ERR_OK:
        Log.e("flag", "-----code:ok");
        if (resp instanceof SendAuth.Resp) {
          SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;
          String code = sendAuthResp.code;
          getAccessToken(code);
          // 发起登录请求
          Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
          Log.e("flag2", "-----code:" + sendAuthResp.code);
          wxPresenter = new WxPresenter(new MyCall());
          wxPresenter.reqeust(code);
        }

        break;
      case BaseResp.ErrCode.ERR_USER_CANCEL:
        if (resp instanceof SendAuth.Resp) {}
        Log.e("flag", "-----授权取消:");
        Toast.makeText(this, "授权取消:", Toast.LENGTH_SHORT).show();
        finish();
        break;
      case BaseResp.ErrCode.ERR_AUTH_DENIED:
        if (resp instanceof SendAuth.Resp) {}
        Log.e("flag", "-----授权失败:");
        Toast.makeText(this, "授权失败:", Toast.LENGTH_SHORT).show();
        finish();
        break;
      default:
        break;
    }
  }
 
  // 根据授权code  获取AccessToken
  public void getAccessToken(String code){


  }
  class MyCall implements DataCall<Result<LoginBean>>{

    @Override
    public void success(Result<LoginBean> result) {
        if (result.getStatus().equals("0000")){
          Log.d("su", "success: "+"成功");
          LoginBean result1 = result.getResult();
          int userId = result1.getUserId();
          String sessionId = result1.getSessionId();
          UserInfoBean userInfo = result1.getUserInfo();
          userInfo.setSessionId(sessionId);
          userInfo.setUserId(userId + "");
          DaoSession daoSession = DaoMaster.newDevSession(WXEntryActivity.this, UserInfoBeanDao.TABLENAME);
          UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
          userInfoBeanDao.insertOrReplace(userInfo);
          Intent intent = new Intent(WXEntryActivity.this,HomePageActivity.class);
          startActivity(intent);
          finish();
        }
    }

    @Override
    public void fail(ApiException e) {
      Toast.makeText(WXEntryActivity.this, e.getMessage()+"失败", Toast.LENGTH_SHORT).show();
      Log.d("su", "success: "+"失败");
    }
  }
 }
