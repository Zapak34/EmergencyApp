package com.nightvisionmedia.emergencyapp.adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.custom_models.TitleChild;
import com.nightvisionmedia.emergencyapp.custom_models.TitleParent;
import com.nightvisionmedia.emergencyapp.utils.Message;

import java.util.List;
import java.util.Locale;

/**
 * Created by Omar (GAZAMAN) Myers on 6/26/2017.
 */

public class DirectoryAdapter extends ExpandableRecyclerAdapter <DirectoryAdapter.TitleParentViewHolder,DirectoryAdapter.TitleChildViewHolder>{

    private TextToSpeech ttsObject;
    static String text;
    private int result;
    private LayoutInflater inflater;
    private Context mContext;


    public DirectoryAdapter(final Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        ttsObject = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    result = ttsObject.setLanguage(Locale.UK);
                    ttsObject.setSpeechRate(0.84f);
                    ttsObject.setPitch(1.1f);
                }else{
                    Message.shortToast(context, "Feature Not Supported!!!");
                }
            }
        });
    }


    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.directory_row_parent,viewGroup,false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.directory_row_child,viewGroup,false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        TitleParent title = (TitleParent) o;
        titleParentViewHolder._textView.setText(title.getTitle());

    }

    @Override
    public void onBindChildViewHolder(final TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        TitleChild title = (TitleChild) o;
        titleChildViewHolder.tvInfo.setText(title.getInfo());

        titleChildViewHolder.tvInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                    Message.shortToast(mContext, "This Feature is Not Supported For Your Device!!!");
                }else{
                    text =  titleChildViewHolder.tvInfo.getText().toString();
                    ttsObject.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
                return true;
            }
        });
    }


    class TitleParentViewHolder extends ParentViewHolder{
        public TextView _textView;
        public ImageButton _imageButton;


        public TitleParentViewHolder(View itemView) {
            super(itemView);
            _textView = (TextView)itemView.findViewById(R.id.parentTitle);
            _imageButton = (ImageButton)itemView.findViewById(R.id.imgBtnExpandArrow);
        }

    }

    class TitleChildViewHolder extends ChildViewHolder {
        public TextView tvInfo;
        public TitleChildViewHolder(View itemView) {
            super(itemView);
            tvInfo = (TextView)itemView.findViewById(R.id.tvInfo);

        }

   }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if(ttsObject != null){
            ttsObject.stop();
            ttsObject.shutdown();
        }
    }


}
