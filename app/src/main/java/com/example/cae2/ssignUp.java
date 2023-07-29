package com.example.cae2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import utl.BankiUsers;

public class ssignUp extends AppCompatActivity {


    EditText password_create ;
    EditText name_create, inputuniversty;
    EditText email_create;
    TextView createBTN, disabilitytxt, seekbarprogress ,locationField2txt;
    Spinner disabilityField, locationField, jobField, locationField2,question1, question2,
            question3,question4, question5, question6, question7, question8, question9, question10;
    SeekBar seekBar;

    Switch aSwitch;


    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssign_up);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Firebase Auth require Google Account on the device to run successfully

        // Congratz!!
        //  Creating Users and logging into AddJournalActivity

        //widget initialization
        createBTN = findViewById(R.id.acc_sign_up_button);
       password_create = findViewById(R.id.password_create);
          email_create = findViewById(R.id.email_create);
        name_create = findViewById(R.id.name);
        locationField = findViewById(R.id.locationSpinner);
        jobField = findViewById(R.id.jobfieldSpinner);
        disabilityField = findViewById(R.id.disabilitySpinner);
      aSwitch = findViewById(R.id.switch1);
      disabilitytxt = findViewById(R.id.disabilitytxtv);
      seekBar = findViewById(R.id.seekbar);
      seekbarprogress = findViewById(R.id.progressseek);
      locationField2 = findViewById(R.id.locationSpinner2);
      locationField2txt = findViewById(R.id.locationSpinner2txt);
        inputuniversty = findViewById(R.id.inputuniversty);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        question6 = findViewById(R.id.question6);
        question7 = findViewById(R.id.question7);
        question8 = findViewById(R.id.question8);
        question9 = findViewById(R.id.question9);
        question10 = findViewById(R.id.question10);


        // Data for Jobs spinner
        String[] jobs = {"please select" , "bancassurance", "capital market business institutions", "communication and CSR", "Compliance and Financial Security","corporate, Restructuring & Recovery", "Corporate Banking", "Credit & Risk Management", "Digital Banking", "EHFC", "Enterprises Banking", "Financial Administration", "Head Quarters", "Human Resources", "Information Technology", "Information Technology", "Internal Audit", "Legal","Logistics & Property", "Operations", "Operations & Property", "Operations", "Operations & Technology", "Private Banking", "Procurement", "Proximity Banking Development","Proximity Banking Distribution", "Retail Banking", "Retail Branches", "Retail Private & Banking Credit", "Security Safety & Environment", "Transformation & Organization Preformance"};

// Set up Jobs spinner adapter
        ArrayAdapter<String> jobAdapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, jobs);
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobField.setAdapter(jobAdapter);

// Data for Location spinner
        String[] locationOptions = {"please select","Alexandria", "Assuit", "Aswan", "Banha", "Bani Souwaif", "Cairo", "Coral Bay", "Damietta El Kournich", "EHFC", "El Gouna", "Giza", "Hurghada","Ismailia", "Kasr El Nil", "Luxor", "Mansora", "Mansoura Torail", "Menya", "Nabq Bay", "Port Said", "Qena", "Sadat", "Sharm El Sheikh", "Sohag", "Suez", "Tanta", "Zagazik"};

// Set up Location spinner adapter
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, locationOptions);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationField.setAdapter(locationAdapter);


        //Data for Location spinner
        String[] locationOptions2 = {"5th Settlement (El-Tagamoa El-Khames)", "Alex Bay", "Alexandria Sporting Club", "All Days", "Assiut", "Aswan", "Aswan El Mahatta", "Baghdad", "Banha", "Banha Branch ATM", "Beni Suef", "Beny Sweif Branch ATM", "Borg El-Aarab", "Chillout", "City Stars Branch", "Damietta Kornish", "Dandy Mall", "Dokki", "EL Messaha", "El Rehab 2 - Eastern Market", "El Sheikh Zayed - El Morshedy Mall", "EL-Teseen", "Emerald Mall Branch", "Faisal", "Fleming", "Garden City", "Gezirah", "Giza", "Gouna", "Hadayek El Qoba", "Hassan Maamoun branch", "Head Office", "Heliopolis", "Hurghada", "Ismailia", "Katameya", "Loran", "Luxor", "Maadi", "Maadi El Nasr", "Madinaty", "Madinaty Air Mall Branch", "Makram Ebeid", "Mansoura", "Mansoura Torail", "Menya", "Merghany", "Miami", "Mohandeseen", "Mokattam", "Nabq Bay", "Nasr City", "New Haram", "Obour", "Palm Strip Branch", "Port Said", "Pyramids", "Qena", "Ramsis Hilton", "Rehab", "Roushdy", "Sadat City", "Saint Fatima", "Salah Salem", "Sarayat", "Sefarat", "Shams", "Sharkia Branch", "Sharm Elshikh", "Shebein EL-Kom branch", "Sheikh Zayed", "Sheraton", "Shobra", "Shooting Club Dokki", "Sixth of October Industrial Zone", "Smouha", "Sohag", "Stadium", "Suez", "Sultan Hussein", "Syria", "Talaat Harab", "Tanta", "Tenth of Ramadan", "Tharwat", "Zamalek"};


