package com.example.morfi.espacemembretuto;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREF_NAME = "app_prefs";
    private static final int PRIVATE_MODE = 0;
    private static final String IS_LOGGED = "isLogged";
    private static final String PSEUDO = "pseudo";
    private static final String ID = "id";
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public boolean isLogged(){
        return prefs.getBoolean(IS_LOGGED, false);
    }

    public String getPseudo(){
        return prefs.getString(PSEUDO, null);
    }

    public String getId(){
        return prefs.getString(ID, null);
    }

    public void insertUser(String id, String pseudo){
        editor.putString(ID, id);
        editor.putString(PSEUDO, pseudo);
        editor.putBoolean(IS_LOGGED, true);
        editor.commit();
    }

    public void logout(){
        editor.clear().commit();
    }

}
