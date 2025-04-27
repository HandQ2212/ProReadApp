package com.example.proreadapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proreadapp.R;
import com.example.proreadapp.view.fragment.HomeFragment;
import com.example.proreadapp.view.fragment.OfflineFragment;
import com.example.proreadapp.view.fragment.SettingFragment;
import com.example.proreadapp.view.fragment.TheLoaiFragment;
import com.example.proreadapp.view.fragment.TimKiemFragment;
import com.example.proreadapp.databinding.ActivityMainBinding;

import java.security.Key;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "fragment_prefs";
    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setupDefaultFragment();
        setupBottomNavigation();
        setupWindowInsets();
    }

    private void setupDefaultFragment() {
        //lay trang thai fragment hien tai qua sharedpreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String currentFragment = prefs.getString(KEY_CURRENT_FRAGMENT, "home"); //mac dinh la home

        Fragment fragment;
        switch (currentFragment) {
            case "theloai":
                fragment = new TheLoaiFragment();
                break;
            case "timkiem":
                fragment = new TimKiemFragment();
                break;
            case "offline":
                fragment = new OfflineFragment();
                break;
            case "setting":
                fragment = new SettingFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            changeFragment(item);
            return true;
        });
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    public void changeFragment(MenuItem item) {
        Fragment selectedFragment = getSelectedFragment(item);
        if (selectedFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.commit();

            // luu trang thai fragment hien tai vao sharedpreferences
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            int itemId = item.getItemId();
            if(itemId == R.id.nav_home)     editor.putString(KEY_CURRENT_FRAGMENT, "home");
            if(itemId == R.id.nav_theloai)  editor.putString(KEY_CURRENT_FRAGMENT, "theloai");
            if(itemId == R.id.nav_timkiem)  editor.putString(KEY_CURRENT_FRAGMENT, "timkiem");
            if(itemId == R.id.nav_offline)  editor.putString(KEY_CURRENT_FRAGMENT, "offline");
            if(itemId == R.id.nav_setting)  editor.putString(KEY_CURRENT_FRAGMENT, "setting");
            editor.apply();
        }
    }

    private Fragment getSelectedFragment(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.nav_home)     return new HomeFragment();
        if(itemId == R.id.nav_theloai)  return new TheLoaiFragment();
        if(itemId == R.id.nav_timkiem)  return new TimKiemFragment();
        if(itemId == R.id.nav_offline)  return new OfflineFragment();
        if(itemId == R.id.nav_setting)  return new SettingFragment();
        return null;
    }
}