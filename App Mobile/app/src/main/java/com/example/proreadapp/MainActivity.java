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
import com.example.proreadapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Mac dinh hien HomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        //Chon muc trong BottomNavigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            changeFragment(item);
            return true;
        });

        //Dong bo giao dien
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void changeFragment(MenuItem item){
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        //Chon fragment de chuyen
        if(itemId == R.id.nav_home){
            selectedFragment = new HomeFragment();
        }
        if(itemId == R.id.nav_theloai){
            selectedFragment = new TheLoaiFragment();
        }
        if(itemId == R.id.nav_timkiem){
            selectedFragment = new TimKiemFragment();
        }
        if(itemId == R.id.nav_offline){
            selectedFragment = new OfflineFragment();
        }

        //Chuyen fragment
        if(selectedFragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
    }
}