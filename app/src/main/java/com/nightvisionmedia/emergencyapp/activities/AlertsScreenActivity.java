package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.AlertsRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.AlertsRecyclerRowClass;
import com.nightvisionmedia.emergencyapp.utils.App;
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

public class AlertsScreenActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private AlertsRecyclerViewAdapter adapter;
    private List<AlertsRecyclerRowClass> data_list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_screen);

        recyclerView = (RecyclerView)findViewById(R.id.alertsRecyclerView);
        data_list = new ArrayList<>();
        load_data_from_server();


        toolbar = (Toolbar)findViewById(R.id.alertsToolbar);
        toolbar.setTitle("ALERTS");
        setSupportActionBar(toolbar);


        //gridLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager = new LinearLayoutManager(AlertsScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AlertsRecyclerViewAdapter(this,data_list);
        recyclerView.setAdapter(adapter);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if( linearLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1){
//                    load_data_from_server(data_list.get(data_list.size()-1).getId());
//                }
//            }
//        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        Intent intent0 = new Intent(AlertsScreenActivity.this, HomeScreenActivity.class);
                        startActivity(intent0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_alerts:
                        App.refreshActivity(AlertsScreenActivity.this);
                        break;
                    case R.id.ic_need_help:
                        Intent intent1 = new Intent(AlertsScreenActivity.this, INeedHelpActivity.class);
                        startActivity(intent1);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_happenings:
                        Intent intent2 = new Intent(AlertsScreenActivity.this, HappeningsActivity.class);
                        startActivity(intent2);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_more:
                        Intent intent3 = new Intent(AlertsScreenActivity.this, MoreScreenActivity.class);
                        startActivity(intent3);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alerts_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        //MenuItem menuItem = menu.findItem(R.id.alertsSearch);

        switch (item.getItemId()){
            case R.id.alertsRefresh:
                load_data_from_server();
                Message.shortToast(AlertsScreenActivity.this, "Alert List Refreshed");
                break;
            case R.id.alertsSearch:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(AlertsScreenActivity.this);
                break;
        }
        return true;
    }

    private void load_data_from_server() {
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

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        AlertsRecyclerRowClass data = new AlertsRecyclerRowClass(object.getInt("alert_id"),object.getString("title"),object.getString("message"),object.getString("image"),object.getString("time_posted"));
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<AlertsRecyclerRowClass> newList = new ArrayList<>();
        for(AlertsRecyclerRowClass rowClass: data_list){
            String name = rowClass.getTitle().toLowerCase();
            if(name.contains(newText)){
                newList.add(rowClass);
            }
        }


        adapter.setFilter(newList);
        return true;
    }
}