// Set up Location spinner adapter
        ArrayAdapter<String> locationAdapter2 = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, locationOptions2);
        locationAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationField2.setAdapter(locationAdapter2);
        //visablity
        locationField2txt.setVisibility(View.GONE);
        locationField2.setVisibility(View.GONE);

// Data for Disability spinner
        String[] disabilityOptions = {"non","Blindness", "Deafness", "Mobility Impairment", "Intellectual Disability", "Autism Spectrum Disorder", "Speech Impairment", "Dyslexia", "Attention Deficit Hyperactivity Disorder (ADHD)", "Cerebral Palsy", "Multiple Sclerosis (MS)", "Muscular Dystrophy", "Down Syndrome", "Post-Traumatic Stress Disorder (PTSD)", "Bipolar Disorder", "Schizophrenia", "Chronic Pain", "Epilepsy", "Amputation", "Visual Impairment", "Hearing Impairment"};
       // disable disability field
        disabilityField.setVisibility(View.GONE);
        disabilitytxt.setVisibility(View.GONE);
        seekbarprogress.setVisibility(View.GONE);
        seekBar.setVisibility(View.GONE);



// Set up Disability spinner adapter
        ArrayAdapter<String> disabilityAdapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, disabilityOptions);
        disabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disabilityField.setAdapter(disabilityAdapter);




        //question 1

        String[] question1options = {"please select", "Retail banking (savings accounts, checking accounts)"," Corporate banking (business loans, trade finance)", "Investment banking (M&A, IPOs, capital raising)", "Wealth management (financial planning, portfolio management)", "All of the above", "None of the above"};
// Set up question 1 spinner adapter
        ArrayAdapter<String> question1Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question1options);
        question1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question1.setAdapter(question1Adapter);

        //question 2

        String[] question2options = {"please select","Advanced - I can navigate and utilize various banking systems with ease.", " Intermediate - I am familiar with some banking software but might need further training.", "Basic - I have limited experience and may require significant guidance.", "None - I have no experience with banking software."};
// Set up question 2 spinner adapter
        ArrayAdapter<String> question2Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question2options);
        question2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question2.setAdapter(question2Adapter);

        //question 3
        String[] question3options = {"please select", "Use simple language and avoid technical jargon", " Ask follow-up questions to clarify the customer's needs", "Offer written materials or resources to support explanations.", " All of the above", "None of the above"};
// Set up question 3 spinner adapter
        ArrayAdapter<String> question3Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question3options);
        question3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question3.setAdapter(question3Adapter);

        //question 4
        String[] question4options = {"please select", "Attend relevant workshops and seminars.", "Regularly review banking regulatory websites and publications.", "Collaborate with the compliance team for updates.", "All of the above", " None of the above"};
// Set up question 4 spinner adapter
        ArrayAdapter<String> question4Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question4options);
        question4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question4.setAdapter(question4Adapter);

        //question 5
        String[] question5options = {"please select", " Stay calm and empathetic while actively listening", " Involve a supervisor or manager if the situation escalates","Offer appropriate compensation or solutions to resolve issues", "All of the above", "None of the above"};
// Set up question 5 spinner adapter
        ArrayAdapter<String> question5Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question5options);
        question5Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question5.setAdapter(question5Adapter);

        //question 6
        String[] question6options = {"please select", " Stay calm and empathetic while actively listening", " Involve a supervisor or manager if the situation escalates","Offer appropriate compensation or solutions to resolve issues", "All of the above", "None of the above"};
// Set up question 6 spinner adapter
        ArrayAdapter<String> question6Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question6options);
        question6Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question6.setAdapter(question6Adapter);

        //question 7
        String[] question7options = {"please select","Create a daily to-do list and set deadlines for each task.", "Utilize project management tools or software", "Seek assistance from colleagues for time-sensitive projects.", "All of the above", "None of the above"};
// Set up question 7 spinner adapter
        ArrayAdapter<String> question7Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question7options);
        question7Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question7.setAdapter(question7Adapter);

        //question 8
        String[] question8options = {"please select", "Analyze the situation and break it down into smaller components", " Consult with team members or subject matter experts", "Conduct research and gather relevant data to inform decision-making", "All of the above", "None of the above"};
