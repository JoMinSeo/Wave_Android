package com.project.wave_v2.widget

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.PlayListSongBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.ListInfo
import com.project.wave_v2.data.response.playlist.PlayListModel
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SongListAdapter(val songList : ArrayList<SongInfo>) : RecyclerView.Adapter<SongListAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.item_image_IV)
        var title = itemView.findViewById<TextView>(R.id.item_title_text)
        var singer = itemView.findViewById<TextView>(R.id.item_singer_text)
        var delete = itemView.findViewById<TextView>(R.id.item_delete_text)

        fun bind(song : SongInfo){
            title.text = song.title

            singer.text = song.artistName


            if(song.jacket == ""){
                img.setImageResource(R.drawable.def_music)
            }else{
                val glide = Glide.with(itemView.context)
                glide.load(Uri.parse(song.jacket)).into(img)
            }

            delete.setOnClickListener {

                Log.d("Logd", "삭제삭제삭제")
            }

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_songlist, parent, false )
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }


}