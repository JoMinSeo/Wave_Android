package com.project.wave_v2.view.activity

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.PlayListBody
import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.playlist.PlayListModel
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.data.response.search.Song
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.fragment.searched.onclick.OnItemClick
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.SongListAdapter
import com.project.wave_v2.widget.sheet.BottomSheet
import kotlinx.android.synthetic.main.activity_song_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.Serializable
import java.util.regex.Pattern

class SongListActivity : AppCompatActivity() {

    private val youtube_link: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_sec: String =
        "[https]+\\:+\\/+\\/+[youtu]+\\.+[be]+\\/+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_thr: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_fou: String =
        "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[index]+\\=[0-9]+"


    var listId = -1
    lateinit var listName: String
    var API: Service? = null
    lateinit var retrofit: Retrofit
    var songList: ArrayList<SongInfo> = ArrayList<SongInfo>()
    var songListAdapter: SongListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val importAddButton: Button = findViewById<Button>(R.id.addPlaylistFromPeople)

        listId = intent.getIntExtra("listId", -1)
        listName = intent.getStringExtra("listName")

        listNameText.text = listName

        rcViewSetting()

        callSongList()

        importAddButton.setOnClickListener {
            showSheet()
        }

    }

    private fun showSheet() {
        val bottomSheet = BottomSheet(0, 1, "", "", null)
        bottomSheet.show(
            supportFragmentManager,
            bottomSheet.tag
        )
    }

    private fun rcViewSetting() {
        songListAdapter = SongListAdapter(songList, listId, this, object : OnItemClick {
            override fun OnItemClick(song: Song) {
            }

            override fun PlayModelClick(song: SongInfo) {
                finish()
                Intent().also {
                    it.action = "WAVE_PLAY_SONG"
                    it.putExtra("jacket", song.jacket)
                    it.putExtra("artistName",song.artistName)
                    it.putExtra("link", getLink(song.songUrl!!))
                    it.putExtra("title", song.title)
                    sendBroadcast(it)
                }
            }

        })
        songList_rcview.adapter = songListAdapter
        songList_rcview.layoutManager = LinearLayoutManager(this)
        songList_rcview.setHasFixedSize(true)
    }

    fun getLink(songLink: String): String {
        var result: String = ""
        if (Pattern.matches(youtube_link, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.length)
            Log.d("logPattern1", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_sec, songLink)) {
            result = songLink.substring(songLink.indexOf("e/") + 2, songLink.length)
            Log.d("logPattern2", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_thr, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.indexOf('&', 0))
            Log.d("logPattern3", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_fou, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.indexOf('&', 0))
            Log.d("logPattern4", "result : $result")
            return result
        }
        return ""
    }

    private fun callSongList() {
        API?.getSongList(PlayListBody(listId))
            ?.enqueue(object : Callback<PlayListModel> {
                override fun onResponse(
                    call: Call<PlayListModel>,
                    response: Response<PlayListModel>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.song == null) {
                            noSongText.visibility = View.VISIBLE
                        } else {
                            songList.clear()

                            for (i in 0 until response.body()?.song?.size!!) {
                                songList.add(response.body()?.song!![i])
                                Log.d("Logd", response.body()?.song!![i].toString())
                            }

                            songListAdapter!!.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("SongListActivity", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<PlayListModel>, t: Throwable) {
                    Log.d("SongListActivity", t.message)
                }
            })

    }
}