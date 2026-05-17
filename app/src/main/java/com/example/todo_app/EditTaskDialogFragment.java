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

public class EditTaskDialogFragment extends DialogFragment {

    private Task task;
    private TextInputEditText taskInput;
    private TextInputEditText dateInput;
    private TextInputEditText timeInput;
    private MaterialButton cancelButton;
    private MaterialButton updateButton;

    public EditTaskDialogFragment(Task task) {
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskInput = view.findViewById(R.id.taskInput);
        dateInput = view.findViewById(R.id.dateInput);
        timeInput = view.findViewById(R.id.timeInput);
        cancelButton = view.findViewById(R.id.cancelButton);
        updateButton = view.findViewById(R.id.addButton); // Reuse add button

        // Pre-fill current values
        taskInput.setText(task.getTaskName());
        dateInput.setText(task.getTaskDate());
        timeInput.setText(task.getTaskTime());
        updateButton.setText("UPDATE");

        cancelButton.setOnClickListener(v -> dismiss());
        updateButton.setOnClickListener(v -> {
            String taskName = taskInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            String time = timeInput.getText().toString().trim();

            if (taskName.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getActivity() instanceof TodoListActivity) {
                TodoListActivity activity = (TodoListActivity) getActivity();
                activity.updateTask(task.getId(), taskName, date, time);
            }

            dismiss();
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
