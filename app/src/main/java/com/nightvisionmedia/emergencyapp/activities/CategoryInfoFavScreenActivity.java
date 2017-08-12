package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.constants.CategoryIDs;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;

public class CategoryInfoFavScreenActivity extends AppCompatActivity {
    private ListView lvCategoryFavorites;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info_fav_screen);

        lvCategoryFavorites = (ListView)findViewById(R.id.lvCategoryFav);
        adapter = new ArrayAdapter<String>(CategoryInfoFavScreenActivity.this,android.R.layout.simple_list_item_1, CategoryIDs.categoryFavList);
        lvCategoryFavorites.setAdapter(adapter);
        lvCategoryFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CategoryInfoFavScreenActivity.this, ShowAllSosFavoritesScreenActivity.class);
                switch (i){
                    //Disasters Category
                    case 0:
                        intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,CategoryIDs.disastersID);
                        intent.putExtra("title",CategoryIDs.categoryFavList[i]);
                        startActivity(intent);
                        break;
                    //Automotive And Road Safety
                    case 1:
                        intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,CategoryIDs.automotiveRoadSafetyID);
                        intent.putExtra("title",CategoryIDs.categoryFavList[i]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,CategoryIDs.householdID);
                        intent.putExtra("title",CategoryIDs.categoryFavList[i]);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
