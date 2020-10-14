package com.narsha.wave_android.view.adapter.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.view.adapter.listener.OnItemClickListener;
import com.narsha.wave_android.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SongSelectAdapter1 extends RecyclerView.Adapter<SongSelectAdapter1.SongSelectViewHolder> {
    private List<SelectGenre> selectGenreList;
    Context mContext;
    int clickPos = -1;



    public void setData(List<SelectGenre> selectGenreList){
        this.selectGenreList = selectGenreList;
        notifyDataSetChanged();
    }
    public SongSelectAdapter1(Context mContext){
        this.mContext = mContext;
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
        SelectGenre selectGenre = selectGenreList.get(position);
        holder.button.setText(selectGenre.getMainGenreName());

        holder.btn_select.setOnClickListener(v-> {
            if(clickPos != position){
                clickPos = position;
            }else{
                clickPos = -1;
            }
            notifyDataSetChanged();
        });

        if(clickPos == position){
            holder.btn_select.setBackground(mContext.getDrawable(R.color.colorBlue));
        }else{
            holder.btn_select.setBackground(mContext.getDrawable(R.drawable.select_boarder));
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
