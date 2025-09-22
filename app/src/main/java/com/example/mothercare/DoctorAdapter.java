package com.example.mothercare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context context;
    private List<Doctor> doctorList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Doctor doctor);
    }

    public DoctorAdapter(Context context, List<Doctor> doctorList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.doctorList = doctorList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.tvDoctorName.setText(doctor.getName());
        holder.tvEmail.setText("Email: " + doctor.getEmail());
        holder.tvPhone.setText("Phone: " + doctor.getPhone());
        holder.tvQualification.setText("Qualification: " + doctor.getQualification());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(doctor));
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDoctorName, tvEmail, tvPhone, tvQualification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvQualification = itemView.findViewById(R.id.tvQualification);
        }
    }
}
