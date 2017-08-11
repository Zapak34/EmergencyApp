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
import com.nightvisionmedia.emergencyapp.activities.ShowMainContentActivity;
import com.nightvisionmedia.emergencyapp.custom_models.MainRecyclerViewRowClass;
import com.nightvisionmedia.emergencyapp.sugar_models.HouseholdFavorites;
import com.nightvisionmedia.emergencyapp.utils.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class HouseholdRecyclerViewAdapter extends RecyclerView.Adapter<HouseholdRecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<MainRecyclerViewRowClass> my_data;


    public HouseholdRecyclerViewAdapter(Context context, List<MainRecyclerViewRowClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //gets the layout to use as the template for each row of the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this sets the text of the text view to its respective data
        holder.householdID = my_data.get(position).getId();
        holder.title.setText(my_data.get(position).getTitle());
        holder.content.setText(my_data.get(position).getContent());
        holder.time_posted.setText(my_data.get(position).getTime_posted());
        holder.image_url = my_data.get(position).getImage_link();


        //this make the title text view focus so it can start to scroll
        holder.title.setSelected(true);
        holder.title.requestFocus();

        //this set the image of the image view based on if image link is nul empty
        if(my_data.get(position).getImage_link().isEmpty() || my_data.get(position).getImage_link().contains(" ")){
            holder.ivImage.setImageResource(R.drawable.no_image_found);
            holder.ivImage.setVisibility(View.GONE);
            holder.title.setTextSize(30);
            holder.content.setTextSize(20);
        }else{
            Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.ivImage);
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.title.setTextSize(20);
            holder.content.setTextSize(18);
        }

        String[] temp = {String.valueOf(holder.householdID)};
        holder.count =  HouseholdFavorites.count(HouseholdFavorites.class, "household_id = ?",temp);
        if(holder.count > 0){
            holder.ivFavorite.setImageResource(R.mipmap.ic_star_on);
        }else{
            holder.ivFavorite.setImageResource(R.mipmap.ic_star_off);
        }
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowMainContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowMainContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowMainContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
            }
        });


        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] temp = {String.valueOf(holder.householdID)};
                long countTemp =  HouseholdFavorites.count(HouseholdFavorites.class, "household_id = ?",temp);
                    if(holder.count == 0)
                    {
                        //querying list return empty, there is no record found matching the query.
                        HouseholdFavorites householdFavorites = new HouseholdFavorites();
                        householdFavorites.setHouseholdID(holder.householdID);
                        householdFavorites.setHouseholdTitle(holder.title.getText().toString());
                        householdFavorites.setHouseholdContent(holder.content.getText().toString());
                        householdFavorites.setHouseholdImageURL(holder.image_url);
                        householdFavorites.setHouseholdPostedTime(holder.time_posted.getText().toString());
                        householdFavorites.save();

                        holder.ivFavorite.setImageResource(R.mipmap.ic_star_on);
                        Message.longToast(context, "Favorite Saved...");
                    }

                    if(countTemp > 0)
                    {

                        //there are records matching your query.
                        HouseholdFavorites.deleteAll(HouseholdFavorites.class, "household_id = ?", String.valueOf(holder.householdID));
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
        public int householdID;
        public TextView content, title, time_posted;
        public ImageView ivImage, ivFavorite;
        public String image_url;
        public long count;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tvMainRowTitle);
            content = (TextView)itemView.findViewById(R.id.tvMainRowContent);
            time_posted = (TextView)itemView.findViewById(R.id.tvMainTimePosted);
            ivImage = (ImageView)itemView.findViewById(R.id.ivMainImage);
            ivFavorite = (ImageView)itemView.findViewById(R.id.ivMainFavorite);
        }
    }

    public void setFilter(ArrayList<MainRecyclerViewRowClass> newList){
        my_data = new ArrayList<>();
        my_data.addAll(newList);
        notifyDataSetChanged();
    }
}
