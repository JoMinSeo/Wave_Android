package com.project.wave_v2.view.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.PlayListBody
import com.project.wave_v2.data.response.playlist.PlayListModel
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.widget.SongListAdapter
import com.project.wave_v2.widget.sheet.BottomSheet
import kotlinx.android.synthetic.main.activity_song_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.properties.Delegates

class SongListActivity : AppCompatActivity() {


    var listId = -1
    lateinit var listName : String

    var API : Service? = null
    lateinit var retrofit : Retrofit
    var songList : ArrayList<SongInfo> = ArrayList<SongInfo>()
    var songListAdapter : SongListAdapter = SongListAdapter(songList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val importAddButton : Button = findViewById<Button>(R.id.addPlaylistFromPeople)

        listId = intent.getIntExtra("listId", -1)
        listName = intent.getStringExtra("listName")

        listNameText.text = listName

        rcViewSetting()

        callSongList()

        importAddButton.setOnClickListener {
            showSheet()
        }

    }

    private fun showSheet(){
        val bottomSheet = BottomSheet(0, 1, "", "")
        bottomSheet.show(
                supportFragmentManager,
                bottomSheet.tag
        )
    }

    private fun rcViewSetting(){
        songList_rcview.adapter = songListAdapter
        songList_rcview.layoutManager = LinearLayoutManager(this)
        songList_rcview.setHasFixedSize(true)
    }

    private fun callSongList(){
        API?.getSongList(PlayListBody(listId))
                ?.enqueue(object : Callback<PlayListModel>{
                    override fun onResponse(call: Call<PlayListModel>, response: Response<PlayListModel>) {
                        if(response.code() == 200){
                            if(response.body()?.song == null){
                                noSongText.visibility = View.VISIBLE
                            }else{
                                songList.clear()

                                for(i in 0 until response.body()?.song?.size!!){
                                    songList.add(response.body()?.song!![i])
                                    Log.d("Logd", response.body()?.song!![i].toString())
                                }

                                songListAdapter.notifyDataSetChanged()
                            }
                        }
                        else{
                            Log.d("SongListActivity", response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<PlayListModel>, t: Throwable) {
                        Log.d("SongListActivity", t.message)
                    }
                })

    }

}