package com.project.wave_v2.widget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.response.playlist.ListInfo
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.view.activity.SongListActivity

class PlayListAdapter(val playList : ArrayList<MyPlayListModel>) : RecyclerView.Adapter<PlayListAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.item_title_text)
        var img = itemView.findViewById<ImageView>(R.id.item_image_IV)
        fun bind(list : MyPlayListModel) {
            title.text = list.listName

            val glide = Glide.with(itemView.context)
            glide.load(Uri.parse(list.jacket)).into(img)

            itemView.setOnClickListener {
                var intent = Intent(itemView.context, SongListActivity::class.java)
                intent.putExtra("listId", list.listId)
                intent.putExtra("listName", list.listName)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false )
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }


}