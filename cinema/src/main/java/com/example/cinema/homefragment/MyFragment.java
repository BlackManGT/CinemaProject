package com.example.cinema.homefragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.example.cinema.activity.LoginActivity;
import com.example.cinema.activity.MyMessagesActivity;
import com.example.cinema.activity.MyTicketActivity;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.sqlite.DBManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
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

    private List<LoginBean> student;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //我的信息
        unbinder = ButterKnife.bind(this, view);
        initDt();

        return view;
    }

    private void initDt() {
        try {
            DBManager dbManager = new DBManager(getContext());
            student = dbManager.getStudent();
            if (student.size() == 0) {
                myname.setText("请登录");
                myname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                LoginBean loginBean = student.get(0);
                UserInfoBean userInfo = loginBean.getUserInfo();
                String nickName = userInfo.getNickName();
                myname.setText(nickName);
                myhead.setImageURI(userInfo.getHeadPic());
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    @OnClick({R.id.mymessages, R.id.my_guanzhu, R.id.my_ticket, R.id.my_yijian, R.id.my_version, R.id.my_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mymessages:
                if(student.size()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }else{
                    Intent intent = new Intent(getActivity(), MyMessagesActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_guanzhu:
                if(student.size()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), MyTicketActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }else{
                    Intent intent = new Intent(getActivity(), MyTicketActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_ticket:
                if(student.size()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(getActivity(), MyTicketActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }else{
                    Intent intent = new Intent(getActivity(), MyTicketActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_yijian:
                break;
            case R.id.my_version:
                break;
            case R.id.my_back:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initDt();

    }
}
