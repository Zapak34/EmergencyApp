package com.nightvisionmedia.emergencyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.async.ForgetPasswordAsync;

public class ForgetPasswordScreenActivity extends AppCompatActivity {
    private EditText edtForgetPassword;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_screen);

        setupWidgets();
        setupListeners();
    }

    private void setupListeners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtForgetPassword.getText().toString();
                new ForgetPasswordAsync(ForgetPasswordScreenActivity.this,0).execute(email);
            }
        });
    }

    private void setupWidgets() {
        edtForgetPassword = (EditText)findViewById(R.id.edtEmailForgotPass);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
    }
}
