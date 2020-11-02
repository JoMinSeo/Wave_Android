package com.narsha.wave_android.view.adapter.search


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.R
import com.narsha.wave_android.data.searched.SearchedData
import com.narsha.wave_android.data.viewtype.SearchedViewType

class SearchedAllAdapter internal constructor(dataList: ArrayList<SearchedData>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myDataList: ArrayList<SearchedData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val context: Context = parent.context

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (viewType == SearchedViewType.ViewType.MUSIC_BIG) {
            view = inflater.inflate(R.layout.item_music_first, parent, false)
             return MusicBigHolder(view)
        } else if (viewType == SearchedViewType.ViewType.CATEGORY) {
            view = inflater.inflate(R.layout.item_category, parent, false)
             return  CategoryHolder(view)
        } else if (viewType == SearchedViewType.ViewType.ARTIST){
            view = inflater.inflate(R.layout.item_searched_artist, parent, false)
            return ArtistViewHolder(view)
        } else {
            view = inflater.inflate(R.layout.item_music_searched, parent, false)
            return MusicHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MusicBigHolder) {

            viewHolder.title.text = myDataList!![position].title
            viewHolder.content.text = myDataList!![position].description

        } else if (viewHolder is MusicHolder) {

            viewHolder.title.text = myDataList!![position].title

        } else if(viewHolder is ArtistViewHolder) {

            viewHolder.title.text = myDataList!![position].title

        } else if(viewHolder is CategoryHolder){
            viewHolder.title.text = myDataList!![position]!!.title
        }
    }

    override fun getItemCount(): Int {
        return myDataList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return myDataList!![position].viewType!!
    }

    inner class MusicBigHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        var type : TextView = itemView.findViewById(R.id.musicType)

    }
    init {
        myDataList = dataList
    }
}