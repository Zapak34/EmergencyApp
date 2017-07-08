package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.MoreRecyclerViewAdapter;
import com.nightvisionmedia.emergencyapp.utils.BottomNavigationHelper;
import com.nightvisionmedia.emergencyapp.custom_models.MoreRecyclerViewRowClass;

import java.util.ArrayList;

public class MoreScreenActivity extends AppCompatActivity implements MoreRecyclerViewAdapter.ClickListener{
    private RecyclerView recyclerView;
    private MoreRecyclerViewAdapter moreRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        recyclerView = (RecyclerView)findViewById(R.id.moreMenuRecyclerView);
        moreRecyclerViewAdapter = new MoreRecyclerViewAdapter(MoreScreenActivity.this, getData());
        recyclerView.setAdapter(moreRecyclerViewAdapter);
        moreRecyclerViewAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        Intent intent = new Intent(MoreScreenActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_alerts:
                        Intent intent0 = new Intent(MoreScreenActivity.this, AlertsScreenActivity.class);
                        startActivity(intent0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_need_help:
                        Intent intent1 = new Intent(MoreScreenActivity.this, INeedHelpActivity.class);
                        startActivity(intent1);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_happenings:
                        Intent intent2 = new Intent(MoreScreenActivity.this, HappeningsActivity.class);
                        startActivity(intent2);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_more:

                        break;

                }
                return false;
            }
        });
    }

    public ArrayList<MoreRecyclerViewRowClass> getData() {
        ArrayList<MoreRecyclerViewRowClass> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_account_box_black_35dp, R.drawable.ic_directory_35dp,  R.drawable.ic_share_black_35dp, R.drawable.ic_half_rate_star_35dp,R.drawable.ic_settings_black_35dp
        ,R.drawable.ic_info_outline_black_35dp};
        String[] titles = getResources().getStringArray(R.array.more_recycler_view_titles);


        for (int i = 0; i < titles.length && i < icons.length; i++) {
            MoreRecyclerViewRowClass current = new MoreRecyclerViewRowClass();
            current.setIconId(icons[i]);
            current.setTitle(titles[i]);
            data.add(current);
        }
        return data;
    }


    @Override
    public void itemClicked(View view, int position) {
        switch(position){
            case 0:
                break;
            case 1:
                startActivity(new Intent(MoreScreenActivity.this,DirectoryScreenActivity.class));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}
