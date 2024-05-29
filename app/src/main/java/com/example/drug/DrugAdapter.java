package com.example.drug;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugViewHolder> {
    private ArrayList<String> drugList;

    public DrugAdapter(ArrayList<String> drugList) {
        this.drugList = drugList;
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug, parent, false);
        return new DrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        String drugName = drugList.get(position);
        holder.drugNameTextView.setText(drugName);
        holder.itemView.setOnClickListener(v -> { //아이템 클릭시
            Intent intent = new Intent(v.getContext(), SaveDrug.class); //저장된 약물 액티비티로 이동
            intent.putExtra("drugName", drugName); //약물 이름 전달
            v.getContext().startActivity(intent);
            System.out.println(drugName);
        });
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    }

    public static class DrugViewHolder extends RecyclerView.ViewHolder {
        TextView drugNameTextView;

        public DrugViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameTextView = itemView.findViewById(R.id.drug_name);
        }
    }
}
