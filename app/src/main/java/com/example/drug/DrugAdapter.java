package com.example.drug;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugViewHolder> { //약물 어댑터
    private ArrayList<String> drugList; //약물 리스트

    public DrugAdapter(ArrayList<String> drugList) {
        this.drugList = drugList;
    }   //생성자

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug, parent, false);
        return new DrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {    //뷰홀더에 데이터 바인딩
        String drugName = drugList.get(position);
        holder.drugNameTextView.setText(drugName);
        holder.itemView.setOnClickListener(v -> { //아이템 클릭시
            Intent intent = new Intent(v.getContext(), SaveDrug.class); //저장된 약물 액티비티로 이동
            intent.putExtra("drugName", drugName); //약물 이름 전달
            v.getContext().startActivity(intent);   //액티비티 시작
            System.out.println(drugName);   //약물 이름 출력
        });
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    }   //리스트 크기 반환

    public static class DrugViewHolder extends RecyclerView.ViewHolder {    //약물 뷰홀더
        TextView drugNameTextView;  //약물 이름 텍스트뷰

        public DrugViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameTextView = itemView.findViewById(R.id.drug_name);   //약물 이름 텍스트뷰 아이디 연결
        }
    }
}
