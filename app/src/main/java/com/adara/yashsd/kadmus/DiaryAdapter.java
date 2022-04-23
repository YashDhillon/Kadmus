package com.adara.yashsd.kadmus;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.greenfrvr.hashtagview.HashtagView;

import java.util.ArrayList;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    Activity context;
    ArrayList<DiaryEntry> diaryEntries;

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mListener){this.mListener = mListener;}

    public static class DiaryViewHolder extends RecyclerView.ViewHolder{

        public TextView tvdate;
        public TextView tvday;
        public TextView tvmonth;
        public TextView tvtime;
        public TextView tvtitle;
        public TextView tvbody;

        public ViewFlipper viewFlipperMainMenu;
        public HashtagView hashtagViewMainMenu;

        public DiaryViewHolder(View itemView ,final OnItemClickListener listener) {
            super(itemView);
            tvdate = itemView.findViewById(R.id.tvdate5);
            tvday = itemView.findViewById(R.id.tvday5);
            tvmonth = itemView.findViewById(R.id.tvmonth5);
            tvtime = itemView.findViewById(R.id.tvtime5);
            tvtitle = itemView.findViewById(R.id.tvtitle5);
            tvbody = itemView.findViewById(R.id.tvbody5);

            viewFlipperMainMenu = itemView.findViewById(R.id.vFlipperMainMenu5);
            hashtagViewMainMenu = itemView.findViewById(R.id.hashTagMainMenu5);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public DiaryAdapter(Activity activity ,ArrayList<DiaryEntry> diaryEntries){
        context = activity;
        this.diaryEntries = diaryEntries;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item,parent,false);
        DiaryViewHolder diaryViewHolder = new DiaryViewHolder(v,mListener);
        return diaryViewHolder;
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position) {
        DiaryEntry currentEntry = diaryEntries.get(position);

        if(currentEntry.getUuid() == ""){
            holder.tvdate.setVisibility(View.INVISIBLE);
            holder.tvday.setVisibility(View.INVISIBLE);
            holder.tvmonth.setVisibility(View.INVISIBLE);
            holder.tvtime.setVisibility(View.INVISIBLE);
            holder.tvtitle.setVisibility(View.INVISIBLE);
            holder.tvbody.setVisibility(View.INVISIBLE);
            holder.hashtagViewMainMenu.setVisibility(View.INVISIBLE);
            holder.viewFlipperMainMenu.setVisibility(View.INVISIBLE);
        } else {
            holder.tvdate.setText(currentEntry.getDate());
            holder.tvday.setText(currentEntry.getDay());
            holder.tvmonth.setText(currentEntry.getMonth());
            holder.tvtime.setText(currentEntry.getTime());
            holder.tvtitle.setText(currentEntry.getName());
            holder.tvbody.setText(currentEntry.getBoby());

            ArrayList<Bitmap> bitmaps = currentEntry.getBitmaps();
            ArrayList<String> hashtags = currentEntry.getHashtags();

            if(hashtags.size()>0){
                holder.hashtagViewMainMenu.setData(hashtags);
            }
            else {
                holder.hashtagViewMainMenu.setVisibility(View.INVISIBLE);
            }

            if(bitmaps.size()>0 && bitmaps != null){
                for(int i = 0 ;i <bitmaps.size() ; i++){
                    ImageView iv = new ImageView(context);
                    iv = new ImageView(context.getApplicationContext());
                    iv.setImageBitmap(bitmaps.get(i));
                    iv.setAdjustViewBounds(true);
                    iv.setMaxHeight(400);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.viewFlipperMainMenu.addView(iv);
                    holder.viewFlipperMainMenu.setInAnimation(context,android.R.anim.fade_in);
                    holder.viewFlipperMainMenu.setOutAnimation(context,android.R.anim.fade_out);
                }
            }

            if(bitmaps.size()>1 && bitmaps != null){
                holder.viewFlipperMainMenu.startFlipping();
            } else {
                holder.viewFlipperMainMenu.stopFlipping();
            }
        }
    }

    @Override
    public int getItemCount() {
        return diaryEntries.size();
    }
}
