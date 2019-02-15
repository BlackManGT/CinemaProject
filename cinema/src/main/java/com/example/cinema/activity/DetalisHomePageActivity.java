package com.example.cinema.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.DaoMaster;
import com.bw.movie.DaoSession;
import com.bw.movie.R;
import com.bw.movie.UserInfoBeanDao;
import com.example.cinema.adapter.FilmReviewAdapter;
import com.example.cinema.adapter.NoticeAdapter;
import com.example.cinema.adapter.StillsAdapter;
import com.example.cinema.bean.FilmReviewBean;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.AddFilmCommentPresenter;
import com.example.cinema.presenter.CommentGreatPresenter;
import com.example.cinema.presenter.FilmReviewPresenter;
import com.example.cinema.presenter.IDMoiveDetalisonePresenter;
import com.example.cinema.presenter.IDMoiveDetalisoneTwoPresenter;
import com.example.cinema.presenter.IsFollowPresenter;
import com.example.cinema.presenter.NoFilmFollowPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;
import me.jessyan.autosize.internal.CustomAdapt;

public class DetalisHomePageActivity extends AppCompatActivity implements CustomAdapt,View.OnClickListener {

    private IDMoiveDetalisTwo idMoiveDetalisTwo;
    private TextView detalishomepagename;
    private SimpleDraweeView detalishomepagesdvtwo;
    private IDMoiveDetalisoneTwoPresenter idMoiveDetalisoneTwoPresenter;
    private int id;
    private SimpleDraweeView detalishomepagesdvone;
    private View detalis;
    private SimpleDraweeView dialog_detalis_sdvone;
    private TextView dialog_detalis_leixing;
    private TextView dialog_detalis_daoyan;
    private TextView dialog_detalis_shichang;
    private TextView dialog_detalis_chandi;
    private SimpleDraweeView dialog_detalis_sdvtwo;
    private TextView plot;
    private Dialog bottomDialog;
    private View notice;
    private View stills;
    private StillsAdapter stillsAdapter;
    private RecyclerView notice_recycleview;
    private NoticeAdapter noticeAdapter;
    private RecyclerView stills_recycleview;
    private FilmReviewPresenter filmReviewPresenter;
    private View filmreview;
    private FilmReviewAdapter filmReviewAdapter;
    private Button detalisbuttonone;
    private Button detalisbuttontwo;
    private Button detalisbuttonthree;
    private Button detalisbuttonfour;
    private IDMoiveDetalisOne idMoiveDetalisOne;
    private CheckBox detalis_home_page_follow;
    private IsFollowPresenter isFollowPresenter;
    private int userId;
    private String sessionId;
    private List<UserInfoBean> userInfoBeans;
    private Button commentaddbutton;
    private View addcomments;
    private EditText addcomment;
    private AddFilmCommentPresenter addFilmCommentPresenter;
    private Button send;
    private Dialog bottomDialogtwo;
    private NoFilmFollowPresenter noFilmFollowPresenter;
    private CommentGreatPresenter commentGreatPresenter;
    private IDMoiveDetalisonePresenter idMoiveDetalisonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis_home_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserInfoBeanDao.TABLENAME);
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        userInfoBeans = userInfoBeanDao.loadAll();



        //得到电影id
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        idMoiveDetalisonePresenter = new IDMoiveDetalisonePresenter(new IDMoiveDetalisOneCall());
        idMoiveDetalisonePresenter.reqeust(userId,sessionId, id);
        //按钮
        detalisbuttonone = findViewById(R.id.detalisbuttonone);
        detalisbuttontwo = findViewById(R.id.detalisbuttontwo);
        detalisbuttonthree = findViewById(R.id.detalisbuttonthree);
        detalisbuttonfour = findViewById(R.id.detalisbuttonfour);
        detalisbuttonone.setOnClickListener(this);
        detalisbuttontwo.setOnClickListener(this);
        detalisbuttonthree.setOnClickListener(this);
        detalisbuttonfour.setOnClickListener(this);

        //关注
        userId = Integer.parseInt(userInfoBeans.get(0).getUserId());
        sessionId = userInfoBeans.get(0).getSessionId();
        isFollowPresenter = new IsFollowPresenter(new isFollowCall());
        detalis_home_page_follow = findViewById(R.id.detalis_home_page_follow);
        //取消关注
        noFilmFollowPresenter = new NoFilmFollowPresenter(new NoFollowCall());
        //关注影片按钮
        detalis_home_page_follow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)//关注
                {
                    if(userInfoBeans.size() != 0)
                    {

                        Log.e("=========userId========", userId +"");
                        Log.e("======sessionId======",sessionId+"");
                        isFollowPresenter.reqeust(userId, sessionId,id);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetalisHomePageActivity.this);
                        builder.setMessage("请先登录");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myMessagesintent = new Intent(DetalisHomePageActivity.this, LoginActivity.class);
                                startActivity(myMessagesintent);
                            }
                        });
                        builder.show();
                    }
                }
                else//取消关注
                {
                    noFilmFollowPresenter.reqeust(userId, sessionId,id);
                }

            }
        });



        //购票按钮
        Button purchase = findViewById(R.id.purchase);
        purchase.setOnClickListener(this);
        bottomDialog = new Dialog(DetalisHomePageActivity.this, R.style.BottomDialog);

        idMoiveDetalisoneTwoPresenter = new IDMoiveDetalisoneTwoPresenter(new Dialog_DetalisCall());
        idMoiveDetalisoneTwoPresenter.reqeust(userId,sessionId, id);
        //影评
        filmReviewPresenter = new FilmReviewPresenter(new FilmReviewCall());
        detalishomepagesdvone = findViewById(R.id.detalishomepagesdvone);
        Button detalishomepagebutton = findViewById(R.id.detalishomepagebutton);
        detalishomepagename = findViewById(R.id.detalishomepagename);
        detalishomepagesdvtwo = findViewById(R.id.detalishomepagesdvtwo);
        detalishomepagebutton.setOnClickListener(this);

        //点击电影详情/预告/剧照/影评
        detalis = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_detalis, null);
        dialog_detalis_sdvone = detalis.findViewById(R.id.dialog_detalis_sdvone);
        dialog_detalis_leixing = detalis.findViewById(R.id.dialog_detalis_leixing);
        dialog_detalis_daoyan = detalis.findViewById(R.id.dialog_detalis_daoyan);
        dialog_detalis_shichang = detalis.findViewById(R.id.dialog_detalis_shichang);
        dialog_detalis_chandi = detalis.findViewById(R.id.dialog_detalis_chandi);
        dialog_detalis_sdvtwo = detalis.findViewById(R.id.dialog_detalis_sdvtwo);
        dialog_detalis_sdvtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        plot = detalis.findViewById(R.id.plot);
        
        //预告
        notice = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_notice, null);
        notice.findViewById(R.id.notice_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        notice_recycleview = notice.findViewById(R.id.notice_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notice_recycleview.setLayoutManager(linearLayoutManager);
        noticeAdapter = new NoticeAdapter(this);
        notice_recycleview.setAdapter(noticeAdapter);

        //剧照
        stills = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_stills, null);
        stills.findViewById(R.id.stills_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        stills_recycleview = stills.findViewById(R.id.stills_recycleview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        stills_recycleview.setLayoutManager(staggeredGridLayoutManager);
        stillsAdapter = new StillsAdapter(this);
        stills_recycleview.setAdapter(stillsAdapter);

        //影评
        filmreview = View.inflate(DetalisHomePageActivity.this, R.layout.dialog_filmreview, null);
        RecyclerView filmreview_recycleview = filmreview.findViewById(R.id.filmreview_recycleview);
        LinearLayoutManager linearLayoutManagerfilmreview = new LinearLayoutManager(this);
        filmreview_recycleview.setLayoutManager(linearLayoutManagerfilmreview);
        filmReviewAdapter = new FilmReviewAdapter(this);
        filmreview_recycleview.setAdapter(filmReviewAdapter);
        filmreview.findViewById(R.id.filmreview_sdv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        commentGreatPresenter = new CommentGreatPresenter(new CommentGreat());
        filmReviewAdapter.setGreatOnClick(new FilmReviewAdapter.GreatOnClick() {
            @Override
            public void setonClick(int commentid) {
//                Log.d("abc", "setonClick: "+userInfoBeans.get(0).getUserId()+
//                        userInfoBeans.get(0).getSessionId()+commentid);
                commentGreatPresenter.reqeust(userInfoBeans.get(0).getUserId(),
                        userInfoBeans.get(0).getSessionId(),commentid);
            }
        });

        //添加评论按钮
        commentaddbutton = filmreview.findViewById(R.id.commentaddbutton);
        commentaddbutton.setOnClickListener(this);

        //评论
        addcomments = View.inflate(DetalisHomePageActivity.this, R.layout.filmreview_addcomment, null);
        //评论内容
        addcomment = addcomments.findViewById(R.id.addcomment);
        //点击发送
        send = addcomments.findViewById(R.id.send);
        send.setOnClickListener(this);
        addFilmCommentPresenter = new AddFilmCommentPresenter(new AddFilmCommentCall());

        filmReviewPresenter.reqeust(userId,sessionId,id,1,10);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.detalishomepagebutton:
                finish();
                break;
            case R.id.detalisbuttonone:
                bottomDialog.setContentView(detalis);
                ViewGroup.LayoutParams layoutParamsDetalis = detalis.getLayoutParams();
                layoutParamsDetalis.width = getResources().getDisplayMetrics().widthPixels;
                detalis.setLayoutParams(layoutParamsDetalis);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();

                break;
            case R.id.detalisbuttontwo:
                bottomDialog.setContentView(notice);
                ViewGroup.LayoutParams layoutParamsnotice= notice.getLayoutParams();
                layoutParamsnotice.width = getResources().getDisplayMetrics().widthPixels;
                notice.setLayoutParams(layoutParamsnotice);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();

                break;
            case R.id.detalisbuttonthree:
                bottomDialog.setContentView(stills);
                ViewGroup.LayoutParams layoutParamsStills = stills.getLayoutParams();
                layoutParamsStills.width = getResources().getDisplayMetrics().widthPixels;
                stills.setLayoutParams(layoutParamsStills);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                break;
            case R.id.detalisbuttonfour://影评
                bottomDialog.setContentView(filmreview);
                ViewGroup.LayoutParams layoutParamsthreefilmreview = filmreview.getLayoutParams();
                layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                filmreview.setLayoutParams(layoutParamsthreefilmreview);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.setCanceledOnTouchOutside(true);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                break;
            case R.id.purchase:
                Intent intent = new Intent(DetalisHomePageActivity.this,PurchaseActivity.class);
                intent.putExtra("id",id+"");
                intent.putExtra("name",idMoiveDetalisOne.getName());
                startActivity(intent);
                break;
            case R.id.commentaddbutton://添加评论
                if(userInfoBeans.size() != 0)
                {
                    bottomDialogtwo = new Dialog(DetalisHomePageActivity.this, R.style.BottomDialog);
                    bottomDialogtwo.setContentView(addcomments);
                    ViewGroup.LayoutParams layoutParamsaddcomment = addcomments.getLayoutParams();
                    layoutParamsaddcomment.width = getResources().getDisplayMetrics().widthPixels;
                    addcomments.setLayoutParams(layoutParamsaddcomment);
                    bottomDialogtwo.getWindow().setGravity(Gravity.BOTTOM);
                    bottomDialogtwo.setCanceledOnTouchOutside(true);
                    bottomDialogtwo.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                    bottomDialogtwo.show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalisHomePageActivity.this);
                    builder.setMessage("请先登录");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myMessagesintent = new Intent(DetalisHomePageActivity.this, LoginActivity.class);
                            startActivity(myMessagesintent);
                        }
                    });
                    builder.show();
                }
                break;
                case R.id.send://发送评论
                    int userIdOne = Integer.parseInt(userInfoBeans.get(0).getUserId());
                    String sessionIdOne = userInfoBeans.get(0).getSessionId();
                    String s = addcomment.getText().toString();
                    addFilmCommentPresenter.reqeust(userIdOne,sessionIdOne,id,s);
                    bottomDialogtwo.dismiss();
                    break;
        }
    }
    class CommentGreat implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
