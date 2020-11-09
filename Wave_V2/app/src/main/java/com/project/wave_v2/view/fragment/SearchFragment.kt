package com.project.wave_v2.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.wave_v2.R
import com.project.wave_v2.data.request.SearchBody
import com.project.wave_v2.data.response.*
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.StateFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchFragment : Fragment() {
    var API : Service? = null
    var retrofit : Retrofit? = null

    var viewModel : SearchedViewModel = SearchedViewModel()
    var arraySerached: List<SearchSongInfo> ?= arrayListOf()
    var arrayArtist : List<ArtistInfo> ?= arrayListOf()
    var arrayAlbum : List<AlbumInfo> ?= arrayListOf()
    var arrayCategory : List<Category> ?= arrayListOf()

    var searched : SearchModel = SearchModel(arrayArtist, arrayAlbum, arraySerached, arrayCategory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchedViewModel::class.java)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        Log.d("log", "${API}")

        val viewPager: ViewPager2 = view.findViewById(R.id.pager)
        val adapter = StateFragment(this)
        viewPager.adapter = adapter
        val tabLayout: TabLayout = view.findViewById(R.id.searched_tab)
        TabLayoutMediator(
                tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text =
                    if (position + 1 == 1) "전체" else if (position + 1 == 2) "앨범" else if (position + 1 == 3) "곡" else "아티스트"
        }.attach()
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                getAPI(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    fun getAPI(s : String){
        API!!.getSearchInfo(SearchBody(s))!!.enqueue(object : Callback<SearchModel?> {
            override fun onResponse(call: Call<SearchModel?>, response: Response<SearchModel?>) {

            }

            override fun onFailure(call: Call<SearchModel?>, t: Throwable) {

            }

        })
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}