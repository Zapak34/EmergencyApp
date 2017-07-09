package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.utils.BottomNavigationHelper;

public class HappeningsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happenings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem= menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        Intent intent = new Intent(HappeningsActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_alerts:
                        Intent intent0 = new Intent(HappeningsActivity.this, AlertsScreenActivity.class);
                        startActivity(intent0);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_need_help:
                        Intent intent1 = new Intent(HappeningsActivity.this, INeedHelpActivity.class);
                        startActivity(intent1);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case R.id.ic_happenings:
                        break;
                    case R.id.ic_more:
                        Intent intent3 = new Intent(HappeningsActivity.this, MoreScreenActivity.class);
                        startActivity(intent3);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(HappeningsActivity.this, HomeScreenActivity.class));
    }
}
