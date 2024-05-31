package com.example.drug;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.DrugViewHolder> {
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
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DrugInfo.class);
            intent.putExtra("drugName", drugName);
            v.getContext().startActivity(intent);
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
            drugNameTextView = itemView.findViewById(R.id.drug_list_name);
        }
    }
}