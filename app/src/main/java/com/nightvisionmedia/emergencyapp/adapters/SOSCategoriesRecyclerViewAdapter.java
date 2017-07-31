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
import com.nightvisionmedia.emergencyapp.activities.LoadSOSInformationScreenActivity;
import com.nightvisionmedia.emergencyapp.constants.Endpoints;
import com.nightvisionmedia.emergencyapp.custom_models.SOSTitleRecyclerViewRowClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class SOSCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<SOSCategoriesRecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<SOSTitleRecyclerViewRowClass> my_data;


    public SOSCategoriesRecyclerViewAdapter(Context context, List<SOSTitleRecyclerViewRowClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //gets the layout to use as the template for each row of the recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sos_category_recycler_view_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //this sets the text of the text view to its respective data
        holder.categoryID = my_data.get(position).getId();
        holder.title.setText(my_data.get(position).getTitle());
        holder.image_url = my_data.get(position).getImage_link();


        //this make the title text view focus so it can start to scroll
        holder.title.setSelected(true);
        holder.title.requestFocus();

        //this set the image of the image view based on if image link is nul empty
        if(my_data.get(position).getImage_link().isEmpty() || my_data.get(position).getImage_link().contains(" ")){
            holder.ivImage.setImageResource(R.drawable.no_image_found);
            holder.ivImage.setVisibility(View.GONE);
        }else{
            Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.ivImage);
            holder.ivImage.setVisibility(View.VISIBLE);
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LoadSOSInformationScreenActivity.class);
                intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,String.valueOf(holder.categoryID));
                intent.putExtra("title",String.valueOf(holder.title.getText().toString()));
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LoadSOSInformationScreenActivity.class);
                intent.putExtra(Endpoints.KEY_BUNDLE_CATEGORY_ID,String.valueOf(holder.categoryID));
                intent.putExtra("title",String.valueOf(holder.title.getText().toString()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }


    //this class gets the each widget on the template for reference to give it the respective data
    public class ViewHolder extends RecyclerView.ViewHolder {
        public int categoryID;
        public TextView title;
        public ImageView ivImage;
        public String image_url;
        public long count;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tvMainRowTitle);
            ivImage = (ImageView)itemView.findViewById(R.id.ivMainImage);
        }
    }

    public void setFilter(ArrayList<SOSTitleRecyclerViewRowClass> newList){
        my_data = new ArrayList<>();
        my_data.addAll(newList);
        notifyDataSetChanged();
    }
}
