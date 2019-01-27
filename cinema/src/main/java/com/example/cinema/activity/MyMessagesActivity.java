package com.example.cinema.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.UserInfoBean;
import com.example.cinema.core.DataCall;
import com.example.cinema.core.exception.ApiException;
import com.example.cinema.presenter.UploadHeadPicPresenter;
import com.example.cinema.view.Constant;
import com.example.cinema.view.GetRealPath;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyMessagesActivity extends AppCompatActivity implements CustomAdapt {
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
    private int SELECT_PICTURE = 1; // 从图库中选择图片
    private int SELECT_CAMER = 0; // 用相机拍摄照片
    private File file;
    private UploadHeadPicPresenter uploadHeadPicPresenter;
    private LoginBean loginBean;
    private Bitmap bmp;
    private List<LoginBean> student;
    private LinearLayout resetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        ButterKnife.bind(this);

        resetpwd = findViewById(R.id.resetpwd);
        resetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyMessagesActivity.this, ResetPwdActivity.class);
                startActivity(intent);
            }
        });

        uploadHeadPicPresenter = new UploadHeadPicPresenter(new MyCall());
    }



    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }


    @OnClick({R.id.mymessagesheard,R.id.mymessagesreturn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mymessagesheard:
                CharSequence[] items = {"相机","相册"};
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("请选择图片来源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==SELECT_CAMER){//相册
                                    if(ContextCompat.checkSelfPermission(MyMessagesActivity.this,
                                            Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){//申请权限
                                        ActivityCompat.requestPermissions(MyMessagesActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                                        return;
                                    }
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    startActivityForResult(intent,SELECT_CAMER);
                                }else {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                                    intent.setType("image/*");
                                    startActivityForResult(intent,SELECT_PICTURE);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.mymessagesreturn:
                finish();
                break;
        }
    }
    class MyCall implements DataCall<Result>{

        @Override
        public void success(Result result) {
            String headPath = result.getHeadPath();
            mymessagesheard.setImageURI(headPath);
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){//如果没有图片返回就return
            Toast.makeText(MyMessagesActivity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if(requestCode==0){
            Bitmap bitmap = data.getParcelableExtra("data");
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                    null, null));
            mymessagesheard.setImageURI(uri);
            String realPathFromUri = GetRealPath.getRealPathFromUri(MyMessagesActivity.this, uri);
            file = new File(realPathFromUri);
            
            File file = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filepath = cursor.getString(column_index);
            file = new File(filepath);
            // TODO: 2019/1/24 上传头像
            uploadHeadPicPresenter.reqeust(loginBean.getId()+"", loginBean.getSessionId(), file);
            return;
        }
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                if (bmp != null) {
                    bmp.recycle();
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    file = new File(uri.toString());
                    uploadHeadPicPresenter.reqeust(loginBean.getId()+"", loginBean.getSessionId(), file);
                }
        } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        mymessagesheard.setImageURI(uri);
        } else {
        Toast.makeText(MyMessagesActivity.this, "选择图片失败,请重新选择", Toast.LENGTH_SHORT)
        .show();
        }
    }
}
