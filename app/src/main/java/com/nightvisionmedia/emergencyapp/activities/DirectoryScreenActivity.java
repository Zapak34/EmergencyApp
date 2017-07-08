package com.nightvisionmedia.emergencyapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.adapters.DirectoryAdapter;
import com.nightvisionmedia.emergencyapp.custom_models.TitleChild;
import com.nightvisionmedia.emergencyapp.custom_models.TitleCreator;
import com.nightvisionmedia.emergencyapp.custom_models.TitleParent;

import java.util.ArrayList;
import java.util.List;

public class DirectoryScreenActivity extends AppCompatActivity{

    RecyclerView recyclerView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((DirectoryAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_screen);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDirectory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DirectoryAdapter adapter = new DirectoryAdapter(DirectoryScreenActivity.this, getData());
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);
    }

    private List<ParentObject> getData() {
        TitleCreator titleCreator = TitleCreator.get(DirectoryScreenActivity.this);
        List<TitleParent> titles = titleCreator.getAll();
        //String[] titles = getResources().getStringArray(R.array.directory_agencies_names);
        String[] information = getResources().getStringArray(R.array.directory_agencies_info);

        List<ParentObject> parentObject = new ArrayList<>();
        int i = 0;
        for(TitleParent title: titles){
            List<Object> childList = new ArrayList<>();
            childList.add(new TitleChild(information[i]));
            title.setChildObjectList(childList);
            parentObject.add(title);
            i += 1;
        }
        return parentObject;
    }

}
