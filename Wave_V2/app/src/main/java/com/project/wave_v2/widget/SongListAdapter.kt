package com.project.wave_v2.widget

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
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

class SongListAdapter(val songList : ArrayList<SongInfo>, val listId : Int, val context : Context) : RecyclerView.Adapter<SongListAdapter.Holder>(){

    var API: Service? = null
    lateinit var retrofit: Retrofit

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.item_image_IV)
        var title = itemView.findViewById<TextView>(R.id.item_title_text)
        var singer = itemView.findViewById<TextView>(R.id.item_singer_text)
        var delete = itemView.findViewById<TextView>(R.id.item_delete_text)

        fun bind(song : SongInfo){
            retrofit = RetrofitClient.getInstance()
            API = RetrofitClient.getService()
            title.text = song.title
            singer.text = song.artistName


            if(song.jacket == ""){
                img.setImageResource(R.drawable.def_music)
            }else{
                val glide = Glide.with(itemView.context)
                glide.load(Uri.parse(song.jacket)).into(img)
            }

            delete.setOnClickListener {
                API?.delPlayListSong(PlayListSongBody(listId, song.songId))!!.enqueue(object : Callback<ResultModel> {
                    override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                        Toast.makeText(context , "성공적으로 삭제되었습니다",Toast.LENGTH_LONG).show()
                        for(i in songList.indices){
                            if(i != 0){
                                if(songList[i - 1].songId == song.songId){
                                    songList.remove(song)
                                }
                            }else{
                                if(songList[i].songId == song.songId){
                                    songList.remove(song)
                                }
                            }
                        }
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                        Toast.makeText(context , "삭제 실패 메세지 : "+t.message ,Toast.LENGTH_LONG).show()
                    }

                })
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