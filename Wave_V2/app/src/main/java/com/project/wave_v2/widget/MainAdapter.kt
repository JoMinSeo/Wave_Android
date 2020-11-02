package com.project.wave_v2.widget

import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.transition.Hold
import com.project.wave_v2.R
import com.project.wave_v2.data.response.playlist.ListInfo

class MainAdapter(val playList : ArrayList<ListInfo>) : RecyclerView.Adapter<MainAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.img_title)

        fun bind(list : ListInfo) {
            if(list.jacket == ""){
                img.setImageResource(R.drawable.def_music)
            }else{
                val glide = Glide.with(itemView.context)
                glide.load(Uri.parse(list.jacket)).into(img)
            }

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false )
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }

}