package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class cadapter extends FirebaseRecyclerAdapter<cpatients,cadapter.MyViewHolder>
{
    public cadapter(@NonNull FirebaseRecyclerOptions<cpatients> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position , @NonNull cpatients cpatients) {

        holder.Patient_name.setText(cpatients.getPatient_name());
        holder.Email.setText(cpatients.getEmail());
        holder.Phone_no.setText(cpatients.getPhone_no());
        holder.Address.setText(cpatients.getAddress());
        holder.State.setText(cpatients.getState());
        holder.City.setText(cpatients.getCity());
        holder.Pin.setText(cpatients.getPin());
        holder.Aadhar_card_number.setText(cpatients.getAadhar_card_number());
        holder.Pan_card_number.setText(cpatients.getPan_card_number());
        holder.Medical_Insurance.setText(cpatients.getMedical_Insurance());
        Glide.with(holder.img.getContext()).load(cpatients.getPatient_id()).into(holder.img);

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                            .setContentHolder(new ViewHolder(R.layout.activity_contact))
                            //.setExpanded(true,538)
                            .setExpanded(true,740)
                            .create();

                    View myview=dialogPlus.getHolderView();
                        EditText Patient_name=myview.findViewById(R.id.uname);
                        EditText Email=myview.findViewById(R.id.ugmail);
                        EditText Phone_no=myview.findViewById(R.id.uphone);
                        EditText Medical_Insurance=myview.findViewById(R.id.umedical);

                        Patient_name.setText(cpatients.getPatient_name());
                        Email.setText(cpatients.getEmail());
                        Phone_no.setText(cpatients.getPhone_no());
                        Medical_Insurance.setText(cpatients.getMedical_Insurance());

                        dialogPlus.show();
                    }
                });

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details,parent,false);
        return  new MyViewHolder(v);
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img, view;
        TextView  Patient_name,Email,Phone_no,Address,State,City,Pin,Aadhar_card_number,Pan_card_number,Medical_Insurance;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view=itemView.findViewById(R.id.view);
            img=itemView.findViewById(R.id.img);
            Patient_name=itemView.findViewById(R.id.tvName);
            Email=itemView.findViewById(R.id.tvgmail);
            Phone_no = itemView.findViewById(R.id.tvphone);
            Address = itemView.findViewById(R.id.tvaddress);
            State = itemView.findViewById(R.id.tvstate);
            City=itemView.findViewById(R.id.tvcity);
            Pin=itemView.findViewById(R.id.tvpin);
            Aadhar_card_number=itemView.findViewById(R.id.tvaadhar);
            Pan_card_number=itemView.findViewById(R.id.tvpan);
            Medical_Insurance=itemView.findViewById(R.id.tvmedical);
        }
    }
}
