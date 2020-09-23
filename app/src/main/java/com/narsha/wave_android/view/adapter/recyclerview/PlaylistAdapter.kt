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
import com.narsha.wave_android.data.response.playlist.playList
import com.narsha.wave_android.view.activity.song.SongListActivity

class PlaylistAdapter(val list : ArrayList<playList>) :
        RecyclerView.Adapter<PlaylistAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title = itemView?.findViewById<TextView>(R.id.item_title_text)
        val image = itemView?.findViewById<ImageView>(R.id.item_image_IV)
        val user = itemView?.findViewById<TextView>(R.id.item_user_Text)


        fun bind(playList: playList) {
            title?.text = playList.listTitle
            Glide.with(itemView.context).load(playList.listImageUrl).into(image)
            user?.text = playList.userId

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SongListActivity::class.java)
                intent.putExtra("ListId", playList.listId)
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