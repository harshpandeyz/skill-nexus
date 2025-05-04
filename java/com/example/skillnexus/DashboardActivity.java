package com.example.skillnexus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button btnSkillMatch, buttonLogout;
    private TextView textWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        textWelcome = findViewById(R.id.textWelcome);
        btnSkillMatch = findViewById(R.id.btnSkillMatch);
        buttonLogout = findViewById(R.id.buttonLogout);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Button click actions
        btnSkillMatch.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SkillMatchActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Bottom Navigation item selection handling
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                Toast.makeText(DashboardActivity.this, "Dashboard Selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.menu_search) {
                Toast.makeText(DashboardActivity.this, "Search Selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.menu_profile) {
                Toast.makeText(DashboardActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });
    }
}
