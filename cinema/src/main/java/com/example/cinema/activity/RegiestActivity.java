package com.example.cinema.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;
import com.example.cinema.bean.Result;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.EncryptUtil;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.RegiestPresenter;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class RegiestActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.regiest_name)
    EditText regiestName;
    @BindView(R.id.regiest_sex)
    EditText regiestSex;
    @BindView(R.id.regiest_date)
    EditText regiestDate;
    @BindView(R.id.regiest_phone)
    EditText regiestPhone;
    @BindView(R.id.regiest_email)
    EditText regiestEmail;
    @BindView(R.id.regiest_pwd)
    EditText regiestPwd;
    @BindView(R.id.btn_regiest)
    Button btnRegiest;
    @BindView(R.id.regiest_name2)
    ImageView regiestName2;
    @BindView(R.id.regiest_sex2)
    ImageView regiestSex2;
    @BindView(R.id.regiest_date2)
    ImageView regiestDate2;
    @BindView(R.id.regiest_phone2)
    ImageView regiestPhone2;
    @BindView(R.id.regiest_email2)
    ImageView regiestEmail2;
    @BindView(R.id.regiest_pwd2)
    ImageView regiestPwd2;
    private RegiestPresenter regiestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        regiestPresenter = new RegiestPresenter(new MyCall());
        regiestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerView.Builder(RegiestActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        regiestDate.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();
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

    @OnClick(R.id.btn_regiest)
    public void onViewClicked() {
        int i = 0;
        String name = regiestName.getText().toString();
        String phone = regiestPhone.getText().toString();
        String s = regiestPwd.getText().toString();
        String pwd = EncryptUtil.encrypt(s);
        String sex = regiestSex.getText().toString();
        if (sex.equals("男")) {
            i = 1;
        } else {
            i = 2;
        }
        String date = regiestDate.getText().toString();
        String email = regiestEmail.getText().toString();
        regiestPresenter.reqeust(name, phone, pwd, pwd, i, date, "123456", "小米", " 5.0", "android", email);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    class MyCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            Toast.makeText(RegiestActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            if(result.getStatus().equals("0000")){
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
