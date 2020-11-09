package com.project.wave_v2.view.fragment.searched.searched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.data.searched.SearchedData
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.R
import com.project.wave_v2.data.response.SearchSongInfo
import java.util.*

class MusicSearchedFragment : Fragment() {
    var arraySerached: ArrayList<SearchSongInfo> = ArrayList<SearchSongInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_searched, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arraySerached.add(SearchSongInfo(0,"사랑이란 멜로는 없어", 0, "전상근", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(1,"그대 행복해야 해요", 0, "전상근",0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(2,"사랑은 지날수록 더욱 선명하게 남아", 0, "전상근", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(3,"어디에도", 0, "이수", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(4,"사계", 0, "이수", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(5,"넘쳐흘러", 0, "이수", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(6,"야생화", 0, "박효신", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(7,"오래된 노래", 0, "스탠딩에그", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchSongInfo(8,"너를 만나", 0, "폴킴", 0,0,0, "https://youtu.be/o6QeCDp2oyU", 0, "", SearchedViewType.ViewType.MUSIC))
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearchedMusic)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = SearchedAllAdapter(requireContext(), arraySerached) // Adapter 등록
    }
}