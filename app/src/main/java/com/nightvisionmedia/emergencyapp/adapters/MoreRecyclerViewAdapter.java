package com.nightvisionmedia.emergencyapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.custom_models.MoreRecyclerViewRowClass;

import java.util.ArrayList;

/**
 * Created by Omar (GAZAMAN) Myers on 6/18/2017.
 */

public class MoreRecyclerViewAdapter extends RecyclerView.Adapter<MoreRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<MoreRecyclerViewRowClass> data;
    LayoutInflater inflater;
    int previousPosition = 0;
    ClickListener clickListener;

    public MoreRecyclerViewAdapter(Context context, ArrayList<MoreRecyclerViewRowClass> data) {
        this.context = context;
        this.data = data;
//        Intent intent = ((Activity)context).getIntent();
//        userUsername = intent.getExtras().getString("username");
//        userPassword = intent.getExtras().getString("password");

        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //ROW INFLATER
        View view = inflater.inflate(R.layout.more_recycler_view_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MoreRecyclerViewAdapter.MyViewHolder holder, int position) {
        MoreRecyclerViewRowClass current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageResource(current.getIconId());
    }

    public void setListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvRowTitle);
            icon = (ImageView) itemView.findViewById(R.id.ivRowImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener !=null){
                clickListener.itemClicked(view,getPosition());
            }
        }
    }

    public interface ClickListener{
        void itemClicked(View view, int position);
    }


}
