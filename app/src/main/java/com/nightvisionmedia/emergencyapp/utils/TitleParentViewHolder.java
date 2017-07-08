package com.nightvisionmedia.emergencyapp.utils;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.nightvisionmedia.emergencyapp.R;

/**
 * Created by Omar (GAZAMAN) Myers on 6/26/2017.
 */

public class TitleParentViewHolder extends ParentViewHolder {
    public TextView _textView;
    public ImageButton _imageButton;


    public TitleParentViewHolder(View itemView) {
        super(itemView);
        _textView = (TextView)itemView.findViewById(R.id.parentTitle);
        _imageButton = (ImageButton)itemView.findViewById(R.id.imgBtnExpandArrow);
    }
}
