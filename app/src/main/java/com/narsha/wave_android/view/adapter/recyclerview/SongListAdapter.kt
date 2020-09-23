package com.narsha.wave_android.view.adapter.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narsha.wave_android.R
import com.narsha.wave_android.data.Songs
import com.narsha.wave_android.data.response.playlist.playList
import com.narsha.wave_android.view.activity.song.SongListActivity

class SongListAdapter(var list : ArrayList<Songs>):
        RecyclerView.Adapter<SongListAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView?.findViewById<TextView>(R.id.item_song_title)
        val img = itemView?.findViewById<ImageView>(R.id.item_song_img)

        fun bind(songs: Songs) {
            title.text = songs.title
            Glide.with(itemView.context).load(songs.songurl).into(img)

            itemView.setOnClickListener {
                //song액티비티로 넘겨야함함
            }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_songlist, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

}