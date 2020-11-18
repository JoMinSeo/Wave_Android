package com.project.wave_v2.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.viewmodel.CallPlayListViewModel
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.PlayListAdapter
import com.project.wave_v2.widget.sheet.BottomSheet
import kotlinx.android.synthetic.main.fragment_playlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PlayListFragment : Fragment() {

    lateinit var navController: NavController

    var API: Service? = null
    lateinit var retrofit: Retrofit
    lateinit var viewModel: CallPlayListViewModel
    lateinit var viewModelSearch : SearchedViewModel

    var playList = ArrayList<MyPlayListModel>()
    var playListAdapter: PlayListAdapter ?= null


    override fun onPause() {
        super.onPause()
        Log.d("Logd", "PlayListFragment onPause")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CallPlayListViewModel::class.java)
        viewModelSearch = ViewModelProvider(requireActivity()).get(SearchedViewModel::class.java)


        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")

        navController = Navigation.findNavController(view)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        playListAdapter = PlayListAdapter(playList, requireContext(), prefs.getString("userId", "user")!!)
        selectedPlayList.adapter = playListAdapter
        selectedPlayList.setHasFixedSize(true)

        callPlayList(id)

        addList_Btn.setOnClickListener {
            var dialog = AddPlayListFragment()
            fragmentManager?.let { it1 -> dialog.show(it1, "addPlayList") }
        }

        with(viewModel) {
            addFinish.observe(viewLifecycleOwner, Observer {
                callPlayList(id)
            })
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }


    private fun callPlayList(id: String?) {
        API?.myList(CallPlayListBody(userId = id))
                ?.enqueue(object : Callback<List<MyPlayListModel>> {
                    override fun onResponse(call: Call<List<MyPlayListModel>>, response: Response<List<MyPlayListModel>>) {
                        playList.clear()
                        for (i in 0 until response.body()?.size!!) {
                            playList.add(0, response.body()!![i])
                        }
                        playListAdapter!!.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<List<MyPlayListModel>>, t: Throwable) {
                    }


                })
    }
}


