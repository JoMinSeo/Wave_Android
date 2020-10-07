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
import com.narsha.wave_android.data.response.music.PlayList
import com.narsha.wave_android.data.response.music.Song
import com.narsha.wave_android.view.activity.song.SongListActivity
import com.narsha.wave_android.view.adapter.listener.OnItemClickListener

class PlaylistAdapter(val list : List<PlayList>, val listener: OnItemClickListener) :
        RecyclerView.Adapter<PlaylistAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title = itemView?.findViewById<TextView>(R.id.item_title_text)
        val image = itemView?.findViewById<ImageView>(R.id.item_image_IV)
       // val user = itemView?.findViewById<TextView>(R.id.item_user_Text)


        fun bind(list: PlayList) {
            title?.text = list.title
            Glide.with(itemView.context).load(list.jacket).into(image)
            //user?.text = song.

            itemView.setOnClickListener {
                //val intent = Intent(itemView.context, SongListActivity::class.java)
                //intent.putExtra("ListId", song.songid)
                //itemView.context.startActivity(intent)
                listener.OnItemClick(0, list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
            return list.size
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.Holder, position: Int) {
        holder.bind(list[position])
    }

}