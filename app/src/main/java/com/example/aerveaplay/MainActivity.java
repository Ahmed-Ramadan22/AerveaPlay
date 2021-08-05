package com.example.aerveaplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.aerveaplay.Fragmants.AboutAsAnFragment;
import com.example.aerveaplay.Fragmants.DashBoardFragment;
import com.example.aerveaplay.Fragmants.MyProfileFragment;
import com.example.aerveaplay.Fragmants.NearByResFragment;
import com.example.aerveaplay.Fragmants.SettingFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;

import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;



public class MainActivity extends AppCompatActivity {


    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_NEARBY_RES = 3;
    private static final int POS_SETTING = 4;
    private static final int POS_ABOUT_US = 5;

    private static final int POS_LOGOUT = 7;


    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

    }



    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD) {
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        } else if (position == POS_MY_PROFILE) {
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container, myProfileFragment);
        } else if (position == POS_NEARBY_RES) {
            NearByResFragment nearByResFragment = new NearByResFragment();
            transaction.replace(R.id.container, nearByResFragment);
        } else if (position == POS_SETTING) {
            SettingFragment settingFragment = new SettingFragment();
            transaction.replace(R.id.container, settingFragment);
        } else if (position == POS_ABOUT_US) {
            AboutAsAnFragment aboutAsAnFragment = new AboutAsAnFragment();
            transaction.replace(R.id.container, aboutAsAnFragment);
        } else if (position == POS_LOGOUT) {
            finish();
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}


















