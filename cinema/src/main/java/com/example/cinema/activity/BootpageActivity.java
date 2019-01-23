package com.example.cinema.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.cinema.R;

import java.util.ArrayList;

import me.jessyan.autosize.internal.CustomAdapt;

public class BootpageActivity extends AppCompatActivity implements CustomAdapt {

    private ViewPager bootpageviewpage;
    private int currentItem = 0;
    private int flaggingWidth;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootpage);

        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;

        bootpageviewpage = findViewById(R.id.bootpageviewpage);

//        int a[] = {R.drawable.tuceng1,
//                    R.drawable.tuceng2,
//                        R.drawable.tuceng3,
//                            R.drawable.tuceng4};

        final ArrayList<View> bootpages = new ArrayList<>();
        View one = View.inflate(this, R.layout.bootpage_one, null);
        View two = View.inflate(this, R.layout.bootpage_two, null);
        View three = View.inflate(this, R.layout.bootpage_three, null);
        View four = View.inflate(this, R.layout.bootpage_four, null);
        bootpages.add(one);
        bootpages.add(two);
        bootpages.add(three);
        bootpages.add(four);


        //viewpage适配器
        bootpageviewpage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return bootpages.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = bootpages.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        final RadioGroup radioGroup = findViewById(R.id.bootpageradioGroup);
        radioGroup.check(radioGroup.getChildAt(0).getId());
        //页面改变按钮也跟着变化
        bootpageviewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
                currentItem = i;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
//        //按钮切换页面也跟着切换
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Log.e("====================", checkedId + "");
//                bootpageviewpage.setCurrentItem(checkedId - 2131165223);
//            }
//        });

        gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (currentItem == 3) {
                    if ((e1.getRawX() - e2.getRawX()) >= flaggingWidth)
                    {
                        Intent intent = new Intent(BootpageActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(gestureDetector.onTouchEvent(ev))
        {
            ev.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(ev);
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
