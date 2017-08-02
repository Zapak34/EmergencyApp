package com.nightvisionmedia.emergencyapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.async.SendTokenToServerAsync;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.sugar_models.UserInformation;
import com.nightvisionmedia.emergencyapp.utils.DialogHandler;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LoginScreenActivity extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT=22000;
    public static final int READ_TIMEOUT=20000;
    private ProgressDialog pdLoading;
    EditText edtEmailAddress;
    EditText edtPassword;
    Button btnLogin;
    TextView tvRegisterLink, tvForgotPassword;

    UserInformation userInformation;
        String userEmail = "";
    String userFname = "";
    String userLname = "";
    String userPassword = "";
    String userAge = "";
    String userPhoneNumber = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setupWidgets();
        setupListeners();

        //Message.longToast(LoginScreenActivity.this,"DEVICE TOKEN FROM LOGIN SCREEN: "+ SharedPrefManager.getInstance(LoginScreenActivity.this).getDeviceToken());
        final String fcm_token = SharedPrefManager.getInstance(LoginScreenActivity.this).getDeviceToken();
        //Message.longToast(this,"This is the token: "+fcm_token);
        if(fcm_token == ""){
            //showErrorMessage("Oops!! Something went wrong please reinstall this application");
            pdLoading.setMessage("Oops!! Something went wrong please reinstall this application");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        final int isAutoLogin = SharedPrefManager.getInstance(LoginScreenActivity.this).getAutomaticLogin();
        if(isAutoLogin == 1){
            final String email = SharedPrefManager.getInstance(LoginScreenActivity.this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY);
            final String password = SharedPrefManager.getInstance(LoginScreenActivity.this).getUserDetails(SharedPrefManager.USER_PASSWORD_KEY);
            new LoginAsync1().execute(email,password);

        }

        final int isFirstTimeLogin = SharedPrefManager.getInstance(LoginScreenActivity.this).getLoginScreenSaveAutoLoginShown();
        if(isFirstTimeLogin == 1){
            final String email = SharedPrefManager.getInstance(LoginScreenActivity.this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY);
            edtEmailAddress.setText(email);
        }



    }

    private void setupWidgets() {
        edtEmailAddress = (EditText)findViewById(R.id.edtLoginEmailAddress);
        edtPassword = (EditText)findViewById(R.id.edtLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegister);
        tvForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        pdLoading = new ProgressDialog(LoginScreenActivity.this);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtEmailAddress.getText().toString();
                final String password = edtPassword.getText().toString();
                boolean isValid = checkUserInfo(email,password);
                if(isValid){
                    //getUserData(email,password);
                     new LoginAsync1().execute(email,password);
                }

            }
        });
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreenActivity.this,RegisterScreenActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreenActivity.this,ForgetPasswordScreenActivity.class));
            }
        });
    }

    private boolean checkUserInfo(String email, String password) {
        boolean isValid = false;
        if(!email.equals("") && !password.equals("")){
            isValid = true;
        }else{
            isValid = false;
            if(email.isEmpty()){
                edtEmailAddress.setError("Please enter your email address");
            }else{
                edtEmailAddress.setError(null);
            }

            if(password.isEmpty()){
                edtPassword.setError("Please enter your password");
            }else{
                edtPassword.setError(null);
            }
        }

        return isValid;
    }

    private void showErrorMessage(String message) {
        DialogHandler.ErrorInForm errorInForm = new DialogHandler.ErrorInForm();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        errorInForm.setArguments(bundle);
        errorInForm.show(getFragmentManager(), "No Name");
    }

    private void getUserData(String email, String password) {
        String url = Endpoints.GET_ALL_USER_INFO_URL+email+"&password="+password;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message.longToast(LoginScreenActivity.this, "ERROR FROM GET USER DATA: "+error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(LoginScreenActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Endpoints.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            //Message.longToast(LoginScreenActivity.this,"USER DETAILS FOR ASYNC: "+result.getJSONObject(0));
            final String userEmail = collegeData.getString(Endpoints.KEY_DB_EMAIL);
            final String userFname = collegeData.getString(Endpoints.KEY_DB_FIRST_NAME);
            final String userLname = collegeData.getString(Endpoints.KEY_DB_LAST_NAME);
            final String encryptedUserPassword = collegeData.getString(Endpoints.KEY_DB_PASSWORD);
            final String userAge = collegeData.getString(Endpoints.KEY_DB_AGE);
            final String userPhoneNumber = collegeData.getString(Endpoints.KEY_DB_PHONE_NUMBER);

//            userInformation = new UserInformation();
//            userInformation.setFirstName(userFname);
//            userInformation.setLastName(userLname);
//            //userInformation.setAge(userAge);
//            userInformation.setEmail(userEmail);
//            userInformation.setPassword(userPassword);
//            userInformation.setPhoneNumber(userPhoneNumber);
//            userInformation.save();
            SharedPrefManager.getInstance(LoginScreenActivity.this).saveUserDetailsOffline(userEmail,userFname,userLname,encryptedUserPassword,userAge,userPhoneNumber);

        } catch (JSONException e) {
            Message.longToast(LoginScreenActivity.this,e.toString());
            e.printStackTrace();
        }

    }


    public class LoginAsync1 extends AsyncTask<String, String, String> {
        private Context context;
        private int byGetOrPost = 1;
        private String email = "";
        private String password = "";
        UserInformation userInformation;



        int userID;

        //flag 0 means get and 1 means post.(By default it is get.)

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            if (byGetOrPost == 0) { //means by Get Method

                try {
                    email = (String) params[0];
                    password = (String) params[1];


                    String link = Endpoints.LOGIN_URL + "?" + Endpoints.KEY_EMAIL + "=" + email + "&" + Endpoints.KEY_PASSWORD + "=" + password;

                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            } else {
                try {
                    email = (String) params[0];
                    password = (String) params[1];


                    String link = Endpoints.LOGIN_URL;
                    String data = URLEncoder.encode(Endpoints.KEY_EMAIL, "UTF-8") + "=" +
                            URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode(Endpoints.KEY_PASSWORD, "UTF-8") + "=" +
                            URLEncoder.encode(password, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Message.shortToast(LoginScreenActivity.this, result);
            if (result.equals("Login Successful")) {
                final String fcm_token = SharedPrefManager.getInstance(LoginScreenActivity.this).getDeviceToken();
                final String action = "login";
                //SendTokenToServerAsync test = new SendTokenToServerAsync(context,1);
                //test.execute(email,fcm_token);
                getUserData(email, password);

                new SendTokenToServerAsync(LoginScreenActivity.this, 1).execute(email, fcm_token, action);
                //final int isAppFirstStart = SharedPrefManager.getInstance(LoginScreenActivity.this).getAppFirstTimeStarting();
                /*if(isAppFirstStart == -1){
                    SharedPrefManager.getInstance(LoginScreenActivity.this).appFirstTimeStarting(1);
                }*/


               startActivity(new Intent(LoginScreenActivity.this, HomeScreenActivity.class));

            }
        }
    }


}
