package com.creation.where.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creation.where.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbw on 2017/5/9.
 */
public class TogetherFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ViewPager mPaper;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private TextView tv_first,tv_second,tv_last;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fx_fragment_together, container, false);
        initLayout();
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mPaper.setAdapter(mAdapter);
        mPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        return view;
    }

    /**
     * 初始化控件
     */
    public void initLayout(){
        tv_first=(TextView)view.findViewById(R.id.tvTopTabFirst);
        tv_second=(TextView)view.findViewById(R.id.tvTopTabSecond);
        tv_last=(TextView)view.findViewById(R.id.tvTopTabLast);

        mPaper = (ViewPager)view.findViewById(R.id.tab_together);

        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_last.setOnClickListener(this);
        tv_first.setSelected(true);

        TgActivitiesFragment f1 = new TgActivitiesFragment();
        TgJoinFragment f2 = new TgJoinFragment();
        TgMineFragment f3 = new TgMineFragment();

        mFragments.add(f1);
        mFragments.add(f2);
        mFragments.add(f3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTopTabFirst:
                mPaper.setCurrentItem(0);
                tv_first.setSelected(true);
                tv_second.setSelected(false);
                tv_last.setSelected(false);
                break;
            case R.id.tvTopTabSecond:
                mPaper.setCurrentItem(1);
                tv_first.setSelected(false);
                tv_second.setSelected(true);
                tv_last.setSelected(false);
                break;
            case R.id.tvTopTabLast:
                mPaper.setCurrentItem(2);
                tv_first.setSelected(false);
                tv_second.setSelected(false);
                tv_last.setSelected(true);
                break;
            default:
                break;
        }
    }

}
