package com.ottego.iplHub;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ottego.iplHub.fragments.IplMatch;
import com.ottego.iplHub.fragments.News;
import com.ottego.iplHub.fragments.TodayMatchFragment;

public class ViewPageAdapter extends FragmentPagerAdapter {

    Context mContext;
    int TotalTabs;

    public ViewPageAdapter(Context context, FragmentManager fragmentManager, int totalTabs) {
        super(fragmentManager);
        mContext = context;
        TotalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("position", position + "");
        switch (position) {
            case 0:
                return new TodayMatchFragment();
            case 1:
                return new IplMatch();
            case 2:
                return new News();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }

}
