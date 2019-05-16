package com.codercoral.electricitychart.adapter;

import android.content.Context;


import com.codercoral.electricitychart.video.codercoral.videos.VideoFragment;
import com.codercoral.electricitychart.view.fragment.ChartFragment;
import com.codercoral.electricitychart.view.fragment.FeedbackFragment;
import com.codercoral.electricitychart.view.fragment.NewsFragment;
import com.codercoral.electricitychart.view.fragment.ObjectFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"负载率图表", "电网资讯","休闲视频", "提交反馈"};
    private Context context;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChartFragment.newInstance(position + 1);
            case 1:
                return NewsFragment.newInstance(position+1);
            case 2:
                return VideoFragment.Companion.newInstance(position+1);
            case 3:
                return FeedbackFragment.newInstance(position+1);
            default:
                break;
        }

        return ObjectFragment.newInstance(position + 1);
    }

    //设置title
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
