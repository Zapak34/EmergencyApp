package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.async.RegisterAsync;
import com.nightvisionmedia.emergencyapp.async.SendTokenToServerAsync;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.utils.DialogHandler;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreenActivity extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmailAddress;
    EditText edtPassword;
    EditText edtConfirmPassword;
    EditText edtPhoneNumber;
    RadioGroup radGrpAges;
    Button btnRegister;

    private String USER_MAC_ADDRESS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        setupWidgets();
        setupListeners();

        boolean hasInternet = isNetworkAvailable();
        if(!hasInternet){
            Message.longToast(RegisterScreenActivity.this, "There is no internet connection...");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void setupListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
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
                final String fcm_token = SharedPrefManager.getInstance(RegisterScreenActivity.this).getDeviceToken();
                final String action = "register";
                //Message.longToast(RegisterScreenActivity.this,"This is the token: "+fcm_token);

                if(fcm_token != null){
                    boolean isValid = checkUserInfo(fname,lname,age,email,password,confirmPass,phone);
                    if(isValid && fcm_token != ""){
                        new RegisterAsync(RegisterScreenActivity.this,1).execute(fname,lname,age,email,password,phone);
                        new SendTokenToServerAsync(RegisterScreenActivity.this,1).execute(email,fcm_token,action);
                    }
                }else{
                    showErrorMessage("Oops!! Something went wrong please reinstall this application");
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
            String fnameSpaceCheck = String.valueOf(fname.charAt(0));
            String lnameSpaceCheck = String.valueOf(lname.charAt(0));
            if(fnameSpaceCheck.equals(" ") || lnameSpaceCheck.equals(" ")){
                isValid = false;
                if(fname.charAt(0) == ' '){
                    edtFirstName.setError("First Name must not start with any spaces");
                }

                if(lname.charAt(0) == ' '){
                    edtLastName.setError("Last Name must not start with any spaces");
                }

            }else{
                isValid = true;
            }
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
        btnRegister = (Button)findViewById(R.id.btnRegister);


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
