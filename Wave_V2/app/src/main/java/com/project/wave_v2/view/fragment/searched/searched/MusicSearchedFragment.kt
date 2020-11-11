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
import com.project.wave_v2.view.viewmodel.SearchedViewModel

class MusicSearchedFragment : Fragment() {

    var viewModel : SearchedViewModel = SearchedViewModel()
    var searchModel : SearchModel?= null

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

        viewModel = ViewModelProvider(requireActivity()).get(SearchedViewModel::class.java)

        searchModel = viewModel.searchModel!!.value

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearchedMusic)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = SearchedAllAdapter(requireContext(), searchModel) // Adapter 등록
    }
}