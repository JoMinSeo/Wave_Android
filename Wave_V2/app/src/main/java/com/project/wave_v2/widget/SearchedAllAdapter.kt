package com.narsha.wave_android.view.adapter.search


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.project.wave_v2.R
import com.project.wave_v2.data.response.*
import com.project.wave_v2.view.activity.SongActivity
import com.project.wave_v2.view.fragment.searched.onclick.itemOnClick
import java.util.*
import kotlin.collections.ArrayList

class SearchedAllAdapter internal constructor(context: Context, data : SearchModel?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data : SearchModel? = null
    private var itemOnClick : itemOnClick ?= null
    private var context : Context? = null
    private var viewHolder : RecyclerView.ViewHolder?= null
    private var allData : ArrayList<SearchObject> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val context: Context = parent.context

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (viewType == SearchedViewType.ViewType.ALBUM) {
            view = inflater.inflate(R.layout.item_album, parent, false)
            viewHolder = AlbumHolder(view)
        } else if (viewType == SearchedViewType.ViewType.CATEGORY) {
            view = inflater.inflate(R.layout.item_category, parent, false)
             viewHolder = CategoryHolder(view)
        } else if (viewType == SearchedViewType.ViewType.ARTIST){
            view = inflater.inflate(R.layout.item_searched_artist, parent, false)
            viewHolder = ArtistViewHolder(view)
        } else if(viewType == SearchedViewType.ViewType.MUSIC) {
            view = inflater.inflate(R.layout.item_music_searched, parent, false)
            viewHolder = MusicHolder(view)
        } else {
            view = inflater.inflate(R.layout.item_error, parent, false)
            viewHolder = ErrorHolder(view)
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = allData[position].viewType
        if (type == SearchedViewType.ViewType.MUSIC) {
            val musicInfo = allData[position] as SearchSongInfo
            viewHolder as MusicHolder
            viewHolder.title.text = musicInfo.title
            viewHolder.playButton.setOnClickListener {
                val intent = Intent(context, SongActivity::class.java);
                intent.putExtra("link", musicInfo.songUrl!!.substring(17, musicInfo.songUrl!!.length))
                context!!.startActivity(intent)
            }
        } else if( type == SearchedViewType.ViewType.ARTIST) {
            val artistInfo = allData[position] as ArtistInfo
            viewHolder as ArtistViewHolder
            viewHolder.title.text = artistInfo.artistName
        } else if(type == SearchedViewType.ViewType.CATEGORY){
            val category = allData[position] as Category
            viewHolder as CategoryHolder
            viewHolder.title.text = category.title
        }else if( type == SearchedViewType.ViewType.ALBUM) {
            val albumInfo = allData[position] as AlbumInfo
            viewHolder as AlbumHolder
            viewHolder.title.text = albumInfo.artistName
        } else if(viewHolder is ErrorHolder){
            viewHolder.error.text = "에러 발생"
        }
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun getItemViewType(position: Int): Int {
        return allData[position].viewType!!
    }

    inner class AlbumHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.musicName)
        var content : TextView = itemView.findViewById(R.id.musicDescription)
        var type : TextView = itemView.findViewById(R.id.musicType)
    }

    inner class CategoryHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.titleOfCategory)
    }

    inner class ArtistViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.artistName)
        var type : TextView = itemView.findViewById(R.id.artistType)

    }
    inner class MusicHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.musicName)
        var playButton : Button = itemView.findViewById(R.id.playButton)
        var type : TextView = itemView.findViewById(R.id.musicType)

    }
    inner class ErrorHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var error : TextView = itemView.findViewById(R.id.error)

    }
    init {
        this.data = data
        this.context = context
        data?.let{
            allData.clear()
            it.artist?.let{ a->
                if(a.isNotEmpty()) {
                    val category = Category("ARTIST", SearchedViewType.ViewType.CATEGORY)
                    allData.add(category)
                    val iterator = a.iterator()
                    while (iterator.hasNext()) {
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.ARTIST
                        allData.add(artistInfo)
                    }
                }
            }
            it.album?.let{a->
                if(a.isNotEmpty()) {
                    val category= Category("ALBUM", SearchedViewType.ViewType.CATEGORY)
                    allData.add(category)
                    val iterator = a.iterator()
                    while (iterator.hasNext()) {
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.ALBUM
                        allData.add(artistInfo)
                    }
                }
            }
            it.song?.let{a->
                if(a.isNotEmpty()) {
                    val category= Category("SONG", SearchedViewType.ViewType.CATEGORY)
                    allData.add(category)
                    val iterator = a.iterator()
                    var index = 0
                    while (iterator.hasNext()) {
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.MUSIC
                        allData.add(artistInfo)
                        index ++
                    }
                }
            }
        }
    }
}