package com.example.cae2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cae2.model.Banki;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import utl.BankiUsers;

public class MainActivity extends AppCompatActivity {

    // declaring widgets
    Button uploadbtn;
    EditText pdf_name;
    StorageReference storageReference;
    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    ImageView imageView;

    // Firebase Connection

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("`pdf`");

    // User Id & email
    private String currentUserId;
    private String currentUseremail;

    private Uri pdfUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //widgets
        uploadbtn = findViewById(R.id.upload_btn);
        pdf_name = findViewById(R.id.name);
        imageView = findViewById(R.id.aboutpagebtn);
        firebaseAuth = FirebaseAuth.getInstance();



        //db
        storageReference = FirebaseStorage.getInstance().getReference();


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFiles();
            }
        });

        // instance to change to Log in
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profilePage.class);
                startActivity(intent);
                finish();
            }
        });
        if (BankiUsers.getInstance() != null) {
            currentUserId = BankiUsers.getInstance().getUserId();
            currentUseremail = BankiUsers.getInstance().getEmail();

        }


    }


    // pdf functionality

    private void selectFiles() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Uploads"), 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UploadFiles(data.getData());
        }


    }

    private void UploadFiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("Uploads/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL from the StorageReference
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String pdfUrl = uri.toString();

                        Banki banki = new Banki();
                        banki.setEmail(currentUseremail);
                        banki.setUserId(currentUserId);
                        banki.setPdf_name_create(pdf_name.getText().toString());
                        banki.setPdf_url(pdfUrl);

                        // Use a unique identifier for the document in Firestore
                        // Here, we are using the document ID as the unique identifier
                        collectionReference.document(currentUserId).set(banki)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Error uploading data to Firestore", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " + (int) progress + "%");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    public static class JobVacancies extends AppCompatActivity {

        Button refresh, applyNow;
        TextView tv, tv2;

        // Firebase Auth
        private FirebaseAuth firebaseAuth;
        private FirebaseAuth.AuthStateListener authStateListener;
        private FirebaseUser currentUser;

        //Firebase Authentication

        private FirebaseUser getCurrentUser;

        //Firebase Connection
        private FirebaseFirestore db = FirebaseFirestore.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_job_vacancies);

            refresh = findViewById(R.id.refresh);
            tv = findViewById(R.id.tvtxt1);
            tv2 = findViewById(R.id.tvtxt2);
            applyNow = findViewById(R.id.applyNow);





            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchData();
                }
            });

            applyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(JobVacancies.this, ssignUp.class);
                    startActivity(i);
                }
            });



        }
        private void fetchData() {
            DocumentReference document = FirebaseFirestore.getInstance().collection("jobsList").document("M5dzgYl6O8DPe69c1T45");
            document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        tv.setText(documentSnapshot.getString("JobList1")
                        + "\n"+ documentSnapshot.getString("description1"));
                        tv2.setText(documentSnapshot.getString("JobList2")
                                + "\n"+ documentSnapshot.getString("description2"));

                    }else {
                        Toast.makeText(JobVacancies.this, "jobs not found", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(JobVacancies.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();

                }
            });


        }
    }
}