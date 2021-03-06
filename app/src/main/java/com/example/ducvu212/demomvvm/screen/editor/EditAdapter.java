package com.example.ducvu212.demomvvm.screen.editor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.ducvu212.demomvvm.screen.edit.EditFragment;
import com.example.ducvu212.demomvvm.screen.filtter.FilterFragment;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class EditAdapter extends FragmentPagerAdapter {

    public EditAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FilterFragment.newInstance();
                break;
            case 1:
                fragment = new EditFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Fillter";
                break;
            case 1:
                title = "Edit";
                break;
        }

        return title;
    }
}
