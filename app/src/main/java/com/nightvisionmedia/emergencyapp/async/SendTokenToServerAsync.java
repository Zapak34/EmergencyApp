package com.nightvisionmedia.emergencyapp.async;

import android.content.Context;
import android.content.Intent;
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
 * Created by Omar (GAZAMAN) Myers on 6/23/2017.
 */

public class SendTokenToServerAsync extends AsyncTask <String, String, String>{
    private Context context;
    private int byGetOrPost = 0;
    private String fcm_token = "";
    private String email = "";
    private String action = "";
    //String fcm_token = "";
    //String mac_address = "";
    //flag 0 means get and 1 means post.(By default it is get.)
    public SendTokenToServerAsync(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... params) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                email = params[0];
                fcm_token = params[1];
                action = params[2];

                String link = Endpoints.URL_SEND_TOKEN_TO_SERVER+"?"+Endpoints.KEY_PHP_FCM_TOKEN+"="+fcm_token+"&"
                        +Endpoints.KEY_PHP_EMAIL+"="+email+"&"+Endpoints.KEY_PHP_ACTION+"="+action;
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
                email = params[0];
                fcm_token = params[1];
                action = params[2];

                String link= Endpoints.URL_SEND_TOKEN_TO_SERVER;
                String data = URLEncoder.encode(Endpoints.KEY_PHP_EMAIL, "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");

                data  += "&" +URLEncoder.encode(Endpoints.KEY_PHP_FCM_TOKEN, "UTF-8") + "=" +
                        URLEncoder.encode(fcm_token, "UTF-8");

                data  += "&" +URLEncoder.encode(Endpoints.KEY_PHP_ACTION, "UTF-8") + "=" +
                        URLEncoder.encode(action, "UTF-8");

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

        if(result.equals("Successfully")){
            Message.shortToast(context,"Token successfully sent");
        }else{
            Message.shortToast(context,result);
        }
    }
}
