package com.narsha.wave_android.view.adapter.search


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.project.wave_v2.R
import com.project.wave_v2.data.dao.playinglist.PlayingRoomDatabase
import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.search.*
import com.project.wave_v2.data.viewtype.ReturnViewType
import com.project.wave_v2.view.fragment.searched.onclick.OnItemClick
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.sheet.BottomSheet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class SearchedAllAdapter internal constructor(
    activity: ViewModelStoreOwner,
    context: Context,
    data: SearchModel?,
    returnType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: SearchModel? = null
    private var positionCheck = 0
    private var isStartViewCheck = true
    private var context: Context? = null
    private var viewHolder: RecyclerView.ViewHolder? = null
    private var allData: ArrayList<SearchObject> = ArrayList()
    private var returnType: Int = 0
    private var activity : ViewModelStoreOwner ?= null
    private var viewModel : SearchedViewModel ?= null
    private var db : PlayingRoomDatabase ?= null
    private val youtube_link: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_sec: String =
        "[https]+\\:+\\/+\\/+[youtu]+\\.+[be]+\\/+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_thr: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_fou : String =
            "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[index]+\\=[0-9]+"
    var modifyList : List<PlayModel> = arrayListOf()

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

        db = Room.databaseBuilder(
                context,
                PlayingRoomDatabase::class.java, "PlayingList").build()

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

                    if (isStartViewCheck) {
                        if (position > 6) isStartViewCheck = false
                    } else {
                        if (position > positionCheck) {
                            viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
                        } else {
                            viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.rising_up)
                        }
                    }

                    viewHolder.artist.text = musicInfo.song?.artistName
                    viewHolder.title.text = musicInfo.song?.title

                    Glide.with(viewHolder.itemView)
                        .load(musicInfo.song!!.jacket)
                        .into(viewHolder.imageCover)

                    viewHolder.moreButton.setOnClickListener {
                        val bottomSheet = BottomSheet(musicInfo.song!!.songId!!, 0, musicInfo.song!!.title!!, musicInfo.song!!.artistName!!, musicInfo.song!!)
                        bottomSheet.show(
                            (context as FragmentActivity).supportFragmentManager,
                            bottomSheet.tag
                        )
                    }

                    Log.d("linkOfyoutube", musicInfo.song!!.songUrl!!)
                    viewHolder.playButton.setOnClickListener {
                        val db = Room.databaseBuilder(
                            context!!,
                            PlayingRoomDatabase::class.java, "PlayingList").build()

                        GlobalScope.launch {
                            async {
                                db.playingList().songInsert(musicInfo.song!!)
                                convertList()
                            }.await()
                        }

                        if (Pattern.matches(
                                youtube_link,
                                musicInfo.song!!.songUrl!!
                            )
                        ) {

                            Log.d("debugged" , musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf('=', 0) + 1, musicInfo.song!!.songUrl!!.length))
                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf('=', 0) + 1, musicInfo.song!!.songUrl!!.length),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)

                        } else if (Pattern.matches(
                                youtube_link_sec,
                                musicInfo.song!!.songUrl!!
                            )
                        ) {
                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf("e/") + 2, musicInfo!!.song!!.songUrl!!.length),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)
                        } else if (Pattern.matches(
                                youtube_link_thr,
                                musicInfo.song!!.songUrl!!
                                )
                        ) {
                            Log.d("debugged" , musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf('=', 0)+ 1, musicInfo!!.song!!.songUrl!!.indexOf('&', 0)))

                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf('=', 0)+ 1, musicInfo!!.song!!.songUrl!!.indexOf('&', 0)),
                                musicInfo.song!!.title!!,
                                musicInfo.song!!.artistName!!)

                        } else if(Pattern.matches(youtube_link_fou, musicInfo.song!!.songUrl!!)){
                            Log.d("debugged" , musicInfo.song!!.songUrl!!.substring(musicInfo!!.song!!.songUrl!!.indexOf('=', 0)+ 1, musicInfo!!.song!!.songUrl!!.indexOf('&', 0)))

                            viewModel!!.playingModel!!.value = PlayModel(musicInfo.song!!.jacket,
                                    musicInfo.song!!.songUrl!!.substring( musicInfo!!.song!!.songUrl!!.indexOf('=', 0)+ 1, musicInfo!!.song!!.songUrl!!.indexOf('&', 0)),
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
            viewHolder.artistTitle.text = albumInfo.album!!.artistName
            viewHolder.title.text = albumInfo.album!!.albumName
            Glide.with(viewHolder.itemView)
                .load(albumInfo.album!!.jacket)
                .into(viewHolder.imageCover)
        } else if(type == SearchedViewType.ViewType.SMALL_ALBUM){
            val albumInfo = allData[position] as AlbumInfo
            viewHolder as SmallAlbumHolder
            viewHolder.title.text = albumInfo.album!!.albumName
            viewHolder.artistTitle.text = albumInfo.album!!.artistName
            Glide.with(viewHolder.itemView)
                .load(albumInfo.album!!.jacket)
                .into(viewHolder.imageCover)

            if (isStartViewCheck) {
                if (position > 6) isStartViewCheck = false
            } else {
                if (position > positionCheck) {
                    viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
                } else {
                    viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.rising_up)
                }
            }
        } else if(type == SearchedViewType.ViewType.SMALL_ARTIST){
            val artistInfo = allData[position] as ArtistInfo
            viewHolder as SmallArtistHolder
            viewHolder.title.text = artistInfo.artist!!.artistName

            if (isStartViewCheck) {
                if (position > 6) isStartViewCheck = false
            } else {
                if (position > positionCheck) {
                    viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
                } else {
                    viewHolder.viewAnimation.animation = AnimationUtils.loadAnimation(context, R.anim.rising_up)
                }
            }
        }else {
            viewHolder as ErrorHolder
            viewHolder.error.text = "에러 발생"
        }


    }

    fun convertList(){
        GlobalScope.launch {
            async {
                for(i in (db as PlayingRoomDatabase).playingList().getAll()){
                    (modifyList as ArrayList).add((modifyList as ArrayList).size,PlayModel(i.jacket,getLink(i.songUrl!!),i.title,i.artistName))
                }
            }
        }
        viewModel!!.playingModelList!!.postValue(modifyList)
    }

    fun getLink(songLink : String) : String {
        var result: String = ""
        if (Pattern.matches(youtube_link, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.length)
            Log.d("logPattern1", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_sec, songLink)) {
            result = songLink.substring(songLink.indexOf("e/") + 2, songLink.length)
            Log.d("logPattern2", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_thr, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0)+ 1, songLink.indexOf('&', 0))
            Log.d("logPattern3", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_fou, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.indexOf('&', 0))
            Log.d("logPattern4", "result : $result")
            return result
        }
        return ""
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
        var artistTitle : TextView = itemView.findViewById(R.id.artistName)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
    }
    inner class SmallAlbumHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.musicName)
        var artistTitle : TextView = itemView.findViewById(R.id.artistName)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
        var viewAnimation : ConstraintLayout = itemView.findViewById(R.id.viewAnimation)
    }

    inner class SmallArtistHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var title: TextView = itemView.findViewById(R.id.artistName)
        var viewAnimation : ConstraintLayout = itemView.findViewById(R.id.viewAnimation)
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

    }

    inner class MusicHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.findViewById(R.id.musicName)
        var playButton: Button = itemView.findViewById(R.id.playButton)
        var artist: TextView = itemView.findViewById(R.id.artistName)
        var imageCover: ImageView = itemView.findViewById(R.id.imageCover)
        var moreButton: Button = itemView.findViewById(R.id.moreButton)
        var viewAnimation : ConstraintLayout = itemView.findViewById(R.id.viewAnimation)

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