package com.narsha.wave_android.view.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.narsha.wave_android.R;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.view.adapter.listener.OnItemClickListener;

import java.util.List;

public class MainSongAdapter extends RecyclerView.Adapter<MainSongAdapter.MainSongViewHolder> {
    private List<PlayList> mainList;
    Context mContext;
    OnItemClickListener onItemClickListener;

    public void setData(List<PlayList> Songs){
        this.mainList = Songs;
        notifyDataSetChanged();
    }
    public MainSongAdapter(Context mContext, OnItemClickListener onItemClickListener){
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
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
        if(mainList != null){
            if(mainList.get(position) != null) {
                        String url = mainList.get(position).getJacket();
                        Glide.with(mContext).load(url).into(holder.imageView_jacket);
            }
        }
        holder.itemView.setOnClickListener(v->{
            onItemClickListener.OnItemClick(position, mainList.get(position));
        });
    }
    @Override
    public int getItemCount() {
        return mainList == null ? 0 : mainList.size();
    }

    class MainSongViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_jacket;
        public MainSongViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_jacket = itemView.findViewById(R.id.img_title);
        }
    }
}
