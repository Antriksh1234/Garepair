package com.atandroidlabs.garepair.FirstInteraction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.atandroidlabs.garepair.MainActivity;
import com.atandroidlabs.garepair.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class InteractionActivity extends FragmentActivity {

    private ViewPager viewPager;
    private final int NUMBER_OF_PAGES = 3;
    private SpringDotsIndicator indicator;
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void passToRegister(View view) {
        //At the moment
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);

        viewPager = findViewById(R.id.fragment_viewpager);
        indicator = findViewById(R.id.dots_indicator);
        viewPager.setCurrentItem(0);
        mPagerAdapter adapter = new mPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    class mPagerAdapter extends FragmentStatePagerAdapter {

        public mPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FirstFragment();
            } else if (position == 1) {
                return new SecondFragment();
            } else {
                return new ThirdFragment();
            }
        }

        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }
    }
}