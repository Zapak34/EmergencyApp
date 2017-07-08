package com.nightvisionmedia.emergencyapp.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nightvisionmedia.emergencyapp.activities.HomeScreenActivity;
import com.nightvisionmedia.emergencyapp.activities.LoginScreenActivity;
import com.nightvisionmedia.emergencyapp.activities.RegisterScreenActivity;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.sugar_models.UserInformation;
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

/**
 * Created by Omar (GAZAMAN) Myers on 6/13/2017.
 */

public class LoginAsync extends AsyncTask <String, String, String>{
    private Context context;
    private int byGetOrPost = 0;
    private String email = "";
    private String password = "";
    UserInformation userInformation;


//    String userEmail = "";
//    String userFname = "";
//    String userLname = "";
//    String userPassword = "";
//    String userAge = "";
//    String userPhoneNumber = "";
    int userID;
    //flag 0 means get and 1 means post.(By default it is get.)
    public LoginAsync(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                email = (String)params[0];
                password = (String)params[1];


                String link = Endpoints.LOGIN_URL+"?"+Endpoints.KEY_EMAIL+"="+email+"&"+Endpoints.KEY_PASSWORD+"="+password;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        } else{
            try{
                email = (String)params[0];
                password = (String)params[1];


                String link= Endpoints.LOGIN_URL;
                String data  = URLEncoder.encode(Endpoints.KEY_EMAIL, "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_PASSWORD, "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Message.shortToast(context, result);
        if (result.equals("Login Successful")) {
            final String fcm_token = SharedPrefManager.getInstance(context).getDeviceToken();
            final String action = "login";
            //SendTokenToServerAsync test = new SendTokenToServerAsync(context,1);
            //test.execute(email,fcm_token);
            getUserData(email,password);

            new SendTokenToServerAsync(context,1).execute(email,fcm_token,action);
            context.startActivity(new Intent(context,HomeScreenActivity.class));
//            Bundle bundle = new Bundle();
//            if(userEmail != ""){
//                bundle.putString(Endpoints.KEY_BUNDLE_FNAME,userFname);
//                bundle.putString(Endpoints.KEY_BUNDLE_LNAME,userLname);
//                bundle.putString(Endpoints.KEY_BUNDLE_EMAIL,userEmail);
//                bundle.putString(Endpoints.KEY_BUNDLE_EMAIL,userEmail);
//                bundle.putString(Endpoints.KEY_BUNDLE_PASSWORD,userPassword);
//                bundle.putString(Endpoints.KEY_BUNDLE_PHONE_NUMBER,userPhoneNumber);
//                Message.longToast(context,"BUNDLE SET");
//            }
//            Intent intent = new Intent(context,HomeScreenActivity.class);
//            if(userEmail != "" || userEmail != null){
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }else {
//                Message.longToast(context, "Opps!! An error occurred while logging in");
//            }


        }
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
                        Message.longToast(context, "ERROR FROM GET USER DATA: "+error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Endpoints.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            //Message.longToast(context,"USER DETAILS FOR ASYNC: "+result.getJSONObject(0));
            final String userEmail = (String)collegeData.getString(Endpoints.KEY_DB_EMAIL);
            final String userFname = (String)collegeData.getString(Endpoints.KEY_DB_FIRST_NAME);
            final String userLname = (String)collegeData.getString(Endpoints.KEY_DB_LAST_NAME);
            final String userPassword = (String)collegeData.getString(Endpoints.KEY_DB_PASSWORD);
            final String userAge = (String)collegeData.getString(Endpoints.KEY_DB_AGE);
            final String userPhoneNumber = (String)collegeData.getString(Endpoints.KEY_DB_PHONE_NUMBER);
            userID = collegeData.getInt(Endpoints.KEY_DB_USER_ID);
            Message.longToast(context,"USER DETAILS FOR ASYNC: "+collegeData.getString(Endpoints.KEY_DB_EMAIL));

            userInformation = new UserInformation();
            userInformation.setFirstName(userFname);
            userInformation.setLastName(userLname);
            //userInformation.setAge(userAge);
            userInformation.setEmail(userEmail);
            userInformation.setPassword(password);
            userInformation.setPhoneNumber(userPhoneNumber);
            userInformation.save();

//            String test = SharedPrefManager.getInstance(context).getUserDetails(SharedPrefManager.USER_EMAIL_KEY);
//            Message.longToast(context,"USER OFFLINE EMAIL: "+test);
//            SharedPrefManager.getInstance(context).saveUserDetailsOffline(userEmail,userFname,userLname,userPassword,userAge,userPhoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    private class SendTokenToServerAsync extends AsyncTask <String, String, String>{
//        private Context context;
//        private int byGetOrPost = 0;
//        private String fcm_token = "";
//        private String email = "";
//        //String fcm_token = "";
//        //String mac_address = "";
//        //flag 0 means get and 1 means post.(By default it is get.)
//        public SendTokenToServerAsync(Context context, int flag) {
//            this.context = context;
//            byGetOrPost = flag;
//        }
//
//        protected void onPreExecute(){
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(byGetOrPost == 0){ //means by Get Method
//
//                try{
//                    email = params[0];
//                    fcm_token = params[1];
//
//                    String link = Endpoints.URL_SEND_TOKEN_TO_SERVER+"?"+Endpoints.KEY_PHP_FCM_TOKEN+"="+fcm_token+"&"
//                            +Endpoints.KEY_PHP_EMAIL+"="+email;
//                    URL url = new URL(link);
//                    HttpClient client = new DefaultHttpClient();
//                    HttpGet request = new HttpGet();
//                    request.setURI(new URI(link));
//                    HttpResponse response = client.execute(request);
//                    BufferedReader in = new BufferedReader(new
//                            InputStreamReader(response.getEntity().getContent()));
//
//                    StringBuffer sb = new StringBuffer("");
//                    String line="";
//
//                    while ((line = in.readLine()) != null) {
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//                    return sb.toString();
//                } catch(Exception e){
//                    return new String("Exception: " + e.getMessage());
//                }
//            } else{
//                try{
//                    email = params[0];
//                    fcm_token = params[1];
//
//                    String link= Endpoints.URL_SEND_TOKEN_TO_SERVER;
//                    String data = URLEncoder.encode(Endpoints.KEY_PHP_EMAIL, "UTF-8") + "=" +
//                            URLEncoder.encode(email, "UTF-8");
//
//                    data  += URLEncoder.encode(Endpoints.KEY_PHP_FCM_TOKEN, "UTF-8") + "=" +
//                            URLEncoder.encode(fcm_token, "UTF-8");
//
//                    URL url = new URL(link);
//                    URLConnection conn = url.openConnection();
//
//                    conn.setDoOutput(true);
//                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                    wr.write( data );
//                    wr.flush();
//
//                    BufferedReader reader = new BufferedReader(new
//                            InputStreamReader(conn.getInputStream()));
//
//                    StringBuilder sb = new StringBuilder();
//                    String line = null;
//
//                    // Read Server Response
//                    while((line = reader.readLine()) != null) {
//                        sb.append(line);
//                        break;
//                    }
//
//                    return sb.toString();
//                } catch(Exception e){
//                    return new String("Exception: " + e.getMessage());
//                }
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//
//            if(result.equals("Successful")){
//                Message.shortToast(context,"Token sent successfully");
//                context.startActivity(new Intent(context, HomeScreenActivity.class));
//            }else{
//                Message.shortToast(context,result);
//            }
//        }
//    }
//        //this.statusField.setText("Login Successful");
//        //this.roleField.setText(result);
    }

