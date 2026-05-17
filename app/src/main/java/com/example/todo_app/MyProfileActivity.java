package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MyProfileActivity extends AppCompatActivity {

    private MaterialButton editInfoButton;
    private MaterialButton signOutButton;
    private ImageButton homeButton;
    private TextView usernameValue;
    private TextView emailValue;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userManager = new UserManager(this);

        editInfoButton = findViewById(R.id.editInfoButton);
        signOutButton = findViewById(R.id.signOutButton);
        homeButton = findViewById(R.id.homeButton);
        usernameValue = findViewById(R.id.usernameValue);
        emailValue = findViewById(R.id.emailValue);

        loadUserProfile();

        editInfoButton.setOnClickListener(v -> {
            EditUserInfoDialogFragment dialogFragment = new EditUserInfoDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "EditUserDialog");
        });

        signOutButton.setOnClickListener(v -> {
            showSignOutConfirmationDialog();
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, TodoListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserProfile() {
        String currentUsername = userManager.getCurrentUsername();
        String email = userManager.getUserEmail(currentUsername);

        usernameValue.setText(currentUsername);
        emailValue.setText(email);
    }

    private void showSignOutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_sign_out)
                .setPositiveButton(R.string.sign_out, (dialog, id) -> {
                    userManager.logoutUser();
                    Intent intent = new Intent(MyProfileActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserProfile();
    }
}
