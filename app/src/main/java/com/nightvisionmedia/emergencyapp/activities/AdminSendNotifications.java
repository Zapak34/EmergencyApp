package com.nightvisionmedia.emergencyapp.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.MyVolley;
import com.nightvisionmedia.emergencyapp.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminSendNotifications extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ArrayList<String> devices;
    private TextView tvNumberOfTokens;
    private EditText edtTitle, edtMessage, edtImageURL;
    private Button btnSend;
    private int showSent = 0, showSent1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_notifications);

        setUpWidgets();
        setupListeners();
    }

    private void setupListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < devices.size(); i++){
                    sendSinglePush(devices.get(i));
                    if(i == 0){
                        showSent = 0;
                        showSent1 = 0;
                        addJob();
                    }
                }
            }
        });
    }

    private void addJob(){


        final String title = edtTitle.getText().toString().trim().replace("'","~");
        final String message = edtMessage.getText().toString().trim().replace("'","~");
        final String image = edtImageURL.getText().toString().trim().replace("'","~");
        //TODO OMAR CHANGES
//        final String emails = email;

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AdminSendNotifications.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(showSent == 0){
                    //showGenericDialog("FINISHED... ",s);
                    Message.longToast(AdminSendNotifications.this,s);
                    showSent = 1;
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Endpoints.KEY_PHP_TITLE,title);
                params.put(Endpoints.KEY_PHP_MESSAGE,message);
                params.put(Endpoints.KEY_PHP_IMAGE,image);
                params.put(Endpoints.KEY_PHP_TYPE,"job");

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Endpoints.URL_ADD_POST, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private void setUpWidgets() {
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtMessage = (EditText)findViewById(R.id.edtMessage);
        edtImageURL = (EditText)findViewById(R.id.edtImageURL);
        btnSend = (Button)findViewById(R.id.btnSend);
        tvNumberOfTokens = (TextView)findViewById(R.id.tvNumberOfTokens);
        devices = new ArrayList<>();
        loadRegisteredDevices();
    }


    public void loadRegisteredDevices() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Devices...");
        progressDialog.show();
//        connectionProblem();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Endpoints.URL_FETCH_DEVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONArray jsonDevices = obj.getJSONArray("devices");

                                for (int i = 0; i < jsonDevices.length(); i++) {
                                    JSONObject d = jsonDevices.getJSONObject(i);
                                    if(devices.contains(d.getString("email"))){
                                        devices.remove(d.getString("email"));
                                    }
                                    devices.add(d.getString("email"));

                                }
                                tvNumberOfTokens.setText("Number Of Accounts: "+String.valueOf(devices.size()));
                                progressDialog.dismiss();
                            }else{
                                Message.longToast(AdminSendNotifications.this,"Oops! Something went wrong, connection took too long to respond. Please  Try Refreshing...");
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };
        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void sendSinglePush(String emails) {
        final String title = edtTitle.getText().toString();
        final String message = edtMessage.getText().toString();
        final String image = edtImageURL.getText().toString();
        final String email = emails;
        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Message.longToast(AdminSendNotifications.this,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Message.longToast(AdminSendNotifications.this,error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);


                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("email", email);
                params.put("type","job");
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
}
