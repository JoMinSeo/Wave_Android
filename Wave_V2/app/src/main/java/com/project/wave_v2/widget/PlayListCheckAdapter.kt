package com.project.wave_v2.widget

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.request.playlist.PlayListSongBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.activity.SongListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PlayListCheckAdapter(var playList : ArrayList<MyPlayListModel>) : RecyclerView.Adapter<PlayListCheckAdapter.Holder>(){

    var API: Service? = null
    lateinit var retrofit: Retrofit

    fun setData(playList: ArrayList<MyPlayListModel>){
        this.playList = playList
        notifyDataSetChanged()
    }

    fun setDummy(){
        for(i in 0..9){
            playList.add(MyPlayListModel(i,i,"","user${i}",false))
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title: TextView = itemView.findViewById<TextView>(R.id.item_title_text)
        var img: ImageView = itemView.findViewById<ImageView>(R.id.item_image_IV)
        var radioCheck : RadioButton = itemView.findViewById(R.id.radioAddButton)

        fun bind(list : MyPlayListModel) {

            retrofit = RetrofitClient.getInstance()
            API = RetrofitClient.getService()


            title.text = list.listName

            val glide = Glide.with(itemView.context)
            glide.load(Uri.parse(list.jacket)).into(img)

            radioCheck.setOnCheckedChangeListener { _, b ->
                list.check = true
                Log.d("mp_log", list.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_dialog, parent, false )
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }

}