package com.shashank.platform.classroomappui.ui.Books;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shashank.platform.classroomappui.R;


public class Book_Fragment extends AppCompatActivity {
    TabLayout tablout;
    TabItem addbook, details;
    ViewPager viewPager;
    Book_Adapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_book_fragment);

        tablout = findViewById(R.id.tabLayout);
       // details = findViewById(R.id.bookdetails);
        viewPager = findViewById(R.id.viewpager);

        bookAdapter = new Book_Adapter(getSupportFragmentManager(), tablout.getTabCount());
        viewPager.setAdapter(bookAdapter);

        tablout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 1)
                    bookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablout));
    }

}