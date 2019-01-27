package com.example.cinema.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.bean.CinemaRecy;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.BuyMovieTicketPresenter;
import com.qfdqc.views.seattable.SeatTable;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    private TextView textname;
    private RelativeLayout rl_bot;
    private TextView txt_choose_price;
    private double price = 0.0;
    private CinemaRecy cinemaname;
    private ImageView img_confirm;
    private ImageView img_abandon;
    private BuyMovieTicketPresenter buyMovieTicketPresenter;
    private UserInfoBean userInfoBean;
    private int count=0;
    private List<UserInfoBean> userInfoBeans;
    private String p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        SeatTable seat_view = (SeatTable) findViewById(R.id.seat_view);
        txt_choose_price = findViewById(R.id.txt_choose_price);
        img_confirm = findViewById(R.id.img_confirm);
        img_abandon = findViewById(R.id.img_abandon);
        textname = findViewById(R.id.txt_session);
        rl_bot = findViewById(R.id.rl_bot);
        Intent intent = getIntent();
        cinemaname = (CinemaRecy) intent.getSerializableExtra("cinemaname");
        String name = intent.getStringExtra("name");
        Log.d("aaa", "onCreate: "+ cinemaname.toString());
        seat_view.setScreenName(cinemaname.getScreeningHall());//设置屏幕名称
        seat_view.setMaxSelected(2);//设置最多选中
        textname.setText(name);
        buyMovieTicketPresenter = new BuyMovieTicketPresenter(new MyBuy());
        seat_view.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                rl_bot.setVisibility(View.VISIBLE);
                price+=cinemaname.getPrice();
                Log.d("a", "unCheck: "+price);
                txt_choose_price.setText(price+"");
                count++;
            }

            @Override
            public void unCheck(int row, int column) {
                price-=cinemaname.getPrice();
                Log.d("a", "unCheck: "+price);
                txt_choose_price.setText(price+"");
                if(price == 0.0){
                    rl_bot.setVisibility(View.GONE);
                }
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seat_view.setData(10,15);
        DaoSession daoSession = DaoMaster.newDevSession(ChooseActivity.this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();
        img_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInfoBeans==null){
                    Toast.makeText(ChooseActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    userInfoBean = userInfoBeans.get(0);
                    final String userId = userInfoBean.getUserId();
                    final String sessionId = userInfoBean.getSessionId();
                    final String md5 = MD5(userId + cinemaname.getId() + count + "movie");
                    buyMovieTicketPresenter.reqeust(userId,sessionId, cinemaname.getId(),count,md5);
                }

            }
        });
        img_abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_bot.setVisibility(View.GONE);
            }
        });
    }
    class MyBuy implements DataCall<Result>{

        @Override
        public void success(Result result) {
            Toast.makeText(ChooseActivity.this, result.getMessage()+"成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(ChooseActivity.this,e.getMessage()+"失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     *  MD5加密
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}
