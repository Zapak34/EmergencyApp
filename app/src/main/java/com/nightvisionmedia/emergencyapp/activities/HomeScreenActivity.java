package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.AlertsRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.adapters.HappeningsRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.MainRecyclerViewRowClass;
import com.nightvisionmedia.emergencyapp.sugar_models.AlertsFavorites;
import com.nightvisionmedia.emergencyapp.utils.App;
import com.nightvisionmedia.emergencyapp.utils.BottomNavigationHelper;
import com.nightvisionmedia.emergencyapp.utils.DialogHandler;
import com.nightvisionmedia.emergencyapp.utils.Message;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeScreenActivity extends AppCompatActivity {
    private TextView tvWhatsNewMarquee, tvHomeArticleTitle, tvHomeArticleMessage;
    private Button btnAdminScreen;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private RecyclerView latestAlertsRecyclerView, latestHappeningsRecyclerView;
    private List<MainRecyclerViewRowClass> alerts_data_list, happenings_data_list;
    private HappeningsRecyclerViewAdapter happeningsAdapter;
    private AlertsRecyclerViewAdapter alertsAdapter;
    private LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    private String articleTitle = "", articleMessage = "";
    private int logout = 0, article_id;
    private boolean hasInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//        getBundle();
        setupWidgets();
        setupListeners();

        latestAlertsRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewLatestAlerts);
        latestHappeningsRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewLatestHappenings);
        happenings_data_list = new ArrayList<>();
        alerts_data_list = new ArrayList<>();

        hasInternet = isNetworkAvailable();
        if(!hasInternet){
            Message.longToast(HomeScreenActivity.this, "There is no internet connection, you can go to offline mode from settings...");
        }else {
            load_data_from_server_alerts();
            load_data_from_server_happenings();
            load_data_from_server_article();

        }

        linearLayoutManager = new LinearLayoutManager(HomeScreenActivity.this);
        linearLayoutManager1 = new LinearLayoutManager(HomeScreenActivity.this);
        latestAlertsRecyclerView.setLayoutManager(linearLayoutManager);
        latestHappeningsRecyclerView.setLayoutManager(linearLayoutManager1);
        alertsAdapter = new AlertsRecyclerViewAdapter(this,alerts_data_list);
        happeningsAdapter = new HappeningsRecyclerViewAdapter(this,happenings_data_list);
        latestAlertsRecyclerView.setAdapter(alertsAdapter);
        latestHappeningsRecyclerView.setAdapter(happeningsAdapter);

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
    private void load_data_from_server_article() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Endpoints.URL_GET_HOME_PAGE_ARTICLE)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());


                        JSONObject object = array.getJSONObject(0);
                        article_id = object.getInt("article_id");
                        articleTitle = object.getString("title");
                        articleMessage = object.getString("message");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(articleTitle.equals("") || articleMessage.equals("")){
                    tvHomeArticleTitle.setVisibility(View.GONE);
                    tvHomeArticleMessage.setVisibility(View.GONE);
                }else{
                    tvHomeArticleTitle.setText(articleTitle);
                    tvHomeArticleMessage.setText(articleMessage);
                    tvHomeArticleTitle.setVisibility(View.VISIBLE);
                    tvHomeArticleMessage.setVisibility(View.VISIBLE);
                }
            }
        };
        task.execute();
    }

    private void load_data_from_server_alerts() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Endpoints.URL_GET_ALERTS)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < 1; i++){
                        JSONObject object = array.getJSONObject(i);
                        MainRecyclerViewRowClass data = new MainRecyclerViewRowClass(object.getInt("alert_id"),object.getString("title"),object.getString("message"),object.getString("image"),object.getString("time_posted"));
                        //Message.shortToast(AlertsScreenActivity.this,array.getJSONArray(i).toString());
                        alerts_data_list.add(data);
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
                alertsAdapter.notifyDataSetChanged();
            }
        };
        task.execute();
    }



    private void load_data_from_server_happenings() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Endpoints.URL_GET_HAPPENINGS)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < 1; i++){
                        JSONObject object = array.getJSONObject(i);
                        MainRecyclerViewRowClass data = new MainRecyclerViewRowClass(object.getInt("happen_id"),object.getString("title"),object.getString("message"),object.getString("image"),object.getString("time_posted"));
                        //Message.shortToast(AlertsScreenActivity.this,array.getJSONArray(i).toString());
                        happenings_data_list.add(data);
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
                happeningsAdapter.notifyDataSetChanged();
            }
        };
        task.execute();
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
        tvHomeArticleTitle = (TextView)findViewById(R.id.tvArticleTitle);
        tvHomeArticleMessage = (TextView)findViewById(R.id.tvArticleMessage);
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
        tvHomeArticleTitle.setSelected(true);
        tvHomeArticleTitle.requestFocus();
        //Message.longToast(HomeScreenActivity.this, SharedPrefManager.getInstance(HomeScreenActivity.this).getUserDetails(SharedPrefManager.USER_EMAIL_KEY));

    }


    @Override
    protected void onResume() {
        super.onResume();
        alertsAdapter.notifyDataSetChanged();
        happeningsAdapter.notifyDataSetChanged();
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
