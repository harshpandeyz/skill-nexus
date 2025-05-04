package com.example.skillnexus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerViewSearch;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize Views
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);

        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList); // Pass only userList
        recyclerViewSearch.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();

        // Listen to typing
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void searchUsers(String query) {
        if (query.isEmpty()) {
            userList.clear();
            userAdapter.notifyDataSetChanged();
            return;
        }

        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        userList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String skill = document.getString("skill");
                            String uid = document.getId();

                            if (name != null && skill != null) {
                                if (name.toLowerCase().contains(query.toLowerCase()) ||
                                        skill.toLowerCase().contains(query.toLowerCase())) {
                                    userList.add(new User(uid, name, skill));
                                }
                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    }
                });
    }
}
