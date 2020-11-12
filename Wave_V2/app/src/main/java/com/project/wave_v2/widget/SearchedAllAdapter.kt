package com.narsha.wave_android.view.adapter.search


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.project.wave_v2.R
import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.search.*
import com.project.wave_v2.data.viewtype.ReturnViewType
import com.project.wave_v2.view.activity.SongActivity
import com.project.wave_v2.view.fragment.searched.onclick.itemOnClick
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.sheet.BottomSheet
import java.util.regex.Pattern


class SearchedAllAdapter internal constructor(
    activity: ViewModelStoreOwner,
    context: Context,
    data: SearchModel?,
    returnType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: SearchModel? = null
    private var itemOnClick: itemOnClick? = null
    private var context: Context? = null
    private var viewHolder: RecyclerView.ViewHolder? = null
    private var allData: ArrayList<SearchObject> = ArrayList()
    private var returnType: Int = 0
    private var activity : ViewModelStoreOwner ?= null
    private var viewModel : SearchedViewModel ?= null
    private val youtube_link: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_sec: String =
        "[https]+\\:+\\/+\\/+[youtu]+\\.+[be]+\\/+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_thr: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"


    fun setDataModel(data: SearchModel?) {
        this.data = data
        Log.d("setData", "$data")
        dataReturn()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val context: Context = parent.context
        viewModel = ViewModelProvider(activity!!).get(SearchedViewModel::class.java)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (viewType == SearchedViewType.ViewType.ALBUM) {
            view = inflater.inflate(R.layout.item_searched_album, parent, false)
            viewHolder = AlbumHolder(view)
        } else if (viewType == SearchedViewType.ViewType.CATEGORY) {
            view = inflater.inflate(R.layout.item_category, parent, false)
            viewHolder = CategoryHolder(view)
        } else if (viewType == SearchedViewType.ViewType.ARTIST) {
            view = inflater.inflate(R.layout.item_searched_artist, parent, false)
            viewHolder = ArtistViewHolder(view)
        } else if (viewType == SearchedViewType.ViewType.MUSIC) {
            view = inflater.inflate(R.layout.item_music_searched, parent, false)
            viewHolder = MusicHolder(view)
        } else if (viewType == SearchedViewType.ViewType.SMALL_ALBUM) {
            view = inflater.inflate(R.layout.item_album_searched, parent, false)
            viewHolder = SmallAlbumHolder(view)
        } else if (viewType == SearchedViewType.ViewType.SMALL_ARTIST) {
            view = inflater.inflate(R.layout.item_artist_searched, parent, false)
            viewHolder = SmallArtistHolder(view)
        } else {
            view = inflater.inflate(R.layout.item_error, parent, false)
            viewHolder = ErrorHolder(view)
        }
        return viewHolder!!
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = allData[position].viewType

        Log.d("logging", allData.toString())
        if (type == SearchedViewType.ViewType.MUSIC) {
            if (type == SearchedViewType.ViewType.CATEGORY) {
                val category = allData[position] as Category
                viewHolder as CategoryHolder
                viewHolder.title.text = category.title
            } else {
                if (allData[position] is SearchSongInfo) {
                    val musicInfo = allData[position] as SearchSongInfo
                    viewHolder as MusicHolder
                    viewHolder.artist.text = musicInfo.song?.artistName
                    viewHolder.title.text = musicInfo.song?.title

                    Glide.with(viewHolder.itemView)
                        .load(musicInfo.song!!.jacket)
                        .into(viewHolder.imageCover)

                    viewHolder.moreButton.setOnClickListener {
                        val bottomSheet = BottomSheet(musicInfo.song!!.songId!!)
                        bottomSheet.show(
                            (context as FragmentActivity).supportFragmentManager,
                            bottomSheet.tag
                        )
                    }

                    Log.d("linkOfyoutube", musicInfo.song!!.songUrl!!)
                    viewHolder.playButton.setOnClickListener {
                        if (Pattern.matches(
                                youtube_link,
                                musicInfo.song!!.songUrl!!
                            )
                        ) {

                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(32, musicInfo.song!!.songUrl!!.length),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)

                        } else if (Pattern.matches(
                                youtube_link_sec,
                                musicInfo.song!!.songUrl!!
                            )
                        ) {
                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(17, musicInfo.song!!.songUrl!!.length),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)
                        } else if (Pattern.matches(
                                youtube_link_thr,
                                musicInfo.song!!.songUrl!!
                            )
                        ) {

                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(32, musicInfo.song!!.songUrl!!.length - 23),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)

                        }
                        viewModel!!.isViewing!!.value = true
                       // context!!.startActivity(intent)
                    }
                }
            }
        } else if (type == SearchedViewType.ViewType.ARTIST) {
            val artistInfo = allData[position] as ArtistInfo
            viewHolder as ArtistViewHolder
            viewHolder.title.text = artistInfo.artist!!.artistName
        } else if (type == SearchedViewType.ViewType.CATEGORY) {
            val category = allData[position] as Category
            viewHolder as CategoryHolder
            viewHolder.title.text = category.title
        } else if (type == SearchedViewType.ViewType.ALBUM) {
            val albumInfo = allData[position] as AlbumInfo
            viewHolder as AlbumHolder
            viewHolder.title.text = albumInfo.album!!.albumName
            Glide.with(viewHolder.itemView)
                .load(albumInfo.album!!.jacket)
                .into(viewHolder.imageCover)
        } else if(type == SearchedViewType.ViewType.SMALL_ALBUM){
            val albumInfo = allData[position] as AlbumInfo
            viewHolder as SmallAlbumHolder
            viewHolder.title.text = albumInfo.album!!.albumName
            Glide.with(viewHolder.itemView)
                .load(albumInfo.album!!.jacket)
                .into(viewHolder.imageCover)
        } else if(type == SearchedViewType.ViewType.SMALL_ARTIST){
            val artistInfo = allData[position] as ArtistInfo
            viewHolder as SmallArtistHolder
            viewHolder.title.text = artistInfo.artist!!.artistName
        }else {
            viewHolder as ErrorHolder
            viewHolder.error.text = "에러 발생"
        }


    }



    override fun getItemCount(): Int {
        return allData.size
    }

    override fun getItemViewType(position: Int): Int {
        return allData[position].viewType!!
    }

    inner class AlbumHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.musicName)
        var content: TextView = itemView.findViewById(R.id.musicDescription)
        var type: TextView = itemView.findViewById(R.id.musicType)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
    }
    inner class SmallAlbumHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.musicName)
        var content: TextView = itemView.findViewById(R.id.musicDescription)
        var type: TextView = itemView.findViewById(R.id.musicType)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
    }

    inner class SmallArtistHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var title: TextView = itemView.findViewById(R.id.artistName)
        var type: TextView = itemView.findViewById(R.id.artistType)
    }

    inner class CategoryHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var title: TextView = itemView.findViewById(R.id.titleOfCategory)
    }

    inner class ArtistViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {

        var title: TextView = itemView.findViewById(R.id.artistName)
        var type: TextView = itemView.findViewById(R.id.artistType)

    }

    inner class MusicHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.musicName)
        var playButton: Button = itemView.findViewById(R.id.playButton)
        var artist: TextView = itemView.findViewById(R.id.artistName)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
        var moreButton: Button = itemView.findViewById(R.id.moreButton)

    }

    inner class ErrorHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var error: TextView = itemView.findViewById(R.id.error)

    }

    init {
        this.data = data
        this.context = context
        this.returnType = returnType
        this.activity = activity
        dataReturn()
    }

    private fun dataReturn() {
        data?.let {
            allData.clear()
            if (returnType == ReturnViewType.ReturnType.ALL) {
                it.album?.let { a ->
                    if (a.isNotEmpty()) {
                        val iterator = a.iterator()
                        val albumInfo = iterator.next()
                        albumInfo!!.viewType = SearchedViewType.ViewType.ALBUM
                        allData.add(albumInfo)
                        }
                }
                it.artist?.let { a ->
                    if (a.isNotEmpty()) {
                        val category = Category("아티스트", SearchedViewType.ViewType.CATEGORY)
                        allData.add(category)
                        val iterator = a.iterator()
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.ARTIST
                        allData.add(artistInfo)
                    }
                }
                it.song?.let { a ->
                    if (a.isNotEmpty()) {
                        val category = Category("곡", SearchedViewType.ViewType.CATEGORY)
                        allData.add(category)
                        val iterator = a.iterator()
                        var index = 0
                        while (iterator.hasNext()) {
                            val songInfo = iterator.next()
                            songInfo!!.viewType = SearchedViewType.ViewType.MUSIC
                            allData.add(songInfo)
                            index++
                        }
                    }
                }
            } else if (returnType == ReturnViewType.ReturnType.MUSIC) {
                it.album?.let { a ->
                    if (a.isNotEmpty()) {
                        val iterator = a.iterator()
                        val albumInfo = iterator.next()
                        albumInfo!!.viewType = SearchedViewType.ViewType.ALBUM
                    }
                }
                it.song?.let { a ->
                    if (a.isNotEmpty()) {
                        val iterator = a.iterator()
                        var index = 0
                        while (iterator.hasNext()) {
                            val songInfo = iterator.next()
                            songInfo!!.viewType = SearchedViewType.ViewType.MUSIC
                            allData.add(songInfo)
                            index++
                        }
                    }
                }
            } else if (returnType == ReturnViewType.ReturnType.ARTIST) {
                it.artist?.let { a ->
                    val iterator = a.iterator()
                    var index = 0
                    while (iterator.hasNext()) {
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.ARTIST
                        allData.add(artistInfo)
                        index++
                    }
                }
            } else if (returnType == ReturnViewType.ReturnType.ALBUM) {
                it.album?.let { a ->
                    val iterator = a.iterator()
                    var index = 0
                    while (iterator.hasNext()) {
                        val albumInfo = iterator.next()
                        albumInfo!!.viewType = SearchedViewType.ViewType.ALBUM
                        allData.add(albumInfo)
                        index++
                    }
                }
            } else if(returnType == ReturnViewType.ReturnType.SMALL_ALBUM) {
                it.album?.let { a ->
                    if (a.isNotEmpty()) {
                        val iterator = a.iterator()
                        var index = 0
                        while (iterator.hasNext()) {
                            val albumInfo = iterator.next()
                            albumInfo!!.viewType = SearchedViewType.ViewType.SMALL_ALBUM
                            allData.add(albumInfo)
                            index++
                        }
                    }
                }
            } else if(returnType == ReturnViewType.ReturnType.SMALL_ARTIST){
                it.artist?.let { a ->
                    val iterator = a.iterator()
                    var index = 0
                    while (iterator.hasNext()) {
                        val artistInfo = iterator.next()
                        artistInfo!!.viewType = SearchedViewType.ViewType.SMALL_ARTIST
                        allData.add(artistInfo)
                        index++
                    }
                }
            }else{

            }
        }
    }
}