package com.example.drug;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    Fragment home;
    Fragment drug;
    Fragment info;
    Fragment more;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // 상태바 높이만큼 패딩 설정
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // 시스템 바
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // 패딩 설정
            return insets;  // 반환
        }); // 상태바 높이만큼 패딩 설정
        bottom_navigation = findViewById(R.id.bottom_navigation);

        home = new home();
        drug = new drug();
        info = new info();
        more = new more();

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                Fragment fragment = null;

                if(itemId == R.id.home){
                    fragment = home;

                }else if(itemId == R.id.drug){
                    fragment = drug;

                }else if(itemId == R.id.info){
                    fragment = info;

                }else if(itemId == R.id.more){
                    fragment = more;
                }

                return loadFragment(fragment) ;
            }
        });
    }
    boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
            return true;
        }else{
            return false;
        }
    }
}