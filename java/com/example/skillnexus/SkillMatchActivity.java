package com.example.skillnexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SkillMatchActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<User> userList;
    private UserListAdapter userAdapter; // Use BaseAdapter version

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_match);

        // Initialize views
        listView = findViewById(R.id.skillListView);
        userList = new ArrayList<>();

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String currentUid = mAuth.getCurrentUser().getUid();

        // Fetch all users except current user
        db.collection("Users")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        String uid = doc.getId();
                        if (!uid.equals(currentUid)) {
                            String name = doc.getString("name");
                            String skill = doc.getString("skill") != null ? doc.getString("skill") : "No Skill Info";
                            userList.add(new User(uid, name, skill));
                        }
                    }

                    userAdapter = new UserListAdapter(SkillMatchActivity.this, userList);
                    listView.setAdapter(userAdapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load users: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        // Handle user click
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            User selectedUser = userList.get(position);
            Intent intent = new Intent(SkillMatchActivity.this, ProfileActivity.class);
            intent.putExtra("uid", selectedUser.getUid());
            intent.putExtra("name", selectedUser.getName());
            intent.putExtra("skill", selectedUser.getSkill());
            startActivity(intent);
        });
    }
}
