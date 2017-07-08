package com.nightvisionmedia.emergencyapp.utils;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.nightvisionmedia.emergencyapp.R;

/**
 * Created by Omar (GAZAMAN) Myers on 6/26/2017.
 */

public class TitleChildViewHolder extends ChildViewHolder {
    public TextView info;
    public TitleChildViewHolder(View itemView) {
        super(itemView);
        info = (TextView)itemView.findViewById(R.id.tvInfo);
    }


}
