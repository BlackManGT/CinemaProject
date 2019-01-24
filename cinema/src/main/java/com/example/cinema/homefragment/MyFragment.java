package com.example.cinema.homefragment;

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
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.sqlite.DBManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.myhead)
    SimpleDraweeView myhead;
    @BindView(R.id.myname)
    TextView myname;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //我的信息
        LinearLayout mymessages = view.findViewById(R.id.mymessages);
        mymessages.setOnClickListener(this);
        unbinder = ButterKnife.bind(this, view);
        try {
            DBManager dbManager = new DBManager(getContext());
            List<UserInfoBean> student = dbManager.getStudent();
            if(student.size()==0){
                myname.setText("请登录");
                myname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }else{
                String nickName = student.get(0).getNickName();
                myname.setText(nickName);
                myhead.setImageURI( student.get(0).getHeadPic());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mymessages:
                Intent myMessagesintent = new Intent(getActivity(), MyMessagesActivity.class);
                startActivity(myMessagesintent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
