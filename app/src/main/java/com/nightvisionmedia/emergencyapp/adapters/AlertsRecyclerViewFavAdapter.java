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
 * Created by Omar (GAZAMAN) Myers on 7/8/2017.
 */
public class AlertsRecyclerViewFavAdapter extends RecyclerView.Adapter<AlertsRecyclerViewFavAdapter.ViewHolder>{
    private Context context;
    private List<AlertsFavorites> arrayList;

    public AlertsRecyclerViewFavAdapter(Context context, List<AlertsFavorites> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //gets the layout to use as the template for each row of the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_recycler_view_row1,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this sets the text of the text view to its respective data
        holder.alertID = arrayList.get(position).getAlertID();
        holder.title.setText(arrayList.get(position).getAlertTitle());
        holder.content.setText(arrayList.get(position).getAlertContent());
        holder.image_url = arrayList.get(position).getAlertImageURL();
        holder.time_posted.setText(arrayList.get(position).getAlertPostedTime());

        //this make the title text view focus so it can start to scroll
        holder.title.setSelected(true);
        holder.title.requestFocus();

        //this set the image of the image view based on if image link is nul empty
        if(arrayList.get(position).getAlertImageURL().isEmpty() || arrayList.get(position).getAlertImageURL().contains(" ")){
            holder.ivAlertImage.setImageResource(R.drawable.image_notpresent);
        }else{
            Glide.with(context).load(arrayList.get(position).getAlertImageURL()).into(holder.ivAlertImage);
        }

//        String[] temp = {String.valueOf(holder.alertID)};
//        count =  AlertsFavorites.count(AlertsFavorites.class, "alert_id = ?",temp);
//        if(count > 0){
//            holder.ivFavorite.setImageResource(R.mipmap.ic_star_on);
//        }else{
//            holder.ivFavorite.setImageResource(R.mipmap.ic_star_off);
//        }
        holder.ivAlertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowAlertsContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
                //Message.shortToast(context,holder.content.getText().toString());
            }
        });

//        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(holder.ivFavorite.getDrawable().getConstantState() == context.getResources().getDrawable( R.mipmap.ic_star_off).getConstantState()){
//                    AlertsFavorites alertsFavorites = new AlertsFavorites();
//                    alertsFavorites.setAlertID(holder.alertID);
//                    alertsFavorites.setAlertTitle(holder.title.getText().toString());
//                    alertsFavorites.setAlertContent(holder.content.getText().toString());
//                    alertsFavorites.setAlertImageURL(holder.image_url);
//                    alertsFavorites.setAlertPostedTime(holder.time_posted.getText().toString());
//                    alertsFavorites.save();
//                    Message.longToast(context, "Favorite Saved...");
//                }else if(holder.ivFavorite.getDrawable().getConstantState() == context.getResources().getDrawable( R.mipmap.ic_star_on).getConstantState()){
//                    AlertsFavorites.deleteAll(AlertsFavorites.class, "alert_id = ?", String.valueOf(holder.alertID));
//                    Message.longToast(context, "Favorite Removed...");
//                }else{
//                    Message.longToast(context, "Cannot Favorite While Searching...");
//                }
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //this class gets the each widget on the template for reference to give it the respective data
    public class ViewHolder extends RecyclerView.ViewHolder {
        public int alertID;
        public TextView content, title, time_posted;
        public ImageView ivAlertImage;
        public String image_url;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tvAlertsRowTitle);
            content = (TextView)itemView.findViewById(R.id.tvAlertsRowContent);
            time_posted = (TextView)itemView.findViewById(R.id.tvAlertsTimePosted);
            ivAlertImage = (ImageView)itemView.findViewById(R.id.ivAlertsImage);
        }
    }

    public void setFilter(ArrayList<AlertsFavorites> newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}

