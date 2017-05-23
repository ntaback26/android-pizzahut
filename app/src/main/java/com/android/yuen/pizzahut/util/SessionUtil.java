package com.android.yuen.pizzahut.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.yuen.pizzahut.model.User;

public class SessionUtil {

    public static final String PREF_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";

    public static User getUser(Context context) {
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER, context.MODE_PRIVATE);
        int id = preferences.getInt(USER_ID, 0);
        String name = preferences.getString(USER_NAME, null);
        String email = preferences.getString(USER_EMAIL, null);
        if (id != 0 && name != null && email != null) {
            user = new User(id, name, email);
        }
        return user;
    }

    public static void setUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID, user.getId());
        editor.putString(USER_NAME, user.getName());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.commit();
    }

    public static void removeUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
