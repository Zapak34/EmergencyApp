package com.nightvisionmedia.emergencyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.AlertsRecyclerViewFavAdapter;
import com.nightvisionmedia.emergencyapp.sugar_models.AlertsFavorites;

import java.util.List;

public class AlertsFavScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private AlertsRecyclerViewFavAdapter adapter;
    private List<AlertsFavorites> data_list;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_fav_screen);

        recyclerView = (RecyclerView)findViewById(R.id.alertsFavRecyclerView);
        data_list = AlertsFavorites.listAll(AlertsFavorites.class);

        toolbar = (Toolbar)findViewById(R.id.alertsFavToolbar);
        toolbar.setTitle("ALERTS FAVORITES");
        setSupportActionBar(toolbar);

        linearLayoutManager = new LinearLayoutManager(AlertsFavScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AlertsRecyclerViewFavAdapter(this,data_list);
        recyclerView.setAdapter(adapter);
    }
}
