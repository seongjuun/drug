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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link drug#newInstance} factory method to
 * create an instance of this fragment.
 */
public class drug extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SQLiteHelper sqLiteHelper;
    public drug() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment drug.
     */
    // TODO: Rename and change types and number of parameters
    public static drug newInstance(String param1, String param2) {
        drug fragment = new drug();
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
        View view = inflater.inflate(R.layout.fragment_drug, container, false);

        Button add_drug = view.findViewById(R.id.add_drug);
        add_drug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addDrug.class); // Make sure you have AddDrugActivity created
                startActivity(intent);
            }
        });

        sqLiteHelper = new SQLiteHelper(getActivity());
        ArrayList<String> drugName = sqLiteHelper.getDrugNames();
        System.out.println(drugName);

        RecyclerView drugListRecyclerView = view.findViewById(R.id.drug_list);
        drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DrugListAdapter adapter = new DrugListAdapter(drugName);
        drugListRecyclerView.setAdapter(adapter);
        return view;
    }
}