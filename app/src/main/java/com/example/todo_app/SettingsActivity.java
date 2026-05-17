package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class SettingsActivity extends AppCompatActivity {

    private MaterialCardView myProfileCard;
    private MaterialCardView devInfoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myProfileCard = findViewById(R.id.myProfileCard);
        devInfoCard = findViewById(R.id.devInfoCard);

        myProfileCard.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MyProfileActivity.class);
            startActivity(intent);
        });

        devInfoCard.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DevInformationActivity.class);
            startActivity(intent);
        });
    }
}
