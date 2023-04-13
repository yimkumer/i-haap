package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;

public class doctorpage extends AppCompatActivity {

    private Button btn1, btn2,logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorpage);
        getSupportActionBar().hide();

        btn1= findViewById(R.id.btn1);
        btn2= findViewById(R.id.btn2);
        logout= findViewById(R.id.logout);
        mAuth= FirebaseAuth.getInstance();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (doctorpage.this, patientlist.class));
                Animatoo.animateFade(doctorpage.this);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (doctorpage.this, cdetails.class));
                Animatoo.animateSplit(doctorpage.this);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { mAuth.signOut();
                startActivity(new Intent(doctorpage.this, MainActivity.class));
                Animatoo.animateInAndOut(doctorpage.this);
            }
        });
    }
}