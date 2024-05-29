package com.example.drug;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;

    Fragment home;
    Fragment drug;
    Fragment info;
    Fragment more;
    Button add_drug;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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