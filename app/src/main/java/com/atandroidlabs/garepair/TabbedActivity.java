package com.atandroidlabs.garepair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atandroidlabs.garepair.TabbedFragment.Service1Fragment;
import com.atandroidlabs.garepair.TabbedFragment.Service2Fragment;
import com.atandroidlabs.garepair.TabbedFragment.Service3Fragment;
import com.atandroidlabs.garepair.TabbedFragment.Service4Fragment;
import com.atandroidlabs.garepair.TabbedFragment.Service5Fragment;
import com.atandroidlabs.garepair.TabbedFragment.Service6Fragment;
import com.google.android.material.tabs.TabLayout;

public class TabbedActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private final int NumberOfServices = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        viewPager = findViewById(R.id.choose_service_viewpager);
        tabLayout = findViewById(R.id.choose_service_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        MyServicePagerAdapter adapter = new MyServicePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    class MyServicePagerAdapter extends FragmentStatePagerAdapter {

        public MyServicePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Service1";
            }  else if (position == 1) {
                return "Service2";
            } else if (position == 2) {
                return "Service3";
            } else if (position == 3) {
                return "Service4";
            } else if (position == 4) {
                return "Service5";
            } else {
                return "Service6";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Service1Fragment();
                case 1:
                    return new Service2Fragment();
                case 2:
                    return new Service3Fragment();
                case 3:
                    return new Service4Fragment();
                case 4:
                    return new Service5Fragment();
                case 5:
                    return new Service6Fragment();
                default:
                    return new Service1Fragment();

            }
        }

        @Override
        public int getCount() {
            return NumberOfServices;
        }
    }
}