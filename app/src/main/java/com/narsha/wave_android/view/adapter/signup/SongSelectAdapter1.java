package com.narsha.wave_android.view.adapter.signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.view.adapter.listener.OnItemClickListener;

import java.util.List;

public class SongSelectAdapter1 extends RecyclerView.Adapter<SongSelectAdapter1.SongSelectViewHolder> {
    private List<SelectGenre> selectGenreList;
    Context mContext;

    public void setData(List<SelectGenre> selectGenreList){
        this.selectGenreList = selectGenreList;

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
        holder.button.setText(selectGenre.getGenre());

        holder.btn_select.setOnClickListener(v->{
            holder.btn_select.setBackgroundColor(mContext.getColor(R.color.colorBlue));
        });
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
