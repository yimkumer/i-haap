package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText e,e2,p,p2;
    private Button reg1, reg2, log1, log2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        e= findViewById(R.id.email);
        e2= findViewById(R.id.email2);
        p= findViewById(R.id.password);
        p2= findViewById(R.id.password2);
        mAuth= FirebaseAuth.getInstance();



        reg1=(Button) findViewById(R.id.register1);
        reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this, patientreg.class));
                Animatoo.animateCard(MainActivity.this);
            }
        });

        reg2=(Button) findViewById(R.id.register2);
        reg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),doctorreg.class));
                Animatoo.animateCard(MainActivity.this);
            }
        });

        log1=(Button) findViewById(R.id.login1);
        log1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e.getText().toString();
                String password= p.getText().toString();

                if (TextUtils.isEmpty(email)){
                    e.setError("Email cannot be empty");
                    e.requestFocus();
                }else if (TextUtils.isEmpty(password)){
                    p.setError("Password cannot be empty");
                    p.requestFocus();
                }else{
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, questionnaire.class));
                                Animatoo.animateSlideUp(MainActivity.this);
                            }else{
                                Toast.makeText(MainActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        log2=(Button) findViewById(R.id.login2);
        log2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2=e2.getText().toString();
                String password2= p2.getText().toString();

                if (TextUtils.isEmpty(email2)){
                    e2.setError("Email cannot be empty");
                    e2.requestFocus();
                }else if (TextUtils.isEmpty(password2)){
                    p2.setError("Password cannot be empty");
                    p2.requestFocus();
                }else{
                    mAuth.signInWithEmailAndPassword(email2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, doctorpage.class));
                                Animatoo.animateSlideDown(MainActivity.this);
                            }else{
                                Toast.makeText(MainActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}