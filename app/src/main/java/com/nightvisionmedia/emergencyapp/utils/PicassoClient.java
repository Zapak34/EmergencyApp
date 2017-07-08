package com.nightvisionmedia.emergencyapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nightvisionmedia.emergencyapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Lennis on 1/11/2017.
 */

public class PicassoClient {

    public static void downloadImage(Context c, String imageUrl, ImageView img)
    {
        if(imageUrl.length()>0 && imageUrl!=null)
        {
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.image_notpresent).into(img);
        }else {
            Picasso.with(c).load(R.drawable.image_notpresent).into(img);
        }
    }
}
