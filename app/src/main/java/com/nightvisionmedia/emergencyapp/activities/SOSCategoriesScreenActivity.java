package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.AlertsRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.adapters.SOSCategoriesRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.SOSTitleRecyclerViewRowClass;
import com.nightvisionmedia.emergencyapp.utils.BottomNavigationHelper;
import com.nightvisionmedia.emergencyapp.utils.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SOSCategoriesScreenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private SOSCategoriesRecyclerViewAdapter adapter;
    private List<SOSTitleRecyclerViewRowClass> data_list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ineed_help);

        recyclerView = (RecyclerView)findViewById(R.id.sosCategoryRecyclerView);
        data_list = new ArrayList<>();
        boolean hasInternet = isNetworkAvailable();
        if(!hasInternet){
            Message.longToast(SOSCategoriesScreenActivity.this, "There is no internet connection...");
        }else {
            load_data_from_server();
        }


//        toolbar = (Toolbar)findViewById(R.id.alertsToolbar);
//        toolbar.setTitle("I NEED HELP");
//        setSupportActionBar(toolbar);


        //gridLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager = new LinearLayoutManager(SOSCategoriesScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SOSCategoriesRecyclerViewAdapter(this,data_list);
        recyclerView.setAdapter(adapter);



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        Intent intent = new Intent(SOSCategoriesScreenActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());                        break;
                    case R.id.ic_alerts:
                        Intent intent0 = new Intent(SOSCategoriesScreenActivity.this, AlertsScreenActivity.class);
                        startActivity(intent0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_need_help:
                        break;
                    case R.id.ic_happenings:
                        Intent intent1 = new Intent(SOSCategoriesScreenActivity.this, HappeningsActivity.class);
                        startActivity(intent1);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_more:
                        Intent intent3 = new Intent(SOSCategoriesScreenActivity.this, MoreScreenActivity.class);
                        startActivity(intent3);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                }
                return false;
            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void load_data_from_server() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Endpoints.URL_GET_SOS_CATEGORIES)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        SOSTitleRecyclerViewRowClass data = new SOSTitleRecyclerViewRowClass(object.getInt(Endpoints.KEY_BUNDLE_CATEGORY_ID),object.getString("title"),object.getString("image"));
                        //Message.shortToast(AlertsScreenActivity.this,array.getJSONArray(i).toString());
                        data_list.add(data);
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
                adapter.notifyDataSetChanged();
            }
        };
        task.execute();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SOSCategoriesScreenActivity.this, HomeScreenActivity.class));
    }

}
