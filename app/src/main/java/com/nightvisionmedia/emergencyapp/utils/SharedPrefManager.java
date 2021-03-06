package com.nightvisionmedia.emergencyapp.utils;

/**
 * Created by Lennis on 1/2/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.SharedPreferences.*;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    private static final String USER_OFFLINE_DETIALS = "UserInfoPref";
    private static final String DEFAULT = "N/A";
    private static final String APP_FIRST_START = "app_first_start";

    public static final String USER_EMAIL_KEY = "user_email";
    private static final String USER_FNAME_KEY = "user_fname";
    public static final String USER_PASSWORD_KEY = "user_password";
    private static final String USER_LNAME_KEY = "user_lname";
    private static final String USER_AGE_KEY = "user_age";
    private static final String USER_PHONE_KEY = "user_home_phone";
    public static final String USER_AUTO_LOGIN_KEY = "auto_login";
    private static final String USER_AUTO_LOGIN_SHOWN_FOR_FIRST_TIME = "auto_login_shown";
//    public static final String USER_UNENCRYPTED_PASSWORD = "unencrypted_password";


    private static final String USER_PROFILE_PIC_KEY = "picId";
    private static final String USER_SAVED_OFFLINE_KEY = "saved_offline";
    private static final String APP_USER_OFFLINE_DATA = "UserDetailsForApp";

    private static final boolean DEFAULT_IS_SAVED = false;
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    public void saveUserDetailsOffline(String userEmail, String userFname, String userLname,String encryptedUserPassword,
                                       String userAge, String userPhoneNumber){
        SharedPreferences sharedPreferences;
        sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL_KEY,userEmail);
        editor.putString(USER_PASSWORD_KEY,encryptedUserPassword);
        editor.putString(USER_FNAME_KEY,userFname);
        editor.putString(USER_LNAME_KEY,userLname);
        editor.putString(USER_AGE_KEY,userAge);
        editor.putString(USER_PHONE_KEY,userPhoneNumber);
        editor.apply();
    }

    public String getUserDetails(String getData){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(getData,DEFAULT);
    }

    public void isUserDataSavedOffline(boolean isSaved){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_SAVED_OFFLINE_KEY, isSaved);
        editor.commit();
    }

    public boolean checkUserDataSavedOffline(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(USER_SAVED_OFFLINE_KEY,DEFAULT_IS_SAVED);
    }

    public void setUserProfilePic(int picId){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(USER_PROFILE_PIC_KEY,picId);
        editor.apply();

    }
    public int getUserProfilePic(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_PROFILE_PIC_KEY,0);
    }

    public void saveAutomaticLogin(int isSaved){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(USER_AUTO_LOGIN_KEY, isSaved);
        editor.apply();
    }

    public int getAutomaticLogin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_AUTO_LOGIN_KEY,-1);
    }

    public void loginScreenSaveAutoLoginShown(int isSaved){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(USER_AUTO_LOGIN_SHOWN_FOR_FIRST_TIME, isSaved);
        editor.apply();
    }

    public int getLoginScreenSaveAutoLoginShown(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USER_AUTO_LOGIN_SHOWN_FOR_FIRST_TIME,-1);
    }

    public void appFirstTimeStarting(int isSaved){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(APP_FIRST_START, isSaved);
        editor.apply();
    }

    public int getAppFirstTimeStarting(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(APP_FIRST_START,-1);
    }

    //APP SAVED DATA AFTER LOGIN
    public void appSaveUserData(String userEmail, String userFname, String userPassword, String userLname,
                                 String userAge, String userPhoneNumber)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_USER_OFFLINE_DATA, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL_KEY,userEmail);
        editor.putString(USER_PASSWORD_KEY,userPassword);
        editor.putString(USER_FNAME_KEY,userFname);
        editor.putString(USER_LNAME_KEY,userLname);
        editor.putString(USER_AGE_KEY,userAge);
        editor.putString(USER_PHONE_KEY,userPhoneNumber);
        editor.apply();
    }


    //APP GET DATA
    public String appGetUserSaveData(String userData){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(APP_USER_OFFLINE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(userData,null);
    }


    public void deleteOfflineUserInfo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(USER_OFFLINE_DETIALS, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(USER_EMAIL_KEY).apply();
        sharedPreferences.edit().remove(USER_FNAME_KEY).apply();
        sharedPreferences.edit().remove(USER_PASSWORD_KEY).apply();
        sharedPreferences.edit().remove(USER_LNAME_KEY).apply();
        sharedPreferences.edit().remove(USER_AGE_KEY).apply();
        sharedPreferences.edit().remove(USER_PHONE_KEY).apply();
        Message.shortToast(mCtx,"Offline Mode Has Been Deactivated");
    }

}
