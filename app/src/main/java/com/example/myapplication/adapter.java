package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter extends FirebaseRecyclerAdapter<patients,adapter.MyViewHolder>
{

    public adapter(@NonNull FirebaseRecyclerOptions<patients> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position , @NonNull patients patients) {

        holder.Patient_name.setText(patients.getPatient_name());
        holder.Gender.setText(patients.getGender());
        holder.Patient_age_yrs.setText(patients.getPatient_age_yrs());
        holder.Patient_height_ft.setText(patients.getPatient_height_ft());
        holder.Patient_weight_kg.setText(patients.getPatient_weight_kg());
        holder.Symptom1.setText(patients.getSymptom1());
        holder.Symptom2.setText(patients.getSymptom2());
        holder.Symptom3.setText(patients.getSymptom3());
        holder.Symptom4.setText(patients.getSymptom4());
        holder.Symptom5.setText(patients.getSymptom5());
        holder.Symptom6.setText(patients.getSymptom6());
        holder.Symptom7.setText(patients.getSymptom7());
        holder.Symptom8.setText(patients.getSymptom8());
        holder.Symptom9.setText(patients.getSymptom9());
        holder.Symptom10.setText(patients.getSymptom10());
        holder.Additional_details.setText(patients.getAdditional_details());
        Glide.with(holder.img.getContext()).load(patients.getPatient_id()).into(holder.img);


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }



   class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView Patient_name,Gender,Patient_age_yrs, Patient_height_ft, Patient_weight_kg,Symptom1,Symptom2,Symptom3,Symptom4,Symptom5,Symptom6,Symptom7,Symptom8,Symptom9,Symptom10,Additional_details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            Patient_name=itemView.findViewById(R.id.tvName);
            Gender=itemView.findViewById(R.id.tvGender);
            Patient_age_yrs = itemView.findViewById(R.id.tvPatient_age_yrs);
            Patient_height_ft = itemView.findViewById(R.id.tvPatient_height_ft);
            Patient_weight_kg = itemView.findViewById(R.id.tvPatient_weight_kg);
            Symptom1=itemView.findViewById(R.id.tvsymptom1);
            Symptom2=itemView.findViewById(R.id.tvsymptom2);
            Symptom3=itemView.findViewById(R.id.tvsymptom3);
            Symptom4=itemView.findViewById(R.id.tvsymptom4);
            Symptom5=itemView.findViewById(R.id.tvsymptom5);
            Symptom6=itemView.findViewById(R.id.tvsymptom6);
            Symptom7=itemView.findViewById(R.id.tvsymptom7);
            Symptom8=itemView.findViewById(R.id.tvsymptom8);
            Symptom9=itemView.findViewById(R.id.tvsymptom9);
            Symptom10=itemView.findViewById(R.id.tvsymptom10);
            Additional_details = itemView.findViewById(R.id.tvadditional);

        }
    }

}