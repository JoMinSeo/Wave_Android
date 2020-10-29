package com.project.wave_v2.widget

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.project.wave_v2.R
import com.project.wave_v2.data.response.playlist.ListInfo

class MainAdapter : RecyclerView.Adapter<MainAdapter.Holder>(){
    lateinit var playList : ArrayList<ListInfo>

    fun setList(list : ArrayList<ListInfo>){
        if(::playList.isInitialized) return
        playList = list
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(list : ListInfo) {

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