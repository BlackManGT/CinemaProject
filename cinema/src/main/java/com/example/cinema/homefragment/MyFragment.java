package com.example.cinema.homefragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.DownLoadService;
import com.example.cinema.activity.FeedBackActivity;
import com.example.cinema.activity.MyFollowActivity;
import com.example.cinema.activity.HomePageActivity;
import com.example.cinema.activity.LoginActivity;
import com.example.cinema.activity.MyMessagesActivity;
import com.example.cinema.activity.MyTicketActivity;
import com.example.cinema.activity.SysMsgListActivity;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.SigninPresenter;
import com.example.cinema.presenter.UpdatePresenter;
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
    private UpdatePresenter updatePrensenter;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();
        for (int i = 0; i < userInfoBeans.size(); i++) {
            userId = Integer.parseInt(userInfoBeans.get(i).getUserId());
            sessionId = userInfoBeans.get(i).getSessionId();
        }
        
        //版本更新
        updatePrensenter = new UpdatePresenter(new VersionsCall());

        //签到P层
        signinPresenter = new SigninPresenter(new SigninCall());

        //我的信息
        unbinder = ButterKnife.bind(this, view);
        //点击进入系统通知
        view.findViewById(R.id.my_sysmsgs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SysMsgListActivity.class);
                startActivity(intent);
            }
        });
        init();
        return view;
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
                if(userInfoBeans.size() == 0)
                {
                    Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(myMessagesintent);
                }
                else
                {
                    Intent intent1 = new Intent(getActivity(), MyMessagesActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.my_guanzhu:
                if(userInfoBeans.size() == 0)
                {
                    Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(myMessagesintent);
                }
                else
                {
                    Intent intent2 = new Intent(getActivity(), MyFollowActivity.class);
                    startActivity(intent2);
                }

                break;
            case R.id.my_ticket:
                if(userInfoBeans.size() == 0)
                {
                    Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(myMessagesintent);
                }
                else
                {
                    Intent intent3 = new Intent(getActivity(), MyTicketActivity.class);
                    startActivity(intent3);
                }

                break;
            case R.id.my_yijian:
                if(userInfoBeans.size() == 0)
                {
                    Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(myMessagesintent);
                }
                else
                {
                    Intent intent4 = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(intent4);
                }

                break;
            case R.id.my_version:
                //Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_SHORT).show();
                try {
                    String versionName = getVersionName(getContext());
                    updatePrensenter.reqeust(userId, sessionId, versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.my_back:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("是否确认退出");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            signin.setText("签到");
                            userInfoBeanDao.deleteAll();
                            init();

                        }
                    });
                    builder.show();
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


    /**
     * 获取版本号
     *
     * @throws
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }


    //查询新版本
    class VersionsCall implements DataCall<Result> {

        @Override
        public void success(final Result result) {
            if (result.getFlag() == 2) {
                Toast.makeText(getActivity(), "当前已是最新版本!", Toast.LENGTH_SHORT).show();
            } else if (result.getFlag() == 1) {
                AlertDialog.Builder builer = new AlertDialog.Builder(getContext());
                builer.setTitle("版本升级");
                builer.setMessage("发现新版本");
                //当点确定按钮时从服务器上下载 新的apk 然后安装
                builer.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), DownLoadService.class);
                        intent.putExtra("download_url", result.getDownloadUrl());
                        getActivity().startService(intent);
                    }
                });
                //当点取消按钮时进行登录
                builer.setNegativeButton("稍后下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //LoginMain();
                    }
                });
                AlertDialog dialog = builer.create();
                dialog.show();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

}
