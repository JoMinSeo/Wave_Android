package com.project.wave_v2.view.fragment.searched.searched

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.R
import com.project.wave_v2.data.response.*
import com.project.wave_v2.data.response.search.SearchModel
import com.project.wave_v2.data.viewtype.ReturnViewType
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import java.util.*
import kotlin.collections.ArrayList

class AllSearchedFragment : Fragment() {
    private var positionCheck = 0
    private var isStartViewCheck = true
    var viewModel : SearchedViewModel = SearchedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SearchedViewModel::class.java)


        val searched = SearchedAllAdapter(requireActivity() ,requireContext(), viewModel.searchModel!!.value, ReturnViewType.ReturnType.ALL)


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerSearched)
        val manager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager // LayoutManager 등록
        recyclerView.adapter = searched

        viewModel.searchModel!!.observe(viewLifecycleOwner, {
            Log.d("log", "AllSearched -${viewModel.searchModel!!.value}")
            searched.setDataModel(viewModel.searchModel!!.value)
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.d("start", "onStart")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_searched, container, false)

        return view
    }

}