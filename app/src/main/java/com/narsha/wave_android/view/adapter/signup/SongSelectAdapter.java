package com.narsha.wave_android.view.adapter.signup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.genre.Genre;
import com.narsha.wave_android.view.adapter.listener.OnGenreItemClickListener;
import com.narsha.wave_android.view.adapter.listener.OnPlaylistItemClickListener;

import java.util.List;

public class SongSelectAdapter extends RecyclerView.Adapter<SongSelectAdapter.SongSelectViewHolder> {
    private List<Genre> selectGenreList;
    OnGenreItemClickListener listener;
    Context mContext;
    int clickPos = -1;

    public void setData(List<Genre> selectGenreList){
        this.selectGenreList = selectGenreList;
        notifyDataSetChanged();
    }
    public SongSelectAdapter(Context mContext, OnGenreItemClickListener listener){
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select, parent, false);

        return new SongSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongSelectViewHolder holder, int position) {
        Genre selectGenre = selectGenreList.get(position);
        holder.button.setText(String.valueOf(selectGenre.getMainGenreName()));

        Integer id = selectGenre.getMainGenreId();
        holder.btn_select.setOnClickListener(v-> {
            if(clickPos != position){
                clickPos = position;
            }else{
                clickPos = -1;
            }
            listener.OnItemClick(position, id);
            notifyDataSetChanged();
        });

        if(clickPos == position){
            holder.btn_select.setBackground(ContextCompat.getDrawable(mContext, R.color.colorBlue));
            holder.btn_select.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            Log.d("position change", position + "");
        }else{
            holder.btn_select.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_boarder));
            holder.btn_select.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlue));
        }
    }
    @Override
    public int getItemCount() {
        return selectGenreList == null ? 0 : selectGenreList.size();
    }

    class SongSelectViewHolder extends RecyclerView.ViewHolder {
        Button button;
        Button btn_select;
        public SongSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_select = itemView.findViewById(R.id.select);
            button = itemView.findViewById(R.id.select);
        }
    }
}
