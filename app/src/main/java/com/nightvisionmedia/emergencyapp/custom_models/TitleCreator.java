package com.nightvisionmedia.emergencyapp.custom_models;

import android.content.Context;

import com.nightvisionmedia.emergencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar (GAZAMAN) Myers on 6/26/2017.
 */

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<TitleParent> _titleParents;

    public TitleCreator(Context context) {
        _titleParents = new ArrayList<>();
        String[] titles = context.getResources().getStringArray(R.array.directory_agencies_names);
        for(int i = 0; i  < titles.length; i++){
            TitleParent title = new TitleParent(titles[i]);
            _titleParents.add(title);
        }
    }

    public static TitleCreator get(Context context){
        if (_titleCreator == null){
            _titleCreator = new TitleCreator(context);
        }

        return _titleCreator;
    }


    public List<TitleParent> getAll() {
        return _titleParents;
    }
}
