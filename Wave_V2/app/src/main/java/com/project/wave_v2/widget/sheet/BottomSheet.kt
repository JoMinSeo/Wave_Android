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
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.request.playlist.PlayListSongBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.viewmodel.CallPlayListViewModel
import com.project.wave_v2.widget.PlayListAdapter
import com.project.wave_v2.widget.PlayListCheckAdapter
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class BottomSheet(songId : Int, type : Int, songName : String, songArtist : String) : BottomSheetDialogFragment() {

    var API: Service? = null
    lateinit var retrofit: Retrofit
    var songId : Int ?= 0
    var type = 0
    var songTitle : String? = null
    var songArtist : String? = null
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
        val titleName : TextView = requireView().findViewById(R.id.titleName)
        val subTitle : TextView = requireView().findViewById(R.id.subTitle)
        val closeButton : Button = requireView().findViewById(R.id.closeButton)
        if(type == SONG){ // if type is song playlist
            titleName.text = songTitle
            subTitle.text = songArtist
        }else if(type == IMPORT){ // if import playlist
            titleName.text = "플레이리스트 가져오기"
            subTitle.text = "다른 사람들의 플레이리스트를 가져옵니다."
        }else {
            Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_LONG).show()
        }

        closeButton.setOnClickListener {
            dismiss()
        }

        addPlaylist.setOnClickListener {
            if(type == SONG){
                showDialog()
            }else if(type == IMPORT){
                showImportDialog()
            }else {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showImportDialog(){
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_playlist, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlaylist)
        var addButton = view.findViewById<Button>(R.id.addPlaylist)

        playListAdapter.setDummy()

        recyclerPlaylist.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylist.adapter = playListAdapter

        val alertDialogBuilder = AlertDialog.Builder(context)
                .setView(view)
                .create()

        addButton.setOnClickListener{
            Toast.makeText(context, "성공적으로 플레이리스트가 복사되었습니다.", Toast.LENGTH_LONG).show()
            alertDialogBuilder.dismiss()
            dismiss()

        }


        alertDialogBuilder.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogBuilder.show()
    }

    private fun showDialog(){
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_playlist, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlaylist)
        var addButton = view.findViewById<Button>(R.id.addPlaylist)

        Log.d("call", id)

        callPlayList(id)

        recyclerPlaylist.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerPlaylist.adapter = playListAdapter

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(view)
            .create()

        addButton.setOnClickListener{
            for(i in playList.indices){
                if(playList[i].check == true){
                    Log.d("log_progress", playList[i].toString())
                    addPlaylist(playList[i].listId, songId)
                }
            }
            alertDialogBuilder.dismiss()
            dismiss()

        }


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
    private fun addPlaylist(listID: Int?, songId: Int?) {
        Log.d("d_song",songId.toString())
        val addPlayList  = PlayListSongBody(listID, songId)
        API?.addPlayListSong(addPlayList)!!.enqueue( object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if(response.code() == 200){
                    Log.d("song","success ${response.body()}")
                }else{
                    Log.d("song","failed")
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Log.d("song","failed ${t.message}")
            }

        })
    }
    init {
        this.songId = songId
        this.type = type
        this.songTitle = songName
        this.songArtist = songArtist
    }
    companion object{
        const val SONG = 0
        const val IMPORT = 1
    }

}