package com.nightvisionmedia.emergencyapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.DisasterRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.adapters.Automotive_RoadSafetyRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.adapters.HouseholdRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.constants.CategoryIDs;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.MainRecyclerViewRowClass;
import com.nightvisionmedia.emergencyapp.utils.App;
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

public class LoadSOSInformationScreenActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    //DECLARE THE ADAPTERS FOR CATEGORIES
    private DisasterRecyclerViewAdapter disasterAdapter;
    private Automotive_RoadSafetyRecyclerViewAdapter automotiveRoadSafetyRecyclerViewAdapter;
    private HouseholdRecyclerViewAdapter householdRecyclerViewAdapter;


    private List<MainRecyclerViewRowClass> data_list;
    private Toolbar toolbar;
    private String category_to_load, title;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_sosinformation_screen);

        getBundles();


        recyclerView = (RecyclerView)findViewById(R.id.mainSosListRecyclerView);
        data_list = new ArrayList<>();


        toolbar = (Toolbar)findViewById(R.id.mainSosListToolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);


        //gridLayoutManager = new GridLayoutManager(this,2);
        linearLayoutManager = new LinearLayoutManager(LoadSOSInformationScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(category_to_load.equals(CategoryIDs.disastersID)){
            disasterAdapter = new DisasterRecyclerViewAdapter(this,data_list);
            recyclerView.setAdapter(disasterAdapter);
        }else  if(category_to_load.equals(CategoryIDs.automotiveRoadSafetyID)){
            automotiveRoadSafetyRecyclerViewAdapter = new Automotive_RoadSafetyRecyclerViewAdapter(this,data_list);
            recyclerView.setAdapter(automotiveRoadSafetyRecyclerViewAdapter);
        }else if(category_to_load.equals(CategoryIDs.householdID)){
            householdRecyclerViewAdapter = new HouseholdRecyclerViewAdapter(this,data_list);
            recyclerView.setAdapter(householdRecyclerViewAdapter);
        }else{
            //Message.longToast(LoadSOSInformationScreenActivity.this,"Opps!! Error occurred, could not locate category");
            Message.longToast(LoadSOSInformationScreenActivity.this,"Please make sure this application is the latest version");
            finish();
        }


        boolean hasInternet = isNetworkAvailable();
        if(!hasInternet){
            Message.longToast(LoadSOSInformationScreenActivity.this, "There is no internet connection...");
        }else {
            load_data_from_server();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(category_to_load.equals(CategoryIDs.disastersID)){
            disasterAdapter.notifyDataSetChanged();
        }else if(category_to_load.equals(CategoryIDs.automotiveRoadSafetyID)){
            automotiveRoadSafetyRecyclerViewAdapter.notifyDataSetChanged();
        }else if(category_to_load.equals(CategoryIDs.householdID)){
            householdRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    private void getBundles() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        category_to_load = intent.getStringExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID);
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
                        .url(Endpoints.URL_GET_SOS_INFO_LIST+category_to_load)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for(int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        MainRecyclerViewRowClass data = new MainRecyclerViewRowClass(object.getInt("id"),object.getString("title"),object.getString("message"),object.getString("image"),object.getString("time_posted"));
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
                if(category_to_load.equals(CategoryIDs.disastersID)){
                    disasterAdapter.notifyDataSetChanged();
                }else  if(category_to_load.equals(CategoryIDs.automotiveRoadSafetyID)){
                    automotiveRoadSafetyRecyclerViewAdapter.notifyDataSetChanged();
                }else if(category_to_load.equals(CategoryIDs.householdID)){
                    householdRecyclerViewAdapter.notifyDataSetChanged();
                }
               
            }
        };
        task.execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sos_information_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        //MenuItem menuItem = menu.findItem(R.id.alertsSearch);

        switch (item.getItemId()){
            case R.id.refresh:
                App.refreshActivity(LoadSOSInformationScreenActivity.this);
                Message.shortToast(LoadSOSInformationScreenActivity.this, title+" List Refreshed");
                break;
            case R.id.search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(LoadSOSInformationScreenActivity.this);
                break;
            case R.id.favorites:
                    Intent intent = new Intent(LoadSOSInformationScreenActivity.this, ShowAllSosFavoritesScreenActivity.class);
                    intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,category_to_load);
                    intent.putExtra("title",title);
                    startActivity(intent);
                break;
        }
        return true;
    }
    
    
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<MainRecyclerViewRowClass> newList = new ArrayList<>();
        for(MainRecyclerViewRowClass rowClass: data_list){
            String name = rowClass.getTitle().toLowerCase();
            if(name.contains(newText)){
                newList.add(rowClass);
            }
        }


        if(category_to_load.equals(CategoryIDs.disastersID)){
            disasterAdapter.setFilter(newList);
        }else if(category_to_load.equals(CategoryIDs.automotiveRoadSafetyID)){
            automotiveRoadSafetyRecyclerViewAdapter.setFilter(newList);
        }else if(category_to_load.equals(CategoryIDs.householdID)){
            householdRecyclerViewAdapter.setFilter(newList);
        }


        return true;
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(LoadSOSInformationScreenActivity.this, HomeScreenActivity.class));
    }


}
