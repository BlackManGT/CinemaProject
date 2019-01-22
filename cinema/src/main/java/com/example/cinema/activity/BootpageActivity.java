package com.example.cinema.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.cinema.R;

import java.util.ArrayList;

import me.jessyan.autosize.internal.CustomAdapt;

public class BootpageActivity extends AppCompatActivity implements CustomAdapt {

    private ViewPager bootpageviewpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootpage);

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
