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

public class MainActivity extends AppCompatActivity { // 메인 액티비티

    BottomNavigationView bottom_navigation; // 바텀 네비게이션 뷰
    Fragment home;  // 홈 프래그먼트
    Fragment drug;  // 약품 프래그먼트
    Fragment info;  // 정보 프래그먼트
    Fragment more;  // 더보기 프래그먼트
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 액티비티 생성
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // 상태바 높이만큼 패딩 설정
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // 시스템 바
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // 패딩 설정
            return insets;  // 반환
        }); // 상태바 높이만큼 패딩 설정
        bottom_navigation = findViewById(R.id.bottom_navigation);   // 바텀 네비게이션 뷰

        home = new Home();  // 홈 프래그먼트
        drug = new Drug();  // 약품 프래그먼트
        info = new Info();  // 정보 프래그먼트
        more = new More();  // 더보기 프래그먼트

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {    // 바텀 네비게이션 뷰 아이템 선택 리스너
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {   // 네비게이션 아이템 선택

                int itemId = item.getItemId();  // 아이템 아이디
                Fragment fragment = null;   // 프래그먼트

                if(itemId == R.id.home){    // 홈
                    fragment = home;

                }else if(itemId == R.id.drug){  // 약품
                    fragment = drug;

                }else if(itemId == R.id.info){  // 정보
                    fragment = info;

                }else if(itemId == R.id.more){  // 더보기
                    fragment = more;
                }

                return loadFragment(fragment) ; // 프래그먼트 로드
            }
        });
    }
    boolean loadFragment(Fragment fragment){    // 프래그먼트 로드
        if(fragment != null){   // 프래그먼트가 널이 아닐 때
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