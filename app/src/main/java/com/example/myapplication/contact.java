package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

public class contact extends AppCompatActivity {

    EditText name,gmail,phone,medical;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name=findViewById(R.id.uname);
        gmail=findViewById(R.id.ugmail);
        phone=findViewById(R.id.uphone);
        medical=findViewById(R.id.umedical);


        }

}

