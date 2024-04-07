package com.shashank.platform.classroomappui.ui.Books;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shashank.platform.classroomappui.ui.Books.Fragments.Add_Book.AddBookFragment;

public class Book_Adapter extends FragmentPagerAdapter {

    int tabcount;

    public Book_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new AddBookFragment();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
