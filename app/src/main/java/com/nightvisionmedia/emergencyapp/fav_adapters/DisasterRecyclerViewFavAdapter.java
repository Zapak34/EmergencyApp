package com.nightvisionmedia.emergencyapp.fav_adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.activities.ShowMainContentActivity;
import com.nightvisionmedia.emergencyapp.sugar_models.DisasterFavorites;
import com.nightvisionmedia.emergencyapp.utils.App;
import com.nightvisionmedia.emergencyapp.utils.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar (GAZAMAN) Myers on 7/8/2017.
 */
public class DisasterRecyclerViewFavAdapter extends RecyclerView.Adapter<DisasterRecyclerViewFavAdapter.ViewHolder>{
    private Context context;
    private List<DisasterFavorites> arrayList;

    public DisasterRecyclerViewFavAdapter(Context context, List<DisasterFavorites> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //gets the layout to use as the template for each row of the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_recycler_view_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this sets the text of the text view to its respective data
        holder.disasterID = arrayList.get(position).getDisasterID();
        holder.title.setText(arrayList.get(position).getDisasterTitle());
        holder.content.setText(arrayList.get(position).getDisasterContent());
        holder.image_url = arrayList.get(position).getDisasterImageURL();
        holder.time_posted.setText(arrayList.get(position).getDisasterPostedTime());

        //this make the title text view focus so it can start to scroll
        holder.title.setSelected(true);
        holder.title.requestFocus();

        //this set the image of the image view based on if image link is nul empty
        if(arrayList.get(position).getDisasterImageURL().isEmpty() || arrayList.get(position).getDisasterImageURL().contains(" ")){
            holder.ivDisasterImage.setImageResource(R.drawable.no_image_found);
            holder.ivDisasterImage.setVisibility(View.GONE);
            holder.title.setTextSize(30);
            holder.content.setTextSize(20);
        }else{
            Glide.with(context).load(arrayList.get(position).getDisasterImageURL()).into(holder.ivDisasterImage);
            holder.ivDisasterImage.setVisibility(View.VISIBLE);
            holder.title.setTextSize(20);
            holder.content.setTextSize(18);
        }

        holder.ivDisasterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ShowMainContentActivity.class);
                intent.putExtra("title",holder.title.getText().toString());
                intent.putExtra("content",holder.content.getText().toString());
                intent.putExtra("image_url",holder.image_url);
                context.startActivity(intent);
                //Message.shortToast(context,holder.content.getText().toString());
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

        holder.ivFavDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisasterFavorites.deleteAll(DisasterFavorites.class,"disaster_id = ?", String.valueOf(holder.disasterID));
                Message.longToast(context, "Favorite Removed...");
                App.refreshActivity((AppCompatActivity) context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //this class gets the each widget on the template for reference to give it the respective data
    public class ViewHolder extends RecyclerView.ViewHolder {
        public int disasterID;
        public TextView content, title, time_posted;
        public ImageView ivDisasterImage, ivFavDelete;
        public String image_url;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tvMainFavRowTitle);
            content = (TextView)itemView.findViewById(R.id.tvMainFavRowContent);
            time_posted = (TextView)itemView.findViewById(R.id.tvMainFavTimePosted);
            ivDisasterImage = (ImageView)itemView.findViewById(R.id.ivMainFavImage);
            ivFavDelete = (ImageView)itemView.findViewById(R.id.ivMainFavDelete);
        }
    }

    public void setFilter(ArrayList<DisasterFavorites> newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}

