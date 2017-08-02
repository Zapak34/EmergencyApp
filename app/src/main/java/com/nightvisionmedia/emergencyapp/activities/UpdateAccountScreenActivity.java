package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.async.RegisterAsync;
import com.nightvisionmedia.emergencyapp.async.SendTokenToServerAsync;
import com.nightvisionmedia.emergencyapp.async.UpdateAccountAsync;
import com.nightvisionmedia.emergencyapp.constants.CategoryIDs;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.MainRecyclerViewRowClass;
import com.nightvisionmedia.emergencyapp.custom_models.UserAccount;
import com.nightvisionmedia.emergencyapp.utils.DialogHandler;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class UpdateAccountScreenActivity extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmailAddress;
    EditText edtPassword;
    EditText edtConfirmPassword;
    EditText edtPhoneNumber;
    int user_id;
    String fname, lname, age, email_address, password, phone_number;
    RadioGroup radGrpAges;
    Button btnUpdate;
//    UserAccount userData;


//    private String userEmail, userPassword, userFname,userMname,userLname,userCellphone, userHomephone,userAddress,userDOB,userAge,userParish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account_screen);
        setupWidgets();
        setupListeners();
        final  String email = SharedPrefManager.getInstance(UpdateAccountScreenActivity.this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY);
        final  String pass = SharedPrefManager.getInstance(UpdateAccountScreenActivity.this).getUserDetails(SharedPrefManager.USER_PASSWORD_KEY);
        password = pass;
        load_data_from_server(email,pass);
    }

    private void load_data_from_server(final String email, final String password) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Endpoints.GET_ALL_USER_INFO_URL_1+email+"&password="+password)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        user_id = object.getInt("user_id");
                        fname = object.getString(Endpoints.KEY_DB_FIRST_NAME);
                        lname = object.getString(Endpoints.KEY_DB_LAST_NAME);
                        email_address = object.getString(Endpoints.KEY_DB_EMAIL);
                        age = object.getString(Endpoints.KEY_DB_AGE);
                        phone_number= object.getString(Endpoints.KEY_DB_PHONE_NUMBER);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                Message.longToast(UpdateAccountScreenActivity.this, "USER INFO ONLINE: "+userData.getEmail());
                    edtFirstName.setText(fname);
                    edtLastName.setText(lname);
                    if(age.equals(Endpoints.AGE_RANGE_1)){
                        radGrpAges.check(R.id.radBtnRange1);
                    }else if(age.equals(Endpoints.AGE_RANGE_2)){
                        radGrpAges.check(R.id.radBtnRange2);
                    }else if(age.equals(Endpoints.AGE_RANGE_3)){
                        radGrpAges.check(R.id.radBtnRange3);
                    }
                    edtEmailAddress.setText(email_address);
                    edtPassword.setText(SharedPrefManager.getInstance(UpdateAccountScreenActivity.this).getUserDetails(SharedPrefManager.USER_PASSWORD_KEY));
                    edtConfirmPassword.setText(SharedPrefManager.getInstance(UpdateAccountScreenActivity.this).getUserDetails(SharedPrefManager.USER_PASSWORD_KEY));
                    edtPhoneNumber.setText(phone_number);
//                }else{
//                    Message.shortToast(UpdateAccountScreenActivity.this,"Opps!! Error occurred while getting account details");
//                }

            }
        };
        task.execute();
    }



    private void setupListeners() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = edtFirstName.getText().toString();
                final String lname = edtLastName.getText().toString();
                String age = "";
                switch (radGrpAges.getCheckedRadioButtonId()){
                    case R.id.radBtnRange1:
                        age = Endpoints.AGE_RANGE_1;
                        break;
                    case R.id.radBtnRange2:
                        age = Endpoints.AGE_RANGE_2;
                        break;
                    case R.id.radBtnRange3:
                        age = Endpoints.AGE_RANGE_3;
                        break;
                    default:

                }

                final String email = edtEmailAddress.getText().toString();
                final String password = edtPassword.getText().toString();
                final String confirmPass = edtConfirmPassword.getText().toString();
                final String phone = edtPhoneNumber.getText().toString();
                //final String action = "register";
                //Message.longToast(RegisterScreenActivity.this,"This is the token: "+fcm_token);
                boolean isValid = checkUserInfo(fname,lname,age,email,password,confirmPass,phone);
                if(isValid) {
                    new UpdateAccountAsync(UpdateAccountScreenActivity.this, 1).execute(fname, lname, age, email, password, phone);
                }
            }
            });
    }

    private boolean checkUserInfo(String fname, String lname, String age, String email, String password, String confirmPass, String phone) {
        boolean isValid;
        //Pattern namePatterns = Pattern.compile("[a-zA-Z-']*");
//     Pattern phonePattern = Pattern.compile("");
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        //Matcher matcher = namePatterns.matcher(fname);
//     Matcher phoneMatcher = phone.matcher(cell);
        Matcher emailMatcher = emailPattern.matcher(email);
        if(!fname.equals("") && !lname.equals("") && !age.equals("") && !email.equals("") && !password.equals("") && password.equals(confirmPass) && emailMatcher.matches()){
            isValid = true;
        }else{
            isValid = false;
            if(fname.isEmpty()){
                edtFirstName.setError("Please enter your first name");
                //showErrorMessage("Please enter your first name");
            }else{
                edtFirstName.setError(null);
            }

            if(lname.isEmpty()){
                edtLastName.setError("Please enter your last name");
                //showErrorMessage("Please enter your first name");
            }else{
                edtLastName.setError(null);
            }

            if(age.isEmpty()){
                showErrorMessage("Please select a age range");
                //showErrorMessage("Please enter your first name");
            }

            if(email.isEmpty()){
                edtEmailAddress.setError("Please enter your email address, Example: example@email.com");
                //showErrorMessage("Please enter your first name");
            }else if(!emailMatcher.matches()){
                edtEmailAddress.setError("Invalid email address format, Example: example@email.com");
            }else{
                edtEmailAddress.setError(null);
            }

            if(password.isEmpty()){
                edtPassword.setError("Please enter a password");
                //showErrorMessage("Please enter your first name");
            }else{
                edtPassword.setError(null);
            }

            if(confirmPass.isEmpty()){
                edtConfirmPassword.setError("Please re-enter your password");
                //showErrorMessage("Please enter your first name");
            }else if(!confirmPass.equals(password)){
                edtConfirmPassword.setError("Passwords don't match");
            }else{
                edtConfirmPassword.setError(null);
            }
        }

        return isValid;
    }

    private void setupWidgets() {
        edtFirstName = (EditText)findViewById(R.id.edtFirstName);
        edtLastName = (EditText)findViewById(R.id.edtLastName);
        edtEmailAddress = (EditText)findViewById(R.id.edtEmailAddress);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)findViewById(R.id.edtConfirmPassword);
        edtPhoneNumber = (EditText)findViewById(R.id.edtPhoneNumber);
        radGrpAges = (RadioGroup)findViewById(R.id.radGrpAges);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);


    }

    private void showErrorMessage(String message){
        DialogHandler.ErrorInForm errorInForm = new DialogHandler.ErrorInForm();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        errorInForm.setArguments(bundle);
        errorInForm.show(getFragmentManager(), "No Name");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
