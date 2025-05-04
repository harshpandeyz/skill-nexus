package com.example.skillnexus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private TextView textName, textSkill, textEmail;
    private Button buttonLogout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Views
        textName = findViewById(R.id.textName);
        textSkill = findViewById(R.id.textSkill);
        textEmail = findViewById(R.id.textEmail);
        buttonLogout = findViewById(R.id.buttonLogout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadProfileData();

        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            finish(); // Finish profile screen
        });
    }

    private void loadProfileData() {
        String userId = mAuth.getCurrentUser().getUid();

        DocumentReference userRef = db.collection("Users").document(userId);
        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String skill = documentSnapshot.getString("skill");
                        String email = mAuth.getCurrentUser().getEmail();

                        textName.setText(name != null ? name : "No Name");
                        textSkill.setText(skill != null ? skill : "No Skill");
                        textEmail.setText(email != null ? email : "No Email");
                    } else {
                        Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                });
    }
}
