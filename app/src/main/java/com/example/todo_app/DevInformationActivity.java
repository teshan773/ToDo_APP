package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DevInformationActivity extends AppCompatActivity {

    private MaterialButton exitButton;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_information);

        exitButton = findViewById(R.id.exitButton);
        homeButton = findViewById(R.id.homeButton);

        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(DevInformationActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(DevInformationActivity.this, TodoListActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
