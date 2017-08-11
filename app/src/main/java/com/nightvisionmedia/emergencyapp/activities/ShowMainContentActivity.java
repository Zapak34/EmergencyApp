package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.utils.SharedPrefManager;

public class ShowMainContentActivity extends AppCompatActivity {
    private TextView tvTitle, tvContent;
    private ImageView ivContentPic;
    private String title, content, image_url = "",fromNotification;
    private int isNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        getActivityExtras();
        setupWidgets();
    }

    private void setupWidgets() {
        tvTitle = (TextView)findViewById(R.id.tvShowAlertsTitle);
        tvContent = (TextView)findViewById(R.id.tvShowAlertsContent);
        ivContentPic = (ImageView)findViewById(R.id.ivShowAlertsImage);

        tvTitle.setText(title);
        tvContent.setText(content);


        tvTitle.setSelected(true);
        tvTitle.requestFocus();
        if(image_url == "" || image_url.contains(" ") || image_url.isEmpty()){
         ivContentPic.setVisibility(View.GONE);
        }else{
            ivContentPic.setVisibility(View.VISIBLE);
            Glide.with(ShowMainContentActivity.this).load(image_url).into(ivContentPic);
        }


    }


    private void getActivityExtras() {
        title = getIntent().getExtras().getString("title");
        content = getIntent().getExtras().getString("content");

        Intent intent = getIntent();
        if(intent.hasExtra("image_url")){
            image_url = getIntent().getExtras().getString("image_url");
        }
        if(intent.hasExtra("isNotification") && SharedPrefManager.getInstance(ShowMainContentActivity.this).getAutomaticLogin() == 1){
            isNotification = 1;

        }else if(intent.hasExtra("isNotification") && SharedPrefManager.getInstance(ShowMainContentActivity.this).getAutomaticLogin() == -1){
            isNotification = 2;
        }
    }

    @Override
    public void onBackPressed() {
        if(isNotification == 1){
            startActivity(new Intent(ShowMainContentActivity.this,AlertsScreenActivity.class));
        }else if(isNotification == 2){
            startActivity(new Intent(ShowMainContentActivity.this,LoginScreenActivity.class));
        }
        super.onBackPressed();

    }
}
