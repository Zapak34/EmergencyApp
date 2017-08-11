package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private CheckBox chkBoxAutomaticLogin, chkBoxOfflineMode;
    private TextView tvUpdateAccountInfo;
    private boolean hasInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        setupWidgets();
        setupListeners();
        hasInternet = isNetworkAvailable();
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

        final boolean isOffline = SharedPrefManager.getInstance(SettingsScreenActivity.this).getOfflineMode();
        if(isOffline){
            chkBoxOfflineMode.setChecked(true);
            chkBoxOfflineMode.setText("Offline Mode (ON)");

        }else{
            chkBoxOfflineMode.setChecked(false);
            chkBoxOfflineMode.setText("Offline Mode (OFF)");

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

        chkBoxOfflineMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkBoxOfflineMode.isChecked()){
                    SharedPrefManager.getInstance(SettingsScreenActivity.this).setOfflineMode(true);
                    Message.shortToast(SettingsScreenActivity.this,"Offline Mode Is Turned On...");
                    chkBoxOfflineMode.setText("Offline Mode (ON)");
                }else{
                    SharedPrefManager.getInstance(SettingsScreenActivity.this).setOfflineMode(false);
                    Message.shortToast(SettingsScreenActivity.this,"Offline Mode Is Turned Off...");
                    chkBoxOfflineMode.setText("Offline Mode (OFF)");
                }
            }
        });

        tvUpdateAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasInternet = isNetworkAvailable();
                if(hasInternet && !SharedPrefManager.getInstance(SettingsScreenActivity.this).getOfflineMode()){
                    startActivity(new Intent(SettingsScreenActivity.this, UpdateAccountScreenActivity.class));
                }else{
                    Message.shortToast(SettingsScreenActivity.this,"There is either no internet or you are in offline mode...");
                }

            }
        });

    }

    private void setupWidgets() {
        chkBoxAutomaticLogin = (CheckBox)findViewById(R.id.chkBoxAutomaticLogin);
        chkBoxOfflineMode = (CheckBox)findViewById(R.id.chkBoxOfflineMode);
        tvUpdateAccountInfo = (TextView)findViewById(R.id.tvUpdateUserInfo);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
