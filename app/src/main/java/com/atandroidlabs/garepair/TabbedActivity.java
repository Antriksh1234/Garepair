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
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.atandroidlabs.garepair.TabbedFragment.AcServiceAndRepairFragment;
import com.atandroidlabs.garepair.TabbedFragment.BatteryServiceFragment;
import com.atandroidlabs.garepair.TabbedFragment.CleaningServiceFragment;
import com.atandroidlabs.garepair.TabbedFragment.DentingPaintingFragment;
import com.atandroidlabs.garepair.TabbedFragment.LightsFilamentFragment;
import com.atandroidlabs.garepair.TabbedFragment.PeriodicServiceFragment;
import com.atandroidlabs.garepair.TabbedFragment.TyresAndWheelsFragment;
import com.atandroidlabs.garepair.TabbedFragment.WinshieldAndGlassFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TabbedActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private final int NumberOfServices = 8;
    public static List<ServicePojo> selectedList;
    public static String carType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        getSupportActionBar().hide();
        carType=getIntent().getStringExtra("Type");
        viewPager = findViewById(R.id.choose_service_viewpager);
        tabLayout = findViewById(R.id.choose_service_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        selectedList=new ArrayList<>();
        MyServicePagerAdapter adapter = new MyServicePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(8);
    }

    public void goToCart(View view) {
        if (selectedList.size() == 0) {
            Toast.makeText(this, "Please Add Some Items First to Your Cart", Toast.LENGTH_SHORT).show();
            return;
        }
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
                return "Periodic Services";
            }  else if (position == 1) {
                return "Denting And Painting";
            } else if (position == 2) {
                return "Lights And Filaments";
            } else if (position == 3) {
                return "AC Service And Repair";
            } else if (position == 4) {
                return "Battery";
            } else if (position == 5){
                return "Cleaning";
            } else if (position == 6) {
                return "Tyres And Wheels";
            } else if (position == 7) {
                return "Windshield And Glass";
            } else {
                return "Periodic Services";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PeriodicServiceFragment();
                case 1:
                    return new DentingPaintingFragment();
                case 2:
                    return new LightsFilamentFragment();
                case 3:
                    return new AcServiceAndRepairFragment();
                case 4:
                    return new BatteryServiceFragment();
                case 5:
                    return new CleaningServiceFragment();
                case 6:
                    return new TyresAndWheelsFragment();
                case 7:
                    return new WinshieldAndGlassFragment();
                default:
                    return new PeriodicServiceFragment();
            }
        }

        @Override
        public int getCount() {
            return NumberOfServices;
        }
    }
}