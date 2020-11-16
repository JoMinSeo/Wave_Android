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
import com.narsha.wave_android.data.viewtype.SearchedViewType
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.R
import com.project.wave_v2.data.request.SearchBody
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.data.response.search.*
import com.project.wave_v2.data.viewtype.ReturnViewType
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
    var arraySearched: List<SearchSongInfo> = ArrayList()
    var arrayArtist : List<ArtistInfo> = ArrayList()
    var arrayAlbum : List<AlbumInfo> = ArrayList()
    var arrayCategory : List<Category> = ArrayList()

    var searched : SearchModel = SearchModel(arrayArtist, arrayAlbum, arraySearched, arrayCategory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SearchedViewModel::class.java)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        viewModel.searchModel!!.value = null

        val viewPager: ViewPager2 = view.findViewById(R.id.pager)
        val adapter = StateFragment(this)
        viewPager.adapter = adapter

        getAll()
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
                Log.d("log", searched.toString())
                getAPI(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if(s.isEmpty()){
                    getAll()
                }
                return false
            }
        })
    }
    private fun getAll(){
        API!!.getAll().enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val search = response.body()

                (arraySearched as ArrayList).clear()
                (arrayArtist as ArrayList).clear()
                (arrayAlbum as ArrayList).clear()

                if(search?.song!! != null){
                    for(i in search?.song!!.indices){
                        (arraySearched as ArrayList).add(SearchSongInfo(search.song!![i], SearchedViewType.ViewType.MUSIC))
                    }
                }
                if(search?.album!! != null){
                    for(i in search.album!!.indices){
                        (arrayAlbum as ArrayList).add(AlbumInfo(search.album!![i], SearchedViewType.ViewType.ALBUM))
                    }
                }
                if(search?.artist!! != null){
                    for(i in search.artist!!.indices){
                        (arrayArtist as ArrayList).add(ArtistInfo(search.artist!![i], SearchedViewType.ViewType.ARTIST))
                    }
                }



                viewModel.searchModel!!.value = searched
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
            }

        })
    }
    fun getAPI(s : String){
        API!!.getSearchInfo(SearchBody(s))!!.enqueue(object : Callback<SearchResult?> {
            override fun onResponse(call: Call<SearchResult?>, response: Response<SearchResult?>) {
                if(response.code() == 200){
                    val search = response.body()
                    Log.d("log", response.body().toString())

                    (arraySearched as ArrayList).clear()
                    (arrayArtist as ArrayList).clear()
                    (arrayAlbum as ArrayList).clear()
                    if(search?.song!! != null){
                        for(i in search?.song!!.indices){
                            (arraySearched as ArrayList).add(SearchSongInfo(search.song!![i], SearchedViewType.ViewType.MUSIC))
                        }
                    }
                    if(search?.album!! != null){
                        for(i in search.album!!.indices){
                            (arrayAlbum as ArrayList).add(AlbumInfo(search.album!![i], SearchedViewType.ViewType.ALBUM))
                        }
                    }
                    if(search?.artist!! != null){
                        for(i in search.artist!!.indices){
                            (arrayArtist as ArrayList).add(ArtistInfo(search.artist!![i], SearchedViewType.ViewType.ARTIST))
                        }
                    }


                    Log.d("log", arraySearched.toString())
                    Log.d("log", arrayAlbum.toString())
                    Log.d("log", arrayArtist.toString())

                    viewModel.searchModel!!.value = searched

                    val searching = SearchedAllAdapter(requireActivity(), requireContext(), viewModel.searchModel!!.value, ReturnViewType.ReturnType.ALL)
                    searching.setDataModel(viewModel.searchModel!!.value)
                    Log.d("log", "SearchFragment- ${searched.toString()} : ${viewModel.searchModel!!.value}")

                }else{
                    Log.d("log", response.message())
                }
            }

            override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
                Log.d("log", t.message)
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