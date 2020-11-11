package com.project.wave_v2.view.fragment.searched.searched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.R
import com.project.wave_v2.data.response.search.SearchModel
import com.project.wave_v2.data.viewtype.ReturnViewType
import com.project.wave_v2.view.viewmodel.SearchedViewModel

class AlbumSearchedFragment : Fragment() {
    var viewModel : SearchedViewModel?= null
    var searched: SearchModel?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(SearchedViewModel::class.java)
        searched = viewModel!!.searchModel!!.value

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearchedAlbum)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = SearchedAllAdapter(requireContext(), searched, ReturnViewType.ReturnType.ALBUM) // Adapter 등록
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_searched, container, false)
    }
}