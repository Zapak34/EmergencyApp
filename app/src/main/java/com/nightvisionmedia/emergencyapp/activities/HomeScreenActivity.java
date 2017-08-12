package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.sugar_models.AlertsFavorites;
import com.nightvisionmedia.emergencyapp.utils.App;
import com.nightvisionmedia.emergencyapp.utils.BottomNavigationHelper;
import com.nightvisionmedia.emergencyapp.utils.DialogHandler;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

public class HomeScreenActivity extends AppCompatActivity {
    private TextView tvWhatsNewMarquee;
    private Button btnAdminScreen;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private int logout = 0;
    private boolean hasInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//        getBundle();
        setupWidgets();
        setupListeners();
        hasInternet = isNetworkAvailable();
        if(!SharedPrefManager.getInstance(HomeScreenActivity.this).getOfflineMode()){
            final int isFirstTimeLogin = SharedPrefManager.getInstance(HomeScreenActivity.this).getLoginScreenSaveAutoLoginShown();
            if(isFirstTimeLogin == -1){
                DialogHandler.SaveAutomaticLogin saveAutomaticLogin =new DialogHandler.SaveAutomaticLogin();
                saveAutomaticLogin.show(getFragmentManager(), "No Name");
            }
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


//    private void getBundle() {
//        Intent intent = getIntent();
//        final  String userFname = intent.getStringExtra(Endpoints.KEY_BUNDLE_FNAME);
//        final  String userLname = intent.getStringExtra(Endpoints.KEY_BUNDLE_LNAME);
//        final  String userAge = intent.getStringExtra(Endpoints.KEY_BUNDLE_AGE);
//        final  String userEmail = intent.getStringExtra(Endpoints.KEY_BUNDLE_EMAIL);
//        final  String userPassword = intent.getStringExtra(Endpoints.KEY_BUNDLE_PASSWORD);
//        final  String userPhoneNumber = intent.getStringExtra(Endpoints.KEY_BUNDLE_PHONE_NUMBER);
//        SharedPrefManager.getInstance(HomeScreenActivity.this).saveUserDetailsOffline(userEmail,userFname,userLname,userPassword,userAge,userPhoneNumber);
//        Message.longToast(HomeScreenActivity.this, "USER INFO SAVED OFFLINE");
//    }

    private void setupListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                hasInternet = isNetworkAvailable();
                switch(item.getItemId()){
                    case R.id.ic_home:
                        App.refreshActivity(HomeScreenActivity.this);
                        break;
                    case R.id.ic_alerts:
                        Intent intent1 = new Intent(HomeScreenActivity.this, AlertsScreenActivity.class);
                        if(!SharedPrefManager.getInstance(HomeScreenActivity.this).getOfflineMode()){
                            startActivity(intent1);
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }else{
                            startActivity(new Intent(HomeScreenActivity.this, AlertsFavScreenActivity.class));
                            Message.longToast(HomeScreenActivity.this,"You in offline mode so we'll open the the favorites for you");
                        }


                        break;
                    case R.id.ic_need_help:
                        Intent intent2 = new Intent(HomeScreenActivity.this, SOSCategoriesScreenActivity.class);
                        if(!SharedPrefManager.getInstance(HomeScreenActivity.this).getOfflineMode()){
                            startActivity(intent2);
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }else{
                            if(SharedPrefManager.getInstance(HomeScreenActivity.this).getOfflineMode()){
                                Message.shortToast(HomeScreenActivity.this,"You are in offline mode");
                            }else if(!hasInternet){
                                Message.shortToast(HomeScreenActivity.this,"There may not be any internet connection");
                            }

                            startActivity(new Intent(HomeScreenActivity.this, CategoryInfoFavScreenActivity.class));
                        }

                        break;
                    case R.id.ic_happenings:
                        Intent intent3 = new Intent(HomeScreenActivity.this, HappeningsActivity.class);
                        if(!SharedPrefManager.getInstance(HomeScreenActivity.this).getOfflineMode()){
                            startActivity(intent3);
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }else{
                            startActivity(new Intent(HomeScreenActivity.this, HappningsFavScreenActivity.class));
                            Message.longToast(HomeScreenActivity.this,"You in offline mode so we'll open the the favorites for you");
                        }

                        break;
                    case R.id.ic_more:
                        Intent intent4 = new Intent(HomeScreenActivity.this, MoreScreenActivity.class);
                        startActivity(intent4);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                }
                return false;
            }
        });


        btnAdminScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this,AdminSendNotifications.class));
            }
        });
    }

    private void setupWidgets() {
        tvWhatsNewMarquee = (TextView)findViewById(R.id.tvWhatsNewMarquee);
        btnAdminScreen = (Button)findViewById(R.id.btnAdminScreen);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
//        toolbar = (Toolbar)findViewById(R.id.homeToolbar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(0);
        menuItem.setChecked(true);

//        toolbar.setTitle("HOME");
        //String test = SharedPrefManager.getInstance(this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY);
        //Message.longToast(this,test);
        tvWhatsNewMarquee.setSelected(true);
        tvWhatsNewMarquee.requestFocus();
        //Message.longToast(HomeScreenActivity.this, SharedPrefManager.getInstance(HomeScreenActivity.this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY));

    }

    @Override
    public void onBackPressed() {
        logout = logout + 1;
        if(logout == 1){
            Message.longToast(HomeScreenActivity.this, "Press Again To Logout...");
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logout = 0;
            }
        },4000);
        if(logout == 2){
            logout = 0;
            SharedPrefManager.getInstance(HomeScreenActivity.this).saveAutomaticLogin(0);
            finish();
            super.onBackPressed();
        }
    }
}
