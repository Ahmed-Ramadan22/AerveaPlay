package com.example.aerveaplay.home.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aerveaplay.Login.LoginActivity;
import com.example.aerveaplay.R;
import com.example.aerveaplay.home.Fragmants.AboutAsAnFragment;
import com.example.aerveaplay.home.Fragmants.HomeFragment;
import com.example.aerveaplay.home.Fragmants.MoviesFragment;
import com.example.aerveaplay.home.Fragmants.MyProfileFragment;
import com.example.aerveaplay.home.Fragmants.SettingFragment;
import com.example.aerveaplay.home.menu.DrawerAdapter;
import com.example.aerveaplay.home.menu.DrawerItem;
import com.example.aerveaplay.home.menu.SimpleItem;
import com.example.aerveaplay.home.menu.SpaceItem;
import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private static final int POS_DASHBOARD = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_MOVIES = 2;
    private static final int POS_SETTING = 3;
    private static final int POS_ABOUT_US = 4;
    private static final int POS_NULL = 5;
    private static final int POS_LOGOUT = 6;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_PROFILE),
                createItemFor(POS_MOVIES),
                createItemFor(POS_SETTING),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onItemSelected(int position) {
        Fragment fragment = null;

        switch (position){
            case POS_DASHBOARD:
                fragment = new HomeFragment();
                break;
            case POS_PROFILE:
                fragment = new MyProfileFragment();
                break;

            case POS_MOVIES:
                fragment = new MoviesFragment();
                break;
            case POS_SETTING:
                fragment = new SettingFragment();
                break;
            case POS_ABOUT_US:
                fragment = new AboutAsAnFragment();
                break;
            case POS_LOGOUT:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }

        if (fragment != null){
            showFragment(fragment);
        }

//        if (position == POS_LOGOUT) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
        slidingRootNav.closeMenu();

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.gradient_start_color))
                .withTextTint(color(R.color.gradient_start_color))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}















