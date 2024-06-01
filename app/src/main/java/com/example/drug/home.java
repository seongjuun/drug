package com.example.drug;

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

public class home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button add_drug;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private TextView selectedDateTextView;
    private SQLiteHelper sqLiteHelper;

    public home() {
        // Required empty public constructor
    }

    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String todayString = dateFormat.format(today.getTime());
        selectedDateTextView.setText(todayString); // 현재 날짜로 TextView 업데이트
        adapter = new ViewPagerAdapter(this, selectedDateTextView);
        viewPager.setAdapter(adapter);




        // 오늘 날짜가 포함된 페이지로 이동
        int todayPosition = Integer.MAX_VALUE / 2;
        viewPager.setCurrentItem(todayPosition, false);

        sqLiteHelper = new SQLiteHelper(getActivity());
        ArrayList<String> drugName = sqLiteHelper.getDateDrugNames();
        System.out.println(drugName);

        RecyclerView drugListRecyclerView = view.findViewById(R.id.homeRecyclerView);
        drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DrugListAdapter adapter = new DrugListAdapter(drugName);
        drugListRecyclerView.setAdapter(adapter);
        return view;
    }
}
