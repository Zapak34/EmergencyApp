package com.nightvisionmedia.emergencyapp.constants;

/**
 * Created by Omar (GAZAMAN) Myers on 6/12/2017.
 */

public class Endpoints {
    //URLS
    public static final String LOGIN_URL = "http://nightvisionmediaapp.000webhostapp.com/login.php";
    public static final String REGISTRATION_URL = "http://nightvisionmediaapp.000webhostapp.com/registerUser.php";
    public static final String GET_ALL_USER_INFO_URL = "http://nightvisionmediaapp.000webhostapp.com/getUserData.php?email=";
    public static final String URL_FETCH_DEVICES = "http://nightvisionmediaapp.000webhostapp.com/getRegisteredDevices.php";
    public static final String URL_SEND_SINGLE_PUSH = "http://nightvisionmediaapp.000webhostapp.com/sendNotification.php";
    public static final String URL_ADD_POST="http://nightvisionmediaapp.000webhostapp.com/addPost.php";
    public static final String URL_REFRESH_SERVER_TOKEN ="http://nightvisionmediaapp.000webhostapp.com/refreshToken.php";
    public static final String URL_SEND_TOKEN_TO_SERVER ="http://nightvisionmediaapp.000webhostapp.com/sendTokenToServer.php";
    public final static String URL_GET_ALERTS ="http://nightvisionmediaapp.000webhostapp.com/getAlerts.php";
    public final static String URL_GET_HAPPENINGS ="http://nightvisionmediaapp.000webhostapp.com/getHappenings.php";
    public final static String URL_FORGET_PASSWORD ="http://nightvisionmediaapp.000webhostapp.com/forgetPassword.php?email=";
    //URL KEYS
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_FIRST_NAME = "fname";
    public static final String KEY_LAST_NAME = "lname";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE_NUMBER = "phone_num";
    public static final String KEY_FCM_TOKEN = "fcm_token";
    public static final String KEY_MAC_ADDRESS= "mac_address";
    public static final String JSON_ARRAY = "result";

    //DATABASE FIELD NAMES AND LOCAL DB
    public static final String KEY_DB_USER_ID = "user_id";
    public static final String KEY_DB_FIRST_NAME = "first_name";
    public static final String KEY_DB_LAST_NAME = "last_name";
    public static final String KEY_DB_AGE = "age";
    public static final String KEY_DB_EMAIL = "email";
    public static final String KEY_DB_PASSWORD = "password";
    public static final String KEY_DB_PHONE_NUMBER = "phone_number";
    public static final String KEY_DB_FCM_TOKEN = "fcm_token";
    public static final String KEY_DB_MAC_ADDRESS = "mac_address";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_PHP_ID = "id";
    public static final String KEY_PHP_TITLE = "title";
    public static final String KEY_PHP_MESSAGE = "message";
    public static final String KEY_PHP_IMAGE = "image";
    public static final String KEY_PHP_EMAIL = "email";
    public static final String KEY_PHP_TYPE = "type";
    public static final String KEY_PHP_FCM_TOKEN = "fcm_token";
    public static final String KEY_PHP_ACTION = "action";

    //BUNDLE KEYS
    public static final String KEY_BUNDLE_FNAME = "USERFNAME";
    public static final String KEY_BUNDLE_LNAME = "USERLNAME";
    public static final String KEY_BUNDLE_AGE = "USERAGE";
    public static final String KEY_BUNDLE_EMAIL = "USEREMAIL";
    public static final String KEY_BUNDLE_PASSWORD = "USERPASSWORD";
    public static final String KEY_BUNDLE_PHONE_NUMBER = "USERPHONENUMBER";

}
