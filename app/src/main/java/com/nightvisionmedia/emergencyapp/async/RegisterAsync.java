package com.nightvisionmedia.emergencyapp.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.nightvisionmedia.emergencyapp.activities.HomeScreenActivity;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.utils.App;
import com.nightvisionmedia.emergencyapp.utils.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

public class RegisterAsync extends AsyncTask <String, String, String>{
    private Context context;
    private int byGetOrPost = 0;
    String fname = "";
    String lname ="";
    String age = "";
    String email = "";
    String password = "";
    String phone_number = "";
    //String fcm_token = "";
    //String mac_address = "";
    //flag 0 means get and 1 means post.(By default it is get.)
    public RegisterAsync(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                fname = params[0];
                lname = params[1];
                age = params[2];
                email = params[3];
                password = params[4];
                phone_number = params[5];
                //fcm_token = params[6];
                //mac_address = params[7];
                String link = Endpoints.REGISTRATION_URL+"?"+Endpoints.KEY_FIRST_NAME+"="+fname+"&"+Endpoints.KEY_LAST_NAME+"="+lname+"&"+Endpoints.KEY_AGE+"="+age
                        +"&"+Endpoints.KEY_EMAIL+"="+email+"&"+Endpoints.KEY_PASSWORD+"="+password+"&"+Endpoints.KEY_PHONE_NUMBER+"="+phone_number;
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
                fname = params[0];
                lname = params[1];
                age = params[2];
                email = params[3];
                password = params[4];
                phone_number = params[5];
                //fcm_token = params[6];
                //mac_address = params[7];

                String link="http://nightvisionmediaapp.000webhostapp.com/registerUser.php";
                String data  = URLEncoder.encode(Endpoints.KEY_FIRST_NAME, "UTF-8") + "=" +
                        URLEncoder.encode(fname, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_LAST_NAME, "UTF-8") + "=" +
                        URLEncoder.encode(lname, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_AGE, "UTF-8") + "=" +
                        URLEncoder.encode(age, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_EMAIL, "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_PASSWORD, "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode(Endpoints.KEY_PHONE_NUMBER, "UTF-8") + "=" +
                        URLEncoder.encode(phone_number, "UTF-8");

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
    protected void onPostExecute(String result){
        Message.shortToast(context,result);

        if(result.equals("Registration Successful")){
            App.finishActivity((AppCompatActivity) context);
        }
        //this.statusField.setText("Login Successful");
        //this.roleField.setText(result);
    }
}
