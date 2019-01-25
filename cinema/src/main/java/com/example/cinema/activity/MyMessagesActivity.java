package com.example.cinema.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.sqlite.DBManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.example.cinema.MyApplication.getContext;

public class MyMessagesActivity extends AppCompatActivity implements CustomAdapt, View.OnClickListener {

    @BindView(R.id.mymessagesheard)
    SimpleDraweeView mymessagesheard;
    @BindView(R.id.mymessagesname)
    TextView mymessagesname;
    @BindView(R.id.mymessagessex)
    TextView mymessagessex;
    @BindView(R.id.mymessagesdate)
    TextView mymessagesdate;
    @BindView(R.id.mymessagesphone)
    TextView mymessagesphone;
    @BindView(R.id.mymessagesemail)
    TextView mymessagesemail;
    @BindView(R.id.mymessagesreturn)
    SimpleDraweeView mymessagesreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        ButterKnife.bind(this);

        SimpleDraweeView mymessagesreturn = findViewById(R.id.mymessagesreturn);
        mymessagesreturn.setOnClickListener(this);
        try {
            DBManager dbManager = new DBManager(this);
            List<LoginBean> student = dbManager.getStudent();
            LoginBean loginBean = student.get(0);
            UserInfoBean userInfo = loginBean.getUserInfo();

            String nickName = userInfo.getNickName();
            mymessagesname.setText(nickName);
            mymessagesheard.setImageURI( userInfo.getHeadPic());
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date d1=new Date(userInfo.getBirthday());
            String t1=format.format(d1);
            mymessagesdate.setText(t1);
            int sex = userInfo.getSex();
            if(sex==1){
                mymessagessex.setText("男");
            }else {
                mymessagessex.setText("女");
            }
            mymessagesphone.setText(userInfo.getPhone());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //返回上一个页面
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mymessagesreturn:
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


}
