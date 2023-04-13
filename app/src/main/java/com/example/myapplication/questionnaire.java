package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class questionnaire extends AppCompatActivity {

    private Button submit ,reset, logout, id;
    private EditText age, height, weight, optional,ename;
    private RadioButton rbmale, rbfemale,rbtrans;
    private CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    private ImageView img;

    Bitmap bitmap;
    Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        getSupportActionBar().hide();

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        img= findViewById(R.id.image);
        id=findViewById(R.id.slctimg);
        c1= findViewById(R.id.checkBox);
        c2= findViewById(R.id.checkBox2);
        c3= findViewById(R.id.checkBox3);
        c4= findViewById(R.id.checkBox4);
        c5= findViewById(R.id.checkBox5);
        c6= findViewById(R.id.checkBox6);
        c7= findViewById(R.id.checkBox7);
        c8= findViewById(R.id.checkBox8);
        c9= findViewById(R.id.checkBox9);
        c10= findViewById(R.id.checkBox10);
        rbmale= findViewById(R.id.radioButton);
        rbfemale= findViewById(R.id.radioButton2);
        rbtrans=findViewById(R.id.radioButton3);
        ename=findViewById(R.id.ename);
        age=  findViewById(R.id.eage);
        age.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "99" )}) ;
        height= findViewById(R.id.eheight);
        height.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "9" )}) ;
        weight= findViewById(R.id.eweight);
        weight.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "180" )}) ;
        optional= findViewById(R.id.optional);
        submit= findViewById(R.id.submit);
        logout=findViewById(R.id.logout);
        reset=findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ename.setText("");
                age.setText("");
                height.setText("");
                weight.setText("");
                optional.setText("");
                rbmale.setChecked(false);
                rbfemale.setChecked(false);
                rbtrans.setChecked(false);
                c1.setChecked(false);
                c2.setChecked(false);
                c3.setChecked(false);
                c4.setChecked(false);
                c5.setChecked(false);
                c6.setChecked(false);
                c7.setChecked(false);
                c8.setChecked(false);
                c9.setChecked(false);
                c10.setChecked(false);
            }
        });


        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(questionnaire.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(questionnaire.this, MainActivity.class));
                Animatoo.animateInAndOut(questionnaire.this);
            }
        });

        submit= findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ename.getText().toString().trim();
                String patient_age = age.getText().toString().trim();
                String patient_height = height.getText().toString().trim();
                String patient_weight = weight.getText().toString().trim();


                if (!rbmale.isChecked() && !rbfemale.isChecked() && !rbtrans.isChecked()){
                    Toast.makeText(questionnaire.this, "Choose Gender", Toast.LENGTH_SHORT).show();
                }else if (name.isEmpty()) {
                    ename.setError("Patient name cannot be empty");
                    ename.requestFocus();
                } else if (patient_age.isEmpty()) {
                    age.setError("Age cannot be empty");
                    age.requestFocus();
                } else if (patient_height.isEmpty()) {
                    height.setError("Height cannot be empty");
                    height.requestFocus();
                } else if (patient_weight.isEmpty()) {
                    weight.setError("Weight cannot be empty");
                    weight.requestFocus();
                }else if(!c1.isChecked() && !c2.isChecked() && !c3.isChecked() &&
                        !c4.isChecked() && !c5.isChecked() && !c6.isChecked() &&
                        !c7.isChecked() && !c8.isChecked() && !c9.isChecked() && !c10.isChecked()) {
                    Toast.makeText(questionnaire.this, "Choose your symptom(s)", Toast.LENGTH_SHORT).show();
                }else if (c1.isChecked() || c2.isChecked() ||
                        c3.isChecked() || c4.isChecked() ||
                        c5.isChecked() || c6.isChecked()||
                        c7.isChecked() || c8.isChecked() || c9.isChecked() || c10.isChecked())
                {
                    uploadtofirebase();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void uploadtofirebase()
    {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Registering Patient");
        dialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Questionnaire_id/"+fileName);

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri){

                                dialog.dismiss();
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("Patient_Questionnaire");

                                String name = ename.getText().toString().toLowerCase();
                                String patient_age = age.getText().toString().trim();
                                String patient_height = height.getText().toString().trim();
                                String patient_weight = weight.getText().toString().trim();
                                String optional_details = optional.getText().toString();
                                String g1="MALE";
                                String g2="FEMALE";
                                String g3="TRANS";
                                String d1="Shortness of Breath";
                                String d2="High Fever";
                                String d3="Skin Rash";
                                String d4="Diarrhoea/Gastric problems";
                                String d5="Allergic Reaction";
                                String d6="Nauseous Vomiting";
                                String d7="Redness in eyes";
                                String d8="Joint/Muscle pain";
                                String d9="Headache";
                                String d10="Dizziness";

                                HashMap<String , String> usermap=new HashMap<>();

                                if (rbmale.isChecked())
                                    usermap.put("Gender", g1);
                                else if (rbfemale.isChecked())
                                    usermap.put("Gender",g2);
                                else if(rbtrans.isChecked())
                                    usermap.put("Gender",g3);
                                usermap.put("Patient_name",name);
                                usermap.put("Patient_age_yrs", patient_age);
                                usermap.put("Patient_height_ft",patient_height );
                                usermap.put("Patient_weight_kg",patient_weight );
                                usermap.put("Additional_details", optional_details);
                                if (c1.isChecked())
                                    usermap.put("Symptom1",d1);
                                if(c2.isChecked())
                                    usermap.put("Symptom2",d2);
                                if (c3.isChecked())
                                    usermap.put("Symptom3",d3);
                                if(c4.isChecked())
                                    usermap.put("Symptom4",d4);
                                if (c5.isChecked())
                                    usermap.put("Symptom5",d5);
                                if(c6.isChecked())
                                    usermap.put("Symptom6",d6);
                                if (c7.isChecked())
                                    usermap.put("Symptom7",d7);
                                if(c8.isChecked())
                                    usermap.put("Symptom8",d8);
                                if(c9.isChecked())
                                    usermap.put("Symptom9",d9);
                                if(c10.isChecked())
                                    usermap.put("Symptom10",d10);
                                usermap.put("Patient_id", uri.toString());

                                root.push().setValue(usermap);

                                ename.setText("");
                                age.setText("");
                                height.setText("");
                                weight.setText("");
                                optional.setText("");
                                rbmale.setChecked(false);
                                rbfemale.setChecked(false);
                                rbtrans.setChecked(false);
                                c1.setChecked(false);
                                c2.setChecked(false);
                                c3.setChecked(false);
                                c4.setChecked(false);
                                c5.setChecked(false);
                                c6.setChecked(false);
                                c7.setChecked(false);
                                c8.setChecked(false);
                                c9.setChecked(false);
                                c10.setChecked(false);
                                img.setImageResource(R.drawable.patient);
                                Toast.makeText(getApplicationContext(),"Questionnaire Submitted Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent (questionnaire.this, await.class));
                                Animatoo.animateZoom(questionnaire.this);
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Please wait a moment :"+(int)percent+" %");
                    }
                });

    }

}

