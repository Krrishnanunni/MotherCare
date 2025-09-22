package com.example.mothercare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.ViewHolder> {

    private Context context;
    private List<BabyModel> babyList;

    public BabyAdapter(Context context, List<BabyModel> babyList) {
        this.context = context;
        this.babyList = babyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.baby_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BabyModel baby = babyList.get(position);
        holder.name.setText("Baby Name: " + baby.getName());
        holder.age.setText("Age: " + baby.getAge());
        holder.gender.setText("Gender: " + baby.getGender());
        holder.weight.setText("Weight: " + baby.getWeight() + " kg");

        // Click listener for each item
        holder.itemView.setOnClickListener(v -> {
            ViewBabies.bid = baby.getBabyId();  // Set selected baby ID
            ((ViewBabies) context).showDialog();  // Call showDialog() from ViewBabies
        });
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, gender, weight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.baby_name);
            age = itemView.findViewById(R.id.baby_age);
            gender = itemView.findViewById(R.id.baby_gender);
            weight = itemView.findViewById(R.id.baby_weight);
        }
    }
}
