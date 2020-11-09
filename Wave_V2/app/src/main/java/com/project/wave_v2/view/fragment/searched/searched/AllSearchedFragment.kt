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

class AllSearchedFragment : Fragment() {
    var arraySerached: ArrayList<SearchedData> = ArrayList<SearchedData>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_searched, container, false)
        arraySerached.add(
            SearchedData(
                "진짜 죽여주는 노래",
                null,
                "ㅎㅇ",
                SearchedViewType.ViewType.MUSIC_BIG
            )
        )
        arraySerached.add(SearchedData("아티스트", null, null, SearchedViewType.ViewType.CATEGORY))
        arraySerached.add(SearchedData("전상근", null, null, SearchedViewType.ViewType.ARTIST))
        arraySerached.add(SearchedData("곡", null, null, SearchedViewType.ViewType.CATEGORY))
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
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearched)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = SearchedAllAdapter(arraySerached) // Adapter 등록
        return view
    }
}