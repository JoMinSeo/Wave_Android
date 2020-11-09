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
import java.util.*

class MusicSearchedFragment : Fragment() {
    var arraySerached: ArrayList<SearchedData> = ArrayList<SearchedData>()
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
        arraySerached.add(SearchedData("사랑이란 멜로는 없어", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("그대 행복해야 해요", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(
            SearchedData(
                "사랑은 지날수록 더욱 선명하게 남아",
                null,
                null,
                SearchedViewType.ViewType.MUSIC
            )
        )
        arraySerached.add(SearchedData("어디에도", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("사계", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("넘쳐흘러", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("야생화", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("오래된 노래", null, null, SearchedViewType.ViewType.MUSIC))
        arraySerached.add(SearchedData("너를 만나", null, null, SearchedViewType.ViewType.MUSIC))
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearchedMusic)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = SearchedAllAdapter(arraySerached) // Adapter 등록
    }
}