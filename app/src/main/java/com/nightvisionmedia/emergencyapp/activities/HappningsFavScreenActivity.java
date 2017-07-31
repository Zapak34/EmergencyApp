package com.nightvisionmedia.emergencyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.fav_adapters.HappeningsRecyclerViewFavAdapter;
import com.nightvisionmedia.emergencyapp.sugar_models.HappeningsFavorites;

import java.util.List;

public class HappningsFavScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private HappeningsRecyclerViewFavAdapter adapter;
    private List<HappeningsFavorites> data_list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happenings_fav_screen);


        recyclerView = (RecyclerView)findViewById(R.id.happeningsFavRecyclerView);
        data_list = HappeningsFavorites.listAll(HappeningsFavorites.class);

        toolbar = (Toolbar)findViewById(R.id.happeningsFavToolbar);
        toolbar.setTitle("HAPPENINGS FAVORITES");
        setSupportActionBar(toolbar);

        linearLayoutManager = new LinearLayoutManager(HappningsFavScreenActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HappeningsRecyclerViewFavAdapter(this,data_list);
        recyclerView.setAdapter(adapter);
    }
}
