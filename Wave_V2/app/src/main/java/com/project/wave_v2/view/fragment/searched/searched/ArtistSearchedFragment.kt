package com.project.wave_v2.view.fragment.searched.searched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.R
import java.util.*

class ArtistSearchedFragment : Fragment() {
//    var arraySerached: ArrayList<SearchedData> = ArrayList<SearchedData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        arraySerached.add(SearchedData("전상근", null, null, SearchedViewType.ViewType.ARTIST))
//        arraySerached.add(SearchedData("이수", null, null, SearchedViewType.ViewType.ARTIST))
//        arraySerached.add(SearchedData("나얼", null, null, SearchedViewType.ViewType.ARTIST))
//        arraySerached.add(SearchedData("김범수", null, null, SearchedViewType.ViewType.ARTIST))
//        arraySerached.add(SearchedData("임재현", null, null, SearchedViewType.ViewType.ARTIST))
//        arraySerached.add(SearchedData("박효신", null, null, SearchedViewType.ViewType.ARTIST))
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearchedArtist)
//        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = manager // LayoutManager 등록
//        recyclerView.adapter = SearchedAllAdapter(requireContext(), arraySerached) // Adapter 등록
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_searched, container, false)
    }
}