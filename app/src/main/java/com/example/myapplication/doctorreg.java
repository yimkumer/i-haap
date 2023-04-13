package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class doctorreg extends AppCompatActivity {

    private Button submit ,reset,image,back;
    private EditText ename, ephone,email,docno,epass,epass2;
    private CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9;
    private ImageView img;
    Bitmap bitmap;
    Uri filepath;


    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorreg);
        getSupportActionBar().hide();

        image=findViewById(R.id.image);
        img=findViewById(R.id.imageView2);
        back=findViewById(R.id.back);
        c1= findViewById(R.id.checkBox);
        c2= findViewById(R.id.checkBox2);
        c3= findViewById(R.id.checkBox3);
        c4= findViewById(R.id.checkBox4);
        c5= findViewById(R.id.checkBox5);
        c6= findViewById(R.id.checkBox6);
        c7= findViewById(R.id.checkBox7);
        c8= findViewById(R.id.checkBox8);
        c9= findViewById(R.id.checkBox9);
        ename=  findViewById(R.id.ename);
        ephone= findViewById(R.id.ephone);
        email= findViewById(R.id.email);
        docno=  findViewById(R.id.docno);
        epass= findViewById(R.id.epass);
        epass2=  findViewById(R.id.epass2);
        submit=findViewById(R.id.submit);
        reset=findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ename.setText("");
                ephone.setText("");
                email.setText("");
                docno.setText("");
                epass.setText("");
                epass2.setText("");
                c1.setChecked(false);
                c2.setChecked(false);
                c3.setChecked(false);
                c4.setChecked(false);
                c5.setChecked(false);
                c6.setChecked(false);
                c7.setChecked(false);
                c8.setChecked(false);
                c9.setChecked(false);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (doctorreg.this, MainActivity.class));
                Animatoo.animateInAndOut(doctorreg.this);
            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(doctorreg.this)
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

        mAuth= FirebaseAuth.getInstance();
        submit= findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ename.getText().toString().trim();
                String user_email = email.getText().toString().trim();
                String enter_password = epass.getText().toString().trim();
                String phone_number = ephone.getText().toString().trim();
                String doctor_no = docno.getText().toString().trim().toUpperCase();
                String confirm_password = epass2.getText().toString().trim();


                if (name.isEmpty()) {
                    ename.setError("Name cannot be empty");
                    ename.requestFocus();
                } else if (user_email.isEmpty()) {
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                } else if (phone_number.isEmpty()) {
                    ephone.setError("Phone number cannot be empty");
                    ephone.requestFocus();
                } else if (doctor_no.isEmpty()) {
                    docno.setError("Doctor number cannot be empty");
                    docno.requestFocus();
                } else if (enter_password.isEmpty()) {
                    epass.setError("Password cannot be empty");
                    epass.requestFocus();
                } else if (confirm_password.isEmpty()) {
                    epass2.setError("Confirm password cannot be empty");
                    epass2.requestFocus();
                }else if (!confirm_password.matches(enter_password)) {
                    epass2.setError("Passwords do not match");
                    epass2.requestFocus();
                }else if(!c1.isChecked() && !c2.isChecked() && !c3.isChecked() &&
                        !c4.isChecked() && !c5.isChecked() && !c6.isChecked() &&
                        !c7.isChecked() && !c8.isChecked() && !c9.isChecked()) {
                    Toast.makeText(doctorreg.this, "Choose at least 1 specialty", Toast.LENGTH_SHORT).show();
                }else if (c1.isChecked() || c2.isChecked() ||
                        c3.isChecked() || c4.isChecked() ||
                        c5.isChecked() || c6.isChecked()||
                        c7.isChecked() || c8.isChecked() || c9.isChecked())
                {

                    mAuth.createUserWithEmailAndPassword(user_email, enter_password)
                            .addOnCompleteListener(doctorreg.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        uploadtofirebase();
                                    }
                                    else {
                                        Toast.makeText(doctorreg.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }  }
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
        dialog.setTitle("Registering Doctor");
        dialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Doctorreg_id/"+fileName);

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
                                DatabaseReference root=db.getReference("Registered_Doctors");

                                String name = ename.getText().toString();
                                String user_email = email.getText().toString().trim();
                                String phone_number = ephone.getText().toString().trim();
                                String doctor_no = docno.getText().toString().trim();
                                String d1="Dermatologist";
                                String d2="Orthopaedist";
                                String d3="Cardiologist";
                                String d4="Neurologist";
                                String d5="Pediatrician";
                                String d6="Psychiatrist";
                                String d7="Radiologist";
                                String d8="Opthalmologist";
                                String d9="Pulmonologist";

                                HashMap<String , String> usermap=new HashMap<>();

                                usermap.put("Doc_name", name);
                                usermap.put("email",user_email );
                                usermap.put("phone_no",phone_number );
                                usermap.put("Doc_number", doctor_no);
                                if (c1.isChecked())
                                    usermap.put("Specialty-1",d1);
                                if(c2.isChecked())
                                    usermap.put("Specialty-2",d2);
                                if (c3.isChecked())
                                    usermap.put("Specialty-3",d3);
                                if(c4.isChecked())
                                    usermap.put("Specialty-4",d4);
                                if (c5.isChecked())
                                    usermap.put("Specialty-5",d5);
                                if(c6.isChecked())
                                    usermap.put("Specialty-6",d6);
                                if (c7.isChecked())
                                    usermap.put("Specialty-7",d7);
                                if(c8.isChecked())
                                    usermap.put("Specialty-8",d8);
                                if(c9.isChecked())
                                    usermap.put("Specialty-9",d9);
                                usermap.put("Doctor_id", uri.toString());


                                root.push().setValue(usermap);

                                ename.setText("");
                                ephone.setText("");
                                email.setText("");
                                docno.setText("");
                                c1.setChecked(false);
                                c2.setChecked(false);
                                c3.setChecked(false);
                                c4.setChecked(false);
                                c5.setChecked(false);
                                c6.setChecked(false);
                                c7.setChecked(false);
                                c8.setChecked(false);
                                c9.setChecked(false);
                                epass.setText("");
                                epass2.setText("");
                                img.setImageResource(R.drawable.patient);
                                Toast.makeText(getApplicationContext(),"Registered Doctor Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent (doctorreg.this, MainActivity.class));
                                Animatoo.animateZoom(doctorreg.this);
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
