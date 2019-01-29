package com.bw.movie.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bw.movie.R;
import com.example.cinema.activity.CinemaDetalisActivity;
import com.example.cinema.activity.MyTicketActivity;
import com.example.cinema.wxapi.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	private PopupWindow window_ok;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();
		}
		if(resp.errCode==0){
			View inflate = View.inflate(this, R.layout.popu_pay_layout, null);
			window_ok = new PopupWindow(inflate,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
			window_ok.setFocusable(true);
			window_ok.setTouchable(true);
			window_ok.setOutsideTouchable(true);
			window_ok.setBackgroundDrawable(new BitmapDrawable());
			window_ok.showAtLocation(null,Gravity.NO_GRAVITY,0,0);
			Button popu_pay_ok_back= inflate.findViewById(R.id.popu_pay_ok_back);
			Button popu_pay_ok_finish= inflate.findViewById(R.id.popu_pay_ok_finish);
			popu_pay_ok_back.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					window_ok.dismiss();
					Intent intent = new Intent(WXPayEntryActivity.this,CinemaDetalisActivity.class);
					startActivity(intent);
					finish();
				}
			});
			popu_pay_ok_finish.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					window_ok.dismiss();
					Intent intent = new Intent(WXPayEntryActivity.this,MyTicketActivity.class);
					intent.putExtra("code","2");
					startActivity(intent);
					finish();
				}
			});

		}else if(resp.errCode==-1){

		}else if(resp.errCode==-2){
			Intent intent = new Intent(WXPayEntryActivity.this,MyTicketActivity.class);
			intent.putExtra("code","1");
			startActivity(intent);
			finish();
		}
	}
}