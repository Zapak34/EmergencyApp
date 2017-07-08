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
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
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

public class RefreshServerTokenAsync extends AsyncTask <String, String, String>{
    private Context context;
    private int byGetOrPost = 0;
    private String publicEmail = "";
    private String publicPassword = "";


    //flag 0 means get and 1 means post.(By default it is get.)
    public RefreshServerTokenAsync(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                String email = (String)params[0];
                String password = (String)params[1];

                publicEmail = (String)params[0];
                publicPassword = (String)params[1];

                String link = Endpoints.URL_REFRESH_SERVER_TOKEN+"?"+Endpoints.KEY_EMAIL+"="+email+"&"+Endpoints.KEY_PASSWORD+"="+password;

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
                String email = (String)params[0];
                String password = (String)params[1];

                publicEmail = (String)params[0];
                publicPassword = (String)params[1];

                String link= Endpoints.URL_REFRESH_SERVER_TOKEN;
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
            context.startActivity(new Intent(context, HomeScreenActivity.class));
        }
    }
    //this.statusField.setText("Login Successful");
    //this.roleField.setText(result);
}

