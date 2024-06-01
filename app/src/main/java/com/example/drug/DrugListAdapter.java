package com.example.drug;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.DrugViewHolder> { // 약물 목록을 보여주는 RecyclerView Adapter
    private ArrayList<String> drugList;

    public DrugListAdapter(ArrayList<String> drugList) {
        this.drugList = drugList;
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug_list, parent, false);
        return new DrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        String drugName = drugList.get(position);
        holder.drugNameTextView.setText(drugName);
        holder.itemView.setOnClickListener(v -> { // 약물 목록을 클릭하면 약물 정보 화면으로 이동
            Intent intent = new Intent(v.getContext(), DrugInfo.class);
            intent.putExtra("drugName", drugName);  // 약물 이름을 전달
            v.getContext().startActivity(intent);   // 약물 정보 화면으로 이동
        });
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    } // 약물 목록의 개수 반환

    public static class DrugViewHolder extends RecyclerView.ViewHolder {
        TextView drugNameTextView;

        public DrugViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameTextView = itemView.findViewById(R.id.drug_list_name); // 약물 목록의 이름을 보여주는 TextView
        }
    }
}
