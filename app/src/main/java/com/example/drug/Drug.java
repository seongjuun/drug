package com.example.drug;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class Drug extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private String mParam2;
    SQLiteHelper sqLiteHelper;
    public Drug() { //생성자
    }


    public static Drug newInstance(String param1, String param2) {  //newInstance 메소드
        Drug fragment = new Drug();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //onCreate 메소드
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { //getArguments()가 null이 아닐 때
            mParam1 = getArguments().getString(ARG_PARAM1); //mParam1에 getArguments()의 ARG_PARAM1을 넣음
            mParam2 = getArguments().getString(ARG_PARAM2); //mParam2에 getArguments()의 ARG_PARAM2을 넣음
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {   //onCreateView 메소드
        View view = inflater.inflate(R.layout.fragment_drug, container, false); //fragment_drug.xml을 view에 넣음

        Button add_drug = view.findViewById(R.id.add_drug); //add_drug 버튼을 찾아서 add_drug에 넣음
        add_drug.setOnClickListener(new View.OnClickListener() {    //add_drug 버튼을 눌렀을 때
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addDrug.class); //addDrug.class로 이동
                startActivity(intent);  //액티비티 시작
            }
        });

        sqLiteHelper = new SQLiteHelper(getActivity()); //SQLiteHelper 객체 생성
        ArrayList<String> drugName = sqLiteHelper.getDrugNames();   //getDrugNames 메소드를 호출하여 drugName에 넣음
        System.out.println(drugName);   //drugName 출력

        RecyclerView drugListRecyclerView = view.findViewById(R.id.drug_list);  //drug_list를 찾아서 drugListRecyclerView에 넣음
        drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //리사이클러뷰를 세로로 배치

        DrugListAdapter adapter = new DrugListAdapter(drugName);    //DrugListAdapter 객체 생성
        drugListRecyclerView.setAdapter(adapter);   //리사이클러뷰에 어댑터 설정
        return view;
    }
}