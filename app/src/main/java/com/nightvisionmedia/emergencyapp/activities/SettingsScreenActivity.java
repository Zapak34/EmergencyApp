package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.utils.App;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

public class SettingsScreenActivity extends AppCompatActivity {
    private CheckBox chkBoxAutomaticLogin;
    private TextView tvUpdateAccountInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        setupWidgets();
        setupListeners();
    }

    private void setupListeners() {
        final int isAutoLogin = SharedPrefManager.getInstance(SettingsScreenActivity.this).getAutomaticLogin();
        if(isAutoLogin == 1){
            chkBoxAutomaticLogin.setChecked(true);
            chkBoxAutomaticLogin.setText("Automatic Login (ON)");
        }else{
            chkBoxAutomaticLogin.setChecked(false);
            chkBoxAutomaticLogin.setText("Automatic Login (OFF)");
        }

        chkBoxAutomaticLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkBoxAutomaticLogin.isChecked()){
                    SharedPrefManager.getInstance(SettingsScreenActivity.this).saveAutomaticLogin(1);
                    Message.shortToast(SettingsScreenActivity.this,"Automatic Login Is Turned On...");
                    chkBoxAutomaticLogin.setText("Automatic Login (ON)");
                }else{
                    SharedPrefManager.getInstance(SettingsScreenActivity.this).saveAutomaticLogin(0);
                    Message.shortToast(SettingsScreenActivity.this,"Automatic Login Is Turned Off...");
                    chkBoxAutomaticLogin.setText("Automatic Login (OFF)");
                }
            }
        });


        tvUpdateAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsScreenActivity.this, UpdateAccountScreenActivity.class));
            }
        });

    }

    private void setupWidgets() {
        chkBoxAutomaticLogin = (CheckBox)findViewById(R.id.chkBoxAutomaticLogin);
        tvUpdateAccountInfo = (TextView)findViewById(R.id.tvUpdateUserInfo);
    }
}
