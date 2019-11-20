package com.queenscc.delivery.shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import com.queenscc.delivery.models.Rider;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;



    public  SessionHandler(Context mContext){
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void loginUser(String email,String fullname){
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_FULL_NAME, fullname);
        Date date = new Date();

        //Setting user session for the next 7 days
        long millis = date.getTime() + (7*24*60*60*1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }
    public boolean isLoggedIn(){
        Date currentDate = new Date();
        long millis = mPreferences.getLong(KEY_EXPIRES,0);

        //If session !set then user !loggedin
        if(millis == 0){
            return false;
        }
        Date expiryDate = new Date(millis);

        //Check if session is expired
        return currentDate.before(expiryDate);
    }

    public Rider getRiderDetails(){
        if(!isLoggedIn()){
            return null;
        }

        Rider rider = new Rider();
        rider.setUSERNAME(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        rider.setFULLNAME(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        rider.setEMAIL(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        rider.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES,0)));

        return rider;
    }

    public void logoutRider(){
        mEditor.clear();
        mEditor.commit();
    }


}
