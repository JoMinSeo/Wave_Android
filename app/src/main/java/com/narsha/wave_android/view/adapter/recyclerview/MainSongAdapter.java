package com.narsha.wave_android.view.adapter.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.data.response.music.List_db;
import com.narsha.wave_android.data.response.music.Song;

import java.util.List;
import java.util.Objects;

public class MainSongAdapter extends RecyclerView.Adapter<MainSongAdapter.MainSongViewHolder> {
    private List<Song> Songs;
    Context mContext;

    public void setData(List<Song> Songs){
        this.Songs = Songs;
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
       Glide.with(mContext).load(Songs.get(position).getJacket()).into(holder.imageView_jacket);
       holder.title.setText(Songs.get(position).getTitle());
    }
    @Override
    public int getItemCount() {
        return Songs == null ? 0 : Songs.size();
    }

    class MainSongViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_jacket;
        TextView title;
        public MainSongViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_jacket = itemView.findViewById(R.id.img_title);
            title = itemView.findViewById(R.id.title);
        }
    }
}
