package com.example.todo_app;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class UserManager {

    private static final String PREF_NAME = "TodoAppUserPrefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_CURRENT_USERNAME = "current_username";
    private static final String KEY_CURRENT_USER_ID = "current_user_id";

    private SharedPreferences sharedPreferences;
    private DBHelper dbHelper;

    public UserManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.dbHelper = new DBHelper(context);
    }

    /**
     * Register a new user
     */
    public boolean registerUser(String username, String email, String password) {
        // Check if user already exists
        if (userExists(username)) {
            return false;
        }

        if (dbHelper.isEmailExists(email)) {
            return false;
        }

        // Validate inputs
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        return dbHelper.insertUser(username, email, password) != -1;
    }

    /**
     * Check if user exists
     */
    public boolean userExists(String username) {
        return dbHelper.isUsernameExists(username);
    }

    /**
     * Verify user login credentials
     */
    public boolean verifyLogin(String username, String password) {
        return authenticate(username, password) != null;
    }

    @Nullable
    public User authenticate(String login, String password) {
        if (login == null || login.trim().isEmpty() || password == null) {
            return null;
        }

        User user = dbHelper.getUserByUsernameAndPassword(login, password);
        if (user != null) {
            return user;
        }
        return dbHelper.getUserByEmailAndPassword(login, password);
    }

    /**
     * Login user (set as current logged-in user)
     */
    public void loginUser(String username) {
        User user = dbHelper.getUserByUsername(username);
        if (user == null) {
            return;
        }
        loginUser(user);
    }

    public void loginUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_CURRENT_USER_ID, user.getId());
        editor.putString(KEY_CURRENT_USERNAME, user.getUsername());
        editor.apply();
    }

    /**
     * Logout user
     */
    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.remove(KEY_CURRENT_USERNAME);
        editor.remove(KEY_CURRENT_USER_ID);
        editor.apply();
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
                && sharedPreferences.getInt(KEY_CURRENT_USER_ID, -1) > 0;
    }

    /**
     * Get current logged-in username
     */
    public String getCurrentUsername() {
        return sharedPreferences.getString(KEY_CURRENT_USERNAME, "");
    }

    public int getCurrentUserId() {
        return sharedPreferences.getInt(KEY_CURRENT_USER_ID, -1);
    }

    /**
     * Get user email
     */
    public String getUserEmail(String username) {
        User user = dbHelper.getUserByUsername(username);
        return user == null ? "" : user.getEmail();
    }

    /**
     * Update user profile
     */
    public boolean updateUserProfile(String username, String newEmail) {
        User user = dbHelper.getUserByUsername(username);
        if (user == null) {
            return false;
        }

        if (dbHelper.isEmailExistsForOtherUser(newEmail, user.getId())) {
            return false;
        }

        return dbHelper.updateUserProfile(user.getId(), user.getUsername(), newEmail);
    }
}
