package com.example.cinema.homefragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.activity.FeedBackActivity;
import com.example.cinema.activity.MyFollowActivity;
import com.example.cinema.activity.HomePageActivity;
import com.example.cinema.activity.LoginActivity;
import com.example.cinema.activity.MyMessagesActivity;
import com.example.cinema.activity.MyTicketActivity;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.SigninPresenter;
import com.example.cinema.view.CacheManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CustomAdapt;


public class MyFragment extends Fragment implements CustomAdapt {
    @BindView(R.id.myhead)
    SimpleDraweeView myhead;
    @BindView(R.id.myname)
    TextView myname;
    Unbinder unbinder;
    @BindView(R.id.mymessages)
    LinearLayout mymessages;
    @BindView(R.id.my_guanzhu)
    LinearLayout myGuanzhu;
    @BindView(R.id.my_ticket)
    LinearLayout myTicket;
    @BindView(R.id.my_yijian)
    LinearLayout myYijian;
    @BindView(R.id.my_version)
    LinearLayout myVersion;
    @BindView(R.id.my_back)
    LinearLayout myBack;
    @BindView(R.id.my_linear)
    LinearLayout myLinear;
    @BindView(R.id.signin)
    Button signin;

    private List<LoginBean> student;
    private UserInfoBean userInfoBean;
    private List<UserInfoBean> userInfoBeans;
    private UserInfoBeanDao userInfoBeanDao;
    private SigninPresenter signinPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        AutoSizeConfig.getInstance().setCustomFragment(true);

        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

        //签到P层
        signinPresenter = new SigninPresenter(new SigninCall());

        //我的信息
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    //去登陆
    private void judge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("请先登录");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                startActivity(myMessagesintent);
            }
        });
        builder.show();
    }

    private void init() {
        DaoSession daoSession = DaoMaster.newDevSession(getContext(), UserInfoBeanDao.TABLENAME);
        userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();

            if (userInfoBeans.size() != 0) {
                userInfoBean = userInfoBeans.get(0);
                myhead.setImageURI(userInfoBean.getHeadPic());
                myname.setText(userInfoBean.getNickName());
            } else {
            myLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
            });
            myname.setText("请登录");
            myhead.setImageResource(R.drawable.myuserhead);

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


    @OnClick({R.id.signin,R.id.mymessages, R.id.my_guanzhu, R.id.my_ticket, R.id.my_yijian, R.id.my_version, R.id.my_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signin:
                if (userInfoBeans.size() == 0) {
                    judge();
                } else {
                    signin.setBackgroundResource(R.drawable.my_sign_in);
                    signin.setText("已签到");
                    signin.setTextColor(Color.WHITE);
                    int userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
                    String sessionId = userInfoBeans.get(0).getSessionId();
                    signinPresenter.reqeust(userId,sessionId);
                }
                break;
            case R.id.mymessages:
                if (userInfoBeans.size() == 0) {
                    judge();
                } else {
                    Intent intent = new Intent(getActivity(), MyMessagesActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_guanzhu:
                if (userInfoBeans.size() == 0) {
                    judge();
                } else {
                    Intent intent = new Intent(getActivity(), MyFollowActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_ticket:
                if (userInfoBeans.size() == 0) {
                    judge();
                } else {
                    Intent intent = new Intent(getActivity(), MyTicketActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_yijian:
                if (userInfoBeans.size() == 0) {
                    judge();
                } else {
                    Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_version:
                break;
            case R.id.my_back:
                if (userInfoBeans.size() == 0) {
                    judge();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("是否确认退出");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userInfoBeanDao.deleteAll();
                            init();

                        }
                    });
                    builder.show();
                }
                break;
        }
    }

    //成功签到
    class SigninCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(getActivity(), ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        MobclickAgent.onResume(getContext());
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }



}
