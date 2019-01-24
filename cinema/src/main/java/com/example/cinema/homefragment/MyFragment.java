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

import com.bw.movie.R;
import com.example.cinema.activity.MyMessagesActivity;


public class MyFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //我的信息
        LinearLayout mymessages = view.findViewById(R.id.mymessages);
        mymessages.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mymessages:
                Intent myMessagesintent = new Intent(getActivity(), MyMessagesActivity.class);
                startActivity(myMessagesintent);
                break;
        }
    }
}