//                commentGreatPresenter.reqeust(userInfoBeans.get(0).getUserId(),
//                        userInfoBeans.get(0).getSessionId(),id);
                filmReviewAdapter.clearList();
                filmReviewPresenter.reqeust(0,"",id,1,10);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class IDMoiveDetalisOneCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                 idMoiveDetalisOne = (IDMoiveDetalisOne) result.getResult();
                 detalishomepagename.setText(idMoiveDetalisOne.getName());
                 detalishomepagesdvtwo.setImageURI(Uri.parse(idMoiveDetalisOne.getImageUrl()));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //详情//预告/剧照
    class Dialog_DetalisCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                idMoiveDetalisTwo = (IDMoiveDetalisTwo) result.getResult();

                if(idMoiveDetalisTwo.getFollowMovie() == 1)
                {
                    detalis_home_page_follow.setBackgroundResource(R.drawable.com_icon_collection_selectet);
                }
                else
                {
                    detalis_home_page_follow.setBackgroundResource(R.drawable.com_icon_collection_default);
                }

                List<IDMoiveDetalisTwo.ShortFilmListBean> shortFilmList = idMoiveDetalisTwo.getShortFilmList();
                List<String> posterList = idMoiveDetalisTwo.getPosterList();
                Log.e("============",shortFilmList.size()+"");
                //详情
                dialog_detalis_sdvone.setImageURI(Uri.parse(idMoiveDetalisTwo.getImageUrl()));
                dialog_detalis_leixing.setText("类型："+ idMoiveDetalisTwo.getMovieTypes());
                dialog_detalis_daoyan.setText("导演："+ idMoiveDetalisTwo.getDirector());
                dialog_detalis_shichang.setText("时长："+ idMoiveDetalisTwo.getDuration());
                dialog_detalis_chandi.setText("产地："+ idMoiveDetalisTwo.getPlaceOrigin());
                plot.setText(idMoiveDetalisTwo.getSummary());

                //预告片
                noticeAdapter.addItem(shortFilmList);
                //剧照
                stillsAdapter.addItem(posterList);


                noticeAdapter.notifyDataSetChanged();
                stillsAdapter.notifyDataSetChanged();


            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //影评
    class FilmReviewCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                List<FilmReviewBean> filmReviewBeans = (List<FilmReviewBean>) result.getResult();
                filmReviewAdapter.addItem(filmReviewBeans);
                filmReviewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //关注
    class isFollowCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                detalis_home_page_follow.setBackgroundResource(R.drawable.com_icon_collection_selectet);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    //取消关注
    class NoFollowCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                detalis_home_page_follow.setBackgroundResource(R.drawable.com_icon_collection_default);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //添加评论
    class AddFilmCommentCall implements DataCall<Result>
    {

        @Override
        public void success(Result result) {
            if(result.getStatus().equals("0000"))
            {
                Toast.makeText(DetalisHomePageActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

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

    @Override
    protected void onResume() {
        super.onResume();
        idMoiveDetalisoneTwoPresenter.reqeust(userId,sessionId, id);
        if(filmreview == null){
            noticeAdapter.clearList();

            noticeAdapter.notifyDataSetChanged();

        }
    }

}
