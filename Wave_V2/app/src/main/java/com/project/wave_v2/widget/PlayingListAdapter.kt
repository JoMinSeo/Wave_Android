package com.project.wave_v2.widget

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.dao.playinglist.PlayingRoomDatabase
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.data.response.search.Song
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.fragment.searched.onclick.OnItemClick
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.logging.Handler

class PlayingListAdapter(var playList: List<Song>, var context: Context, var activity: LifecycleOwner, var viewModel: SearchedViewModel, var songTitle: String, var listener: OnItemClick) : RecyclerView.Adapter<PlayingListAdapter.Holder>(){

    var API: Service? = null
    lateinit var retrofit: Retrofit
    var playListModel = ArrayList<MyPlayListModel>()
    val playListAdapter: PlayListCheckAdapter = PlayListCheckAdapter(playListModel)

    fun setData(playList: List<Song>){
        this.playList = playList
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title: TextView = itemView.findViewById<TextView>(R.id.musicName)
        var img: ImageView = itemView.findViewById<ImageView>(R.id.imageCover)
        var deleteButton : ImageButton = itemView.findViewById<ImageButton>(R.id.delete)
        val nowPlaying : ConstraintLayout = itemView.findViewById(R.id.nowPlaying)
        val isPlayingNow : TextView = itemView.findViewById(R.id.isPlaying)



        fun bind(position: Int, song: Song) {
            val db = Room.databaseBuilder(
                    context!!,
                    PlayingRoomDatabase::class.java, "PlayingList").build()

                title.text = song.title


                viewModel.songTitle!!.observe(activity, Observer {
                    if(viewModel.songTitle!!.value == song.title){
                        Log.d("observe", "observing now")
                        nowPlaying.setBackgroundColor(context.getColor(R.color.colorPrimary))
                        title.setTextColor(Color.WHITE)
                        isPlayingNow.visibility = View.VISIBLE
                        isPlayingNow.setTextColor(Color.WHITE)
                    }else{
                        nowPlaying.setBackgroundColor(Color.WHITE)
                        title.setTextColor(Color.BLACK)
                        isPlayingNow.visibility = View.GONE
                    }
                })





            val glide = Glide.with(itemView.context)
            glide.load(Uri.parse(song.jacket)).into(img)

            itemView.setOnClickListener {
                    listener.OnItemClick(song)
                    title.setTextColor(Color.CYAN)
                    isPlayingNow.visibility = View.VISIBLE
                    isPlayingNow.setTextColor(Color.WHITE)
                    viewModel.songTitle!!.value = song.title

            }

            deleteButton.setOnClickListener {
                GlobalScope.launch {
                    async {
                        db.playingList().songDelete(song)
                    }.await()
                }
                onDeleteButton(song)
            }

        }
    }


    fun onDeleteButton(song: Song){
        (playList as ArrayList).remove(song)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position, playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }

    init {
        this.context = context
    }

}