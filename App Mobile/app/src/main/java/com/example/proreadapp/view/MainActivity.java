package com.example.proreadapp.view;

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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
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
        }
    }

    private Fragment getSelectedFragment(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            return new HomeFragment();
        }
        if (itemId == R.id.nav_theloai) {
            return new TheLoaiFragment();
        }
        if (itemId == R.id.nav_timkiem) {
            return new TimKiemFragment();
        }
        if (itemId == R.id.nav_offline) {
            return new OfflineFragment();
        }
        if (itemId == R.id.nav_setting) {
            return new SettingFragment();
        }
        return null;
    }
}