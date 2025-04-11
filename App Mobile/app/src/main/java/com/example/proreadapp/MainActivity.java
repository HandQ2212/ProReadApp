package com.example.proreadapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.proreadapp.Fragment.HomeFragment;
import com.example.proreadapp.Fragment.OfflineFragment;
import com.example.proreadapp.Fragment.TheLoaiFragment;
import com.example.proreadapp.Fragment.TimKiemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            newFragment(item);
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void newFragment(MenuItem item){
        Fragment selectedFragment = null;

        if(item.getItemId() == R.id.nav_home){
            selectedFragment = new HomeFragment();
        }
        if(item.getItemId() == R.id.nav_theloai){
            selectedFragment = new TheLoaiFragment();
        }
        if(item.getItemId() == R.id.nav_offline){
            selectedFragment = new OfflineFragment();
        }
        if(item.getItemId() == R.id.nav_timkiem){
            selectedFragment = new TimKiemFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
    }
}