package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.constants.CategoryIDs;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.fav_adapters.DisasterRecyclerViewFavAdapter;
import com.nightvisionmedia.emergencyapp.fav_adapters.AutomotiveRoadSafetyRecyclerViewFavAdapter;
import com.nightvisionmedia.emergencyapp.fav_adapters.HouseholdRecyclerViewFavAdapter;
import com.nightvisionmedia.emergencyapp.sugar_models.DisasterFavorites;
import com.nightvisionmedia.emergencyapp.sugar_models.AutomotiveRoadSafetyFavorites;
import com.nightvisionmedia.emergencyapp.sugar_models.HouseholdFavorites;
import com.nightvisionmedia.emergencyapp.utils.Message;

import java.util.List;

public class ShowAllSosFavoritesScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    //DECLARE FAVORITE ADPATERS
    private DisasterRecyclerViewFavAdapter disasterFavAdapter;
    private AutomotiveRoadSafetyRecyclerViewFavAdapter automotiveRoadSafetyFavAdapter;
    private HouseholdRecyclerViewFavAdapter householdRecyclerViewFavAdapter;

    //DECLARE LIST TO HOLD THE FAVORITES
    private List<DisasterFavorites> disaster_fav_data_list;
    private List<AutomotiveRoadSafetyFavorites> temporary_fav_data_list;
    private List<HouseholdFavorites> household_fav_data_list;
    private Toolbar toolbar;
    private String title, fav_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_sos_favorites_screen);


        getBundles();
        setupWidgets();


        loadFavoriteData();
        setupAdapters();



        if(fav_category.equals(CategoryIDs.disastersID)){
            recyclerView.setAdapter(disasterFavAdapter);
        }else if(fav_category.equals(CategoryIDs.automotiveRoadSafetyID)){
            recyclerView.setAdapter(automotiveRoadSafetyFavAdapter);
        }if(fav_category.equals(CategoryIDs.householdID)){
            recyclerView.setAdapter(householdRecyclerViewFavAdapter);
        }
    }

    private void setupAdapters() {
        if(fav_category.equals(CategoryIDs.disastersID)){
            disasterFavAdapter = new DisasterRecyclerViewFavAdapter(this, disaster_fav_data_list);
        }else if(fav_category.equals(CategoryIDs.automotiveRoadSafetyID)){
            automotiveRoadSafetyFavAdapter = new AutomotiveRoadSafetyRecyclerViewFavAdapter(this, temporary_fav_data_list);
        }else if(fav_category.equals(CategoryIDs.householdID)){
            householdRecyclerViewFavAdapter = new HouseholdRecyclerViewFavAdapter(this, household_fav_data_list);
        }else{
            Message.longToast(ShowAllSosFavoritesScreenActivity.this,"Opps!! Error occurred while getting data from favorites");
            Message.longToast(ShowAllSosFavoritesScreenActivity.this,"Please make sure this application is the latest version");
        }
    }

    private void setupWidgets() {
        recyclerView = (RecyclerView)findViewById(R.id.favRecyclerView);
        toolbar = (Toolbar)findViewById(R.id.favToolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        linearLayoutManager = new LinearLayoutManager(ShowAllSosFavoritesScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void loadFavoriteData() {
        if(fav_category.equals(CategoryIDs.disastersID)){
            disaster_fav_data_list = DisasterFavorites.listAll(DisasterFavorites.class);
        }else if(fav_category.equals(CategoryIDs.automotiveRoadSafetyID)){
            temporary_fav_data_list = AutomotiveRoadSafetyFavorites.listAll(AutomotiveRoadSafetyFavorites.class);
        }else if(fav_category.equals(CategoryIDs.householdID)){
            household_fav_data_list = HouseholdFavorites.listAll(HouseholdFavorites.class);
        }else{
            Message.shortToast(ShowAllSosFavoritesScreenActivity.this,"Opps!! Error occurred while loading favorites data");
            Message.longToast(ShowAllSosFavoritesScreenActivity.this,"Please make sure this application is the latest version");
        }

    }

    private void getBundles() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        fav_category = intent.getStringExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID);
    }
}
