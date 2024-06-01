package com.example.drug;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Home extends Fragment { // 홈 화면 Fragment

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button add_drug; // 약 추가 버튼
    private ViewPager2 viewPager;   // ViewPager2 객체
    private ViewPagerAdapter adapter;   // ViewPagerAdapter 객체
    private TextView selectedDateTextView;  // 선택된 날짜를 표시하는 TextView
    private static SQLiteHelper sqLiteHelper;       // SQLiteHelper 객체
    static RecyclerView drugListRecyclerView;   // RecyclerView 객체
    static ArrayList<String> drugName;  // 약 이름을 저장하는 ArrayList
    public Home() {
    }

    public static Home newInstance(String param1, String param2) { // home 객체 생성
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // Fragment 생성
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // Fragment 뷰 생성
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        add_drug = view.findViewById(R.id.add_drug);
        add_drug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addDrug.class);
                startActivity(intent);
            }
        });
        viewPager = view.findViewById(R.id.viewPager); // 'view' 객체를 통해 findViewById 호출
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView); // 'view' 객체를 통해 findViewById 호출
        Calendar today = Calendar.getInstance(); // 오늘 날짜
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayString = dateFormat.format(today.getTime());
        selectedDateTextView.setText(todayString); // 현재 날짜로 TextView 업데이트
        adapter = new ViewPagerAdapter(this, selectedDateTextView);
        viewPager.setAdapter(adapter);


        int todayPosition = Integer.MAX_VALUE / 2; // 오늘 날짜가 포함된 페이지
        viewPager.setCurrentItem(todayPosition, false); // 오늘 날짜로 ViewPager2 업데이트

        sqLiteHelper = new SQLiteHelper(getActivity()); // SQLiteHelper 객체 생성
        drugName = sqLiteHelper.getDateDrugNames(todayString);  // 오늘 날짜의 약 이름을 가져옴

        drugListRecyclerView = view.findViewById(R.id.homeRecyclerView);    // 'view' 객체를 통해 findViewById 호출
        drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  // RecyclerView의 레이아웃 매니저 설정

        DrugListAdapter adapter = new DrugListAdapter(drugName);    // DrugListAdapter 객체 생성
        drugListRecyclerView.setAdapter(adapter);   // RecyclerView에 어댑터 설정
        return view;
    }
    @SuppressLint("NotifyDataSetChanged")
    public static void Refresh(String date){    // 날짜에 따라 RecyclerView 갱신
        drugName = sqLiteHelper.getDateDrugNames(date); // 날짜에 따른 약 이름을 가져옴
        DrugListAdapter adapter = new DrugListAdapter(drugName);    // DrugListAdapter 객체 생성
        drugListRecyclerView.setAdapter(adapter);   // RecyclerView에 어댑터 설정
        adapter.notifyDataSetChanged(); //어댑터 갱신
    }
}
