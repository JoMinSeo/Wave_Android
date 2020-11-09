package com.project.wave_v2.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.wave_v2.R
import com.project.wave_v2.data.request.SearchBody
import com.project.wave_v2.data.response.SearchModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.widget.StateFragment
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class SearchFragment : Fragment() {
    lateinit var API: Service
    lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()
        API = retrofit.create(Service::class.java)

        val adapter = StateFragment(this)
        pager.adapter = adapter


        TabLayoutMediator(
            searched_tab, pager
        ) { tab: TabLayout.Tab, position: Int -> tab.text = if (position + 1 == 1) "전체" else if (position + 1 == 2) "앨범" else if (position + 1 == 3) "곡" else "아티스트" }.attach()

        val searchView: SearchView = view.findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                getAPI(s!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                return false
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    fun getAPI(keyword : String){

        API.getSearchInfo(SearchBody(keyword)).enqueue(object : Callback<SearchModel?> {
            override fun onResponse(call: Call<SearchModel?>, response: Response<SearchModel?>) {
                if(response.code() == 200){
                    Log.d("log_searched", response.body().toString())
                    Toast.makeText(requireContext(), response.body().toString(), Toast.LENGTH_LONG).show()
                }else{
                    Log.d("log_searched", response.body().toString())
                }
            }

            override fun onFailure(call: Call<SearchModel?>, t: Throwable) {
                Log.d("log_searched", t.message)
            }


        })
    }
}