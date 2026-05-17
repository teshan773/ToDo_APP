package com.example.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton signInButton;
    private TextView signUpLink;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize UserManager
        userManager = new UserManager(this);

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        signUpLink = findViewById(R.id.signUpLink);

        signInButton.setOnClickListener(v -> handleSignIn());

        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void handleSignIn() {
        String login = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        // Validation
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verify credentials
        User user = userManager.authenticate(login, password);
        if (user != null) {
            // Login successful
            userManager.loginUser(user);
            Toast.makeText(this, "Welcome " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();

            // Navigate to TodoListActivity
            Intent intent = new Intent(SignInActivity.this, TodoListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
