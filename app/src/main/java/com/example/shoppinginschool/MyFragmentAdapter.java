package com.example.shoppinginschool;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList2;
            public MyFragmentAdapter(FragmentManager fm, List<Fragment>  fragmentList2){
                super(fm);
                this.fragmentList2 = fragmentList2;
            }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList2.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList2.size();
    }
}