// Set up question 8 spinner adapter
        ArrayAdapter<String> question8Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question8options);
        question8Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question8.setAdapter(question8Adapter);

        //question 9
        String[] question9options = {"please select", "Adhere strictly to bank policies on data security and confidentiality", "Limit access to sensitive information on a need-to-know basis", "Store physical and digital documents securely", "All of the above", "None of the above"};
// Set up question 9 spinner adapter
        ArrayAdapter<String> question9Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question9options);
        question9Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question9.setAdapter(question9Adapter);

        //question 10
        String[] question10options = {"please select", "Competitive interest rates and fees", " Exceptional customer service.", "Diverse and innovative banking products", "All of the above", "None of the above"};
// Set up question 10 spinner adapter
        ArrayAdapter<String> question10Adapter = new ArrayAdapter<>(ssignUp.this, android.R.layout.simple_spinner_item, question10options);
        question10Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question10.setAdapter(question10Adapter);



        //switch to turn disabilities on and off
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    disabilityField.setVisibility(View.VISIBLE);
                    disabilitytxt.setVisibility(View.VISIBLE);
                    seekbarprogress.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            seekbarprogress.setText("My disablity affects my work at a rate of " + progress + " /100");
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                }else {
                    disabilityField.setVisibility(View.GONE);
                    disabilitytxt.setVisibility(View.GONE);
                    seekbarprogress.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                }
            }
        });



// add cairo location spinner
        locationField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(locationField.getSelectedItem().toString() == "Cairo"){
                    locationField2txt.setVisibility(View.VISIBLE);
                    locationField2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










        // Authentication
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null){
                    // User Already Logged IN

                }else{
                    // No user yet!
                }
            }
        };

        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        !TextUtils.isEmpty(email_create.getText().toString())
                        && !TextUtils.isEmpty(name_create.getText().toString())
                        && !TextUtils.isEmpty(password_create.getText().toString())
                        && !TextUtils.isEmpty(inputuniversty.getText().toString())
                        && password_create.getText().toString().length() >=9
                        &&locationField.getSelectedItem().toString() != "please select"
                        &&jobField.getSelectedItem().toString() != "please select"
                        &&question1.getSelectedItem().toString() != "please select"
                        &&question2.getSelectedItem().toString() != "please select"
                        &&question3.getSelectedItem().toString() != "please select"
                        &&question4.getSelectedItem().toString() != "please select"
                        &&question5.getSelectedItem().toString() != "please select"
                        &&question6.getSelectedItem().toString() != "please select"
                        &&question7.getSelectedItem().toString() != "please select"
                        &&question8.getSelectedItem().toString() != "please select"
                        &&question9.getSelectedItem().toString() != "please select"
                        &&question10.getSelectedItem().toString() != "please select"

                )

                {

                    String email = email_create.getText().toString().trim();
                    String password = password_create.getText().toString().trim();
                    String username = name_create.getText().toString().trim();
                    String job = jobField.getSelectedItem().toString().trim();
                    String disability = disabilityField.getSelectedItem().toString().trim();
                    String locatition = locationField.getSelectedItem().toString().trim();
                    int disablityScale = seekBar.getProgress();
                    String universty = inputuniversty.getText().toString().trim();
                    Toast.makeText(ssignUp.this, "Account Created Click Sign In", Toast.LENGTH_SHORT).show();


                    CreateUserEmailAccount(email,password,username, job, disability, locatition, disablityScale, universty);
                } else if (password_create.getText().toString().length() <9) {
                    Toast.makeText(ssignUp.this, "Password must be at least 9 characters long", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ssignUp.this,
                            "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void CreateUserEmailAccount(String email, String password,
                                        final String username, final String job,
                                        final String disability, final String locatition, int disablityScale, String universty) {
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // we take user to Next Activity: (AddJournalActivity)
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                String currentUserId = currentUser.getUid();

                                // Create a userMap so we can create a user in the User Collection in Firestore
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("email", email);
                                userObj.put("name", username);
                                userObj.put("job", job);
                                userObj.put("disability", disability);
                                userObj.put("locatition", locatition);
                                userObj.put("Scale", disability);
                                userObj.put("uni", universty);




                                //Adding Users to Firestore
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    String name = task.getResult().getString("email");

                                                                    // If the user is registered successfully,
                                                                    // then move to the AddJournalActivity

                                                                    // Getting use of Global Journal USER
                                                                    BankiUsers journalUser = BankiUsers.getInstance();
                                                                    journalUser.setUserId(currentUserId);
                                                                    journalUser.setEmail(email);


                                                                    Intent i = new Intent(ssignUp.this,
                                                                            MainActivity.class);

                                                                    i.putExtra("email", email);
                                                                    i.putExtra("userId", currentUserId);
                                                                    startActivity(i);
                                                                } else {

                                                                }
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // Display Failed Message
                                                                Toast.makeText(ssignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        });


                            }
                        }
                    });
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}