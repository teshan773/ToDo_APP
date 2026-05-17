package com.example.todo_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditUserInfoDialogFragment extends DialogFragment {

    private TextInputEditText editUsernameInput;
    private TextInputEditText editEmailInput;
    private MaterialButton cancelButton;
    private MaterialButton updateButton;
    private UserManager userManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userManager = new UserManager(getContext());

        editUsernameInput = view.findViewById(R.id.editUsernameInput);
        editEmailInput = view.findViewById(R.id.editEmailInput);
        cancelButton = view.findViewById(R.id.cancelButton);
        updateButton = view.findViewById(R.id.updateButton);

        // Pre-fill current values
        String currentUsername = userManager.getCurrentUsername();
        String currentEmail = userManager.getUserEmail(currentUsername);

        editUsernameInput.setText(currentUsername);
        editEmailInput.setText(currentEmail);
        editUsernameInput.setEnabled(false); // Prevent changing username

        cancelButton.setOnClickListener(v -> dismiss());
        updateButton.setOnClickListener(v -> {
            String newEmail = editEmailInput.getText().toString().trim();

            if (newEmail.isEmpty()) {
                Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newEmail.contains("@")) {
                Toast.makeText(getContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = userManager.updateUserProfile(currentUsername, newEmail);
            if (success) {
                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }
}
