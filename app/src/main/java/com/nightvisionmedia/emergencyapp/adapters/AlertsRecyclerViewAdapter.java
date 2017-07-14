package com.nightvisionmedia.emergencyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.activities.ShowAlertsContentActivity;
import com.nightvisionmedia.emergencyapp.custom_models.AlertsRecyclerRowClass;
import com.nightvisionmedia.emergencyapp.sugar_models.AlertsFavorites;
import com.nightvisionmedia.emergencyapp.utils.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class AlertsRecyclerViewAdapter extends RecyclerView.Adapter<AlertsRecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<AlertsRecyclerRowClass> my_data;


    public AlertsRecyclerViewAdapter(Context context, List<AlertsRecyclerRowClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //gets the layout to use as the template for each row of the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_recycler_view_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this sets the text of the text view to its respective data
        holder.alertID = my_data.get(position).getId();
        holder.title.setText(my_data.get(position).getTitle());
        holder.content.setText(my_data.get(position).getContent());
        holder.time_posted.setText(my_data.get(position).getTime_posted());
        holder.image_url = my_data.get(position).getImage_link();


        //this make the title text view focus so it can start to scroll
        holder.title.setSelected(true);
        holder.title.requestFocus();

        //this set the image of the image view based on if image link is nul empty
        if(my_data.get(position).getImage_link().isEmpty() || my_data.get(position).getImage_link().contains(" ")){
            holder.ivAlertImage.setImageResource(R.drawable.image_notpresent);
        }else{
            Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.ivAlertImage);
        }

        String[] temp = {String.valueOf(holder.alertID)};
        holder.count =  AlertsFavorites.count(AlertsFavorites.class, "alert_id = ?",temp);
        if(holder.count > 0){
            holder.ivFavorite.setImageResource(R.mipmap.ic_star_on);
        }else{
            holder.ivFavorite.setImageResource(R.mipmap.ic_star_off);
        }
        holder.ivAlertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowAlertsContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowAlertsContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowAlertsContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });


        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = {String.valueOf(holder.alertID)};
                long countTemp =  AlertsFavorites.count(AlertsFavorites.class, "alert_id = ?",temp);
                    if(holder.count == 0)
                    {
                        //querying list return empty, there is no record found matching the query.
                        AlertsFavorites alertsFavorites = new AlertsFavorites();
                        alertsFavorites.setAlertID(holder.alertID);
                        alertsFavorites.setAlertTitle(holder.title.getText().toString());
                        alertsFavorites.setAlertContent(holder.content.getText().toString());
                        alertsFavorites.setAlertImageURL(holder.image_url);
                        alertsFavorites.setAlertPostedTime(holder.time_posted.getText().toString());
                        alertsFavorites.save();

                        holder.ivFavorite.setImageResource(R.mipmap.ic_star_on);
                        Message.longToast(context, "Favorite Saved...");
                    }

                    if(countTemp > 0)
                    {

                        //there are records matching your query.
                        AlertsFavorites.deleteAll(AlertsFavorites.class, "alert_id = ?", String.valueOf(holder.alertID));
                        holder.ivFavorite.setImageResource(R.mipmap.ic_star_off);
                        Message.longToast(context, "Favorite Removed...");
                    }

            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }


    //this class gets the each widget on the template for reference to give it the respective data
    public class ViewHolder extends RecyclerView.ViewHolder {
        public int alertID;
        public TextView content, title, time_posted;
        public ImageView ivAlertImage, ivFavorite;
        public String image_url;
        public long count;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tvAlertsRowTitle);
            content = (TextView)itemView.findViewById(R.id.tvAlertsRowContent);
            time_posted = (TextView)itemView.findViewById(R.id.tvAlertsTimePosted);
            ivAlertImage = (ImageView)itemView.findViewById(R.id.ivAlertsImage);
            ivFavorite = (ImageView)itemView.findViewById(R.id.ivAlertsFavorite);
        }
    }

    public void setFilter(ArrayList<AlertsRecyclerRowClass> newList){
        my_data = new ArrayList<>();
        my_data.addAll(newList);
        notifyDataSetChanged();
    }
}
