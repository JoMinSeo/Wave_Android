package com.project.wave_v2.widget.sheet

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.viewmodel.CallPlayListViewModel
import com.project.wave_v2.widget.PlayListAdapter
import com.project.wave_v2.widget.PlayListCheckAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class BottomSheet() : BottomSheetDialogFragment() {

    var API: Service? = null
    lateinit var retrofit: Retrofit

    var playList = ArrayList<MyPlayListModel>()
    val playListAdapter: PlayListCheckAdapter = PlayListCheckAdapter(playList)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val addPlaylist = requireView().findViewById<Button>(R.id.addPlaylistButton)

        addPlaylist.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog(){
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_playlist, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlaylist)

        Log.d("call", id)

        callPlayList(id)

        recyclerPlaylist.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylist.adapter = playListAdapter

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(view)
            .create()

        alertDialogBuilder.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogBuilder.show()
    }

    private fun callPlayList(id: String?) {
        API?.myList(CallPlayListBody(userId = id))
            ?.enqueue(object : Callback<List<MyPlayListModel>> {
                override fun onResponse(call: Call<List<MyPlayListModel>>, response: Response<List<MyPlayListModel>>) {
                    val listResponse = response.body()
                    for(i in listResponse!!.indices){
                        playList.add(listResponse[i])
                    }
                    playListAdapter.setData(playList)
                    Log.d("listOF", response.body().toString())
                }

                override fun onFailure(call: Call<List<MyPlayListModel>>, t: Throwable) {
                    Log.d("listOF", t.message)
                }
            })
    }

}