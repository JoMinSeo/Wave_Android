package com.narsha.wave_android.view.adapter.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.narsha.wave_android.R;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.data.response.music.RecommendList;
import com.narsha.wave_android.data.response.music.Song;

import java.util.List;

public class MainSongAdapter extends RecyclerView.Adapter<MainSongAdapter.MainSongViewHolder> {
    private List<PlayList> MainList;
    Context mContext;

    public void setData(List<PlayList> Songs){
        this.MainList = Songs;
        notifyDataSetChanged();
    }
    public MainSongAdapter(Context mContext){
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MainSongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainSongViewHolder holder, int position) {
        if(MainList != null){
            if(MainList.get(position) != null) {
                        String url = MainList.get(position).getJacket();
                        Glide.with(mContext).load(url).into(holder.imageView_jacket);
            }
        }
    }
    @Override
    public int getItemCount() {
        return MainList == null ? 0 : MainList.size();
    }

    class MainSongViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_jacket;
        public MainSongViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_jacket = itemView.findViewById(R.id.img_title);
        }
    }
}
