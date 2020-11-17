package com.project.wave_v2.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.project.wave_v2.R
import com.project.wave_v2.data.dao.playinglist.PlayingRoomDatabase
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.request.playlist.PlayListBody
import com.project.wave_v2.data.request.playlist.PlayListSongBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.data.response.playlist.PlayListModel
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.data.response.search.Song
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.fragment.searched.onclick.OnItemClick
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.PlayListCheckAdapter
import com.project.wave_v2.widget.PlayingListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val KEY_USER = "user_info"
    var viewModel: SearchedViewModel? = null
    var isPlaying = false
    var thisPlaying = false
    var youtubeTimer : CountDownTimer ?= null
    var initTimer = false
    var songId : Int ?= 0
    var API: Service? = null
    var youtubePlayers : YouTubePlayer ?= null
    lateinit var retrofit: Retrofit
    var songUrl : String = ""
    var playListModel = ArrayList<MyPlayListModel>()
    val playListAdapter: PlayListCheckAdapter = PlayListCheckAdapter(playListModel)
    var db : RoomDatabase ?= null
    var playListAdapters : PlayingListAdapter ?= null
    var playList : List<Song> = arrayListOf()
    var modifyList : List<PlayModel> = arrayListOf()
    var leftMusicTime : Long = 0

    private val youtube_link: String =
            "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_sec: String =
            "[https]+\\:+\\/+\\/+[youtu]+\\.+[be]+\\/+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_thr: String =
            "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+"
    private val youtube_link_fou : String =
            "[https:]+\\:+\\/+[www]+\\.+[youtube]+\\.+[com]+\\/+[ watch ]+\\?+[v]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[list]+\\=+[a-z A-Z 0-9 _ \\- ? !]+\\&+[index]+\\=[0-9]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            PlayingRoomDatabase::class.java, "PlayingList").build()

        val btnStart: Button = findViewById<Button>(R.id.playing)
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
        val progressPlaying: ProgressBar = findViewById<ProgressBar>(R.id.progressPlaying)
        val navController = Navigation.findNavController(this, R.id.fragment_host)
        viewModel = ViewModelProvider(this).get(SearchedViewModel::class.java)


        lifecycle.addObserver(youTubePlayerView)

        GlobalScope.launch {
            async {
                Log.d("songs", (db as PlayingRoomDatabase).playingList().getAll().toString())
            }.await()
        }

        val listener = object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            }

            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) {
            }

            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) {
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                if (state == PlayerConstants.PlayerState.ENDED) {
                    Log.d("DONE", "ENDED")
                } else if (state == PlayerConstants.PlayerState.PLAYING) {
                    btnStart.background = getDrawable(R.drawable.ic_baseline_pause_24)
                    isPlaying = true
                } else if (state == PlayerConstants.PlayerState.PAUSED) {
                    btnStart.background = getDrawable(R.drawable.ic_baseline_play_arrow_24)
                    isPlaying = false
                }
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                Log.d("durations", "${duration.toString()} , ${duration * 1000}")
                if(isPlaying && initTimer){
                    progressPlaying.max = duration.toInt()
                    progressPlaying.progress = 0
                    initTimer(duration.toLong())
                    initTimer = false
                }
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
            }

            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
            }

        }
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youtubePlayers = youTubePlayer
                observe(youTubePlayer, btnStart)
                initTimer = true
            }
        })
        youTubePlayerView.addYouTubePlayerListener(listener)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
    fun initTimer(duration: Long) {
        youtubeTimer = object : CountDownTimer(duration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                leftMusicTime = millisUntilFinished
                if(isPlaying){
                    progressPlaying.progress += 1
                    Log.d("progress", progressPlaying.progress.toString())
                }
            }

            override fun onFinish() {
                youtubeTimer!!.cancel()
                progressPlaying.progress = 0
            }
        }

        (youtubeTimer as CountDownTimer).start()
    }

    fun onResumeTimer(leftTime : Long){
        initTimer(leftTime)
    }

    fun onPauseTimer(){
        youtubeTimer!!.cancel()
    }

    fun convertList(){

    }

    fun observe(youTubePlayer: YouTubePlayer, btnStart: Button){
        val titleSong : TextView = findViewById<TextView>(R.id.songTitle)
        val nameArtist : TextView = findViewById<TextView>(R.id.artistName)
        val coverImage : ImageView = findViewById<ImageView>(R.id.coverImage)
        val playListButton : Button = findViewById(R.id.playListButton)
        titleSong.setSelected(true)

        playListButton.setOnClickListener {
            showDialog()
        }

        btnStart.setOnClickListener {
            if(isPlaying){
                youTubePlayer.pause()
                onPauseTimer()
            }else{
                youTubePlayer.play()
                onResumeTimer(leftMusicTime)
            }
        }
        viewModel!!.isViewing!!.observe(this,
                Observer<Boolean> {
                    if (viewModel!!.isViewing!!.value!!) {
                        progressPlaying.progress = 0
                        titleSong.text = viewModel!!.playingModel!!.value!!.title!!
                        Glide.with(applicationContext).load(viewModel!!.playingModel!!.value!!.jacket).into(coverImage)
                        nameArtist.text = viewModel!!.playingModel!!.value!!.singer!!
                        songUrl = viewModel!!.playingModel!!.value!!.link!!
                        youTubePlayer.loadVideo(viewModel!!.playingModel!!.value!!.link!!, 0F)
                        Log.d("youtube", viewModel!!.playingModel!!.value!!.link!!)
                        Log.d("artist", viewModel!!.playingModel!!.value!!.singer!!)
                    }
                })
        }
    override fun onStop(){
        super.onStop()
        if(youtubeTimer != null)
            youtubeTimer!!.cancel()

    }


    private fun showDialog(){
        val prefs: SharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")
        val titleSong : TextView = findViewById<TextView>(R.id.songTitle)

        val view = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_playing, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlayinglist)

        var closeButton = view.findViewById<Button>(R.id.closingButton)
        var importButton = view.findViewById<Button>(R.id.importButton)



        viewModel!!.songTitle!!.value = titleSong.text.toString()
        GlobalScope.launch {
            async {
                playList = (db as PlayingRoomDatabase).playingList().getAll()
                playListAdapters = PlayingListAdapter(playList, applicationContext, this@MainActivity, viewModel!!, viewModel!!.songTitle!!.value!!  ,object : OnItemClick{
                    override fun OnItemClick(song : Song) {
                        Log.d("s", "$songUrl , ${getLink(song.songUrl!!)}")
                        if(songTitle.text != song.title){
                            progressPlaying.progress = 0
                            Log.d("song", "song URL : "+getLink(song.songUrl!!) + "," + getLink(song.songUrl!!) )
                            viewModel!!.playingModel!!.value = PlayModel(song.jacket, getLink(song.songUrl!!), song.title, song.artistName)
                            viewModel!!.isViewing!!.value = true
                            thisPlaying = true
                        }else{
                            if(thisPlaying && youtubePlayers != null){
                                youtubePlayers!!.pause()
                                thisPlaying = false
                            }else if(!thisPlaying && youtubePlayers != null){
                                youtubePlayers!!.play()
                                thisPlaying = true
                            }
                        }
                    }

                })

                recyclerPlaylist.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                recyclerPlaylist.adapter = playListAdapters

            }.await()

        }

        val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(view)
                .create()

            importButton.setOnClickListener {
                showingDialog(alertDialogBuilder)
            }
            closeButton.setOnClickListener{
              alertDialogBuilder.dismiss()
            }




        alertDialogBuilder.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogBuilder.show()
    }

    fun getLink(songLink : String) : String {
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
            result = songLink.substring(songLink.indexOf('=', 0)+ 1, songLink.indexOf('&', 0))
            Log.d("logPattern3", "result : $result")
            return result
        } else if (Pattern.matches(youtube_link_fou, songLink)) {
            result = songLink.substring(songLink.indexOf('=', 0) + 1, songLink.indexOf('&', 0))
            Log.d("logPattern4", "result : $result")
            return result
        }
        return ""
    }

     private fun showingDialog(alertDialog: AlertDialog){
        val prefs: SharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id: String? = prefs.getString("userId", "user")

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val view = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_playlist, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlaylist)
        var addButton = view.findViewById<Button>(R.id.addPlaylist)

        addButton.text = "음악 재생목록에 추가"

        Log.d("call", id)

        callPlayList(id)

        recyclerPlaylist.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerPlaylist.adapter = playListAdapter

        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(view)
            .create()

         addButton.setOnClickListener {
             for(i in playListModel.indices){
                 if(playListModel[i].check == true){
                     callSongList(playListModel[i].listId, recyclerPlaylist)
                     playListAdapters!!.notifyDataSetChanged()
                     alertDialogBuilder.dismiss()
                     alertDialog.dismiss()
                     Toast.makeText(applicationContext,"성공적으로 플레이리스트에 추가되었습니다", Toast.LENGTH_LONG).show()
                 }
             }
         }




        alertDialogBuilder.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogBuilder.show()
    }
    private fun callSongList(listId : Int, recyclerView: RecyclerView){
        (playList as ArrayList).clear()
        playListAdapters!!.setData(playList)
        playListAdapters!!.notifyDataSetChanged()
        recyclerView.adapter = playListAdapter

        API?.getSongList(PlayListBody(listId))
            ?.enqueue(object : Callback<PlayListModel>{
                override fun onResponse(call: Call<PlayListModel>, response: Response<PlayListModel>) {
                    if(response.code() == 200){
                            for(i in 0 until response.body()?.song?.size!!){
                                var songInfo : SongInfo = response.body()?.song!![i]
                                var song : Song = Song(songInfo.songId, songInfo.title, songInfo.artistId, songInfo.artistName, songInfo.mainGenreId,
                                    songInfo.subGenreId, songInfo.albumId, songInfo.songUrl, songInfo.age, songInfo.writer, songInfo.jacket)
                                GlobalScope.launch {
                                    async {
                                        //checkingDelete(song , i)
                                        (db as PlayingRoomDatabase).playingList().songInsert(song)
                                        for(i in (db as PlayingRoomDatabase).playingList().getAll()){
                                            (playList as ArrayList).add(playList.size, i)
                                        }
                                    }

                                }
                                Handler().post(Runnable {
                                    playListAdapters!!.setData(playList)
                                })
                                playListAdapters!!.notifyDataSetChanged()

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
//    private fun checkingDelete(songInfo : Song, i : Int){
//        GlobalScope.launch {
//            async {
//                if(songInfo.songId == (db as PlayingRoomDatabase).playingList().getAll()[i].songId){
//                    (db as PlayingRoomDatabase).playingList().songDelete(songInfo)
//                }
//            }
//        }
//    }
    private fun callPlayList(id: String?) {
        API?.myList(CallPlayListBody(userId = id))
            ?.enqueue(object : Callback<List<MyPlayListModel>> {
                override fun onResponse(call: Call<List<MyPlayListModel>>, response: Response<List<MyPlayListModel>>) {
                    val listResponse = response.body()
                    for(i in listResponse!!.indices){
                        playListModel.add(listResponse[i])
                    }
                    playListAdapter.setData(playListModel)
                    Log.d("listOF", response.body().toString())
                }

                override fun onFailure(call: Call<List<MyPlayListModel>>, t: Throwable) {
                    Log.d("listOF", t.message)
                }
            })
    }

}