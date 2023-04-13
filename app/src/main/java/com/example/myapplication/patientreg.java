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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class patientreg extends AppCompatActivity {

    private Button submit, reset1 , image,back;
    private EditText ename, ephone, email, eaddress, estate, ecity, epin, ecard,epan, epass, epass2, minsurance;
    ImageView img;
    Bitmap bitmap;
    Uri filepath;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientreg);
        getSupportActionBar().hide();

        img= findViewById(R.id.imageView2);
        submit= findViewById(R.id.submit);
        image= findViewById(R.id.image);
        back=findViewById(R.id.back);
        ename =  findViewById(R.id.ename);
        ephone = findViewById(R.id.ephone);
        email = findViewById(R.id.email);
        eaddress =  findViewById(R.id.eaddress);
        estate =  findViewById(R.id.estate);
        ecity =  findViewById(R.id.ecity);
        epin =  findViewById(R.id.epin);
        ecard = findViewById(R.id.ecard);
        epan=findViewById(R.id.epan);
        minsurance=findViewById(R.id.minsurance);
        epass =  findViewById(R.id.epass);
        epass2 =  findViewById(R.id.epass2);
        reset1 = findViewById(R.id.reset1);

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ename.setText("");
                ephone.setText("");
                email.setText("");
                eaddress.setText("");
                estate.setText("");
                ecity.setText("");
                epin.setText("");
                ecard.setText("");
                epan.setText("");
                epass.setText("");
                epass2.setText("");
                minsurance.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (patientreg.this, MainActivity.class));
                Animatoo.animateInAndOut(patientreg.this);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(patientreg.this)
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ename.getText().toString().trim().toLowerCase();
                 String user_email = email.getText().toString().trim();
                String enter_password = epass.getText().toString().trim();
                 String phone_number = ephone.getText().toString().trim();
                 String address = eaddress.getText().toString().trim();
                 String state = estate.getText().toString().trim();
                String city = ecity.getText().toString().trim();
                 String pin = epin.getText().toString().trim();
                 String aadhar = ecard.getText().toString().trim();
                 String pan = epan.getText().toString().trim();
                 String confirm_password = epass2.getText().toString().trim();
                 String medical_insurance = minsurance.getText().toString().trim();


                if (name.isEmpty()) {
                    ename.setError("Name cannot be empty");
                    ename.requestFocus();
                } else if (user_email.isEmpty()) {
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                }else if (phone_number.isEmpty()) {
                    ephone.setError("Phone number cannot be empty");
                    ephone.requestFocus();
                } else if (address.isEmpty()) {
                    eaddress.setError("Address cannot be empty");
                    eaddress.requestFocus();
                } else if (state.isEmpty()) {
                    estate.setError("State cannot be empty");
                    estate.requestFocus();
                } else if (city.isEmpty()) {
                    ecity.setError("City cannot be empty");
                    ecity.requestFocus();
                } else if (pin.isEmpty()) {
                    epin.setError("Pin code cannot be empty");
                    epin.requestFocus();
                } else if (aadhar.isEmpty() && pan.isEmpty()) {
                    ecard.setError("Aadhar or Pan card number cannot be empty");
                    ecard.requestFocus();
                }else if (!aadhar.isEmpty() && !pan.isEmpty()) {
                    ecard.setError("Enter either Aadhar or Pan , not both");
                    ecard.requestFocus();
                } else if (enter_password.isEmpty()) {
                    epass.setError("Password cannot be empty");
                    epass.requestFocus();
                } else if (confirm_password.isEmpty()) {
                    epass2.setError("Confirm password cannot be empty");
                    epass2.requestFocus();
                }else if (!confirm_password.matches(enter_password)) {
                    epass2.setError("Passwords do not match");
                    epass2.requestFocus();
                }else{

                    mAuth.createUserWithEmailAndPassword(user_email, enter_password)
                            .addOnCompleteListener(patientreg.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        uploadtofirebase();
                                    }
                                    else {
                                        Toast.makeText(patientreg.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }

                            });
                }
            }

        });
    };

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
        final StorageReference uploader=storage.getReference("Patientreg_id/"+fileName);

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
                                DatabaseReference root=db.getReference("Registered_Patients");

                                String name = ename.getText().toString().toLowerCase();
                                String user_email = email.getText().toString().trim();
                                String phone_number = ephone.getText().toString().trim();
                                String address = eaddress.getText().toString().trim();
                                String state = estate.getText().toString().trim();
                                String city = ecity.getText().toString().trim();
                                String pin = epin.getText().toString().trim();
                                String aadhar = ecard.getText().toString().trim();
                                String pan = epan.getText().toString().trim().toUpperCase();
                                String medical_insurance = minsurance.getText().toString();

                                HashMap<String , String> usermap=new HashMap<>();

                                usermap.put("Patient_name", name);
                                usermap.put("Email",user_email );
                                usermap.put("Phone_no",phone_number );
                                usermap.put("Address",address );
                                usermap.put("State", state);
                                usermap.put("City",city );
                                usermap.put("Pin",pin);
                                usermap.put("Aadhar_card_number", aadhar);
                                usermap.put("Pan_card_number", pan);
                                usermap.put("Medical_Insurance",medical_insurance);
                                usermap.put("Patient_id", uri.toString());


                                root.push().setValue(usermap);

                                ename.setText("");
                                ephone.setText("");
                                eaddress.setText("");
                                email.setText("");
                                estate.setText("");
                                ecity.setText("");
                                epin.setText("");
                                ecard.setText("");
                                epan.setText("");
                                minsurance.setText("");
                                epass.setText("");
                                epass2.setText("");
                                img.setImageResource(R.drawable.patient);
                                Toast.makeText(getApplicationContext(),"Registered Patient Successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent (patientreg.this, MainActivity.class));
                                Animatoo.animateZoom(patientreg.this);
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

