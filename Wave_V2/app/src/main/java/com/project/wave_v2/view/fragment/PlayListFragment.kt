package com.project.wave_v2.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.widget.PlayListAdapter
import kotlinx.android.synthetic.main.fragment_playlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PlayListFragment : Fragment() {

    lateinit var navController: NavController
    var playList = ArrayList<MyPlayListModel>()
    val playListAdapter : PlayListAdapter = PlayListAdapter(playList)


    lateinit var API: Service
    lateinit var retrofit: Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        retrofit = RetrofitClient.getInstance()
        API = retrofit.create(Service::class.java)

        selectedPlayList.adapter = playListAdapter
        selectedPlayList.setHasFixedSize(true)


        API.myList(CallPlayListBody(userId = "user"))
                .enqueue(object : Callback<List<MyPlayListModel>> {
                    override fun onResponse(call: Call<List<MyPlayListModel>>, response: Response<List<MyPlayListModel>>) {
                        playList.clear()
                        for(i in 0 until response.body()?.size!!){
                            playList.add(response.body()!![i])
                        }
                        playListAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<List<MyPlayListModel>>, t: Throwable) {
                    }


                })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }
}


