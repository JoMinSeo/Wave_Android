package com.project.wave_v2.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.request.playlist.PlayListSongBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import com.project.wave_v2.widget.PlayListCheckAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*


class MainActivity : AppCompatActivity() {
    val KEY_USER = "user_info"
    var viewModel: SearchedViewModel? = null
    var isPlaying = false
    var youtubeTimer : CountDownTimer ?= null
    var initTimer = false
    var API: Service? = null
    lateinit var retrofit: Retrofit
    var songId : Int ?= 0
    var playList = ArrayList<MyPlayListModel>()
    val playListAdapter: PlayListCheckAdapter = PlayListCheckAdapter(playList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById<Button>(R.id.playing)
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
        val progressPlaying: ProgressBar = findViewById<ProgressBar>(R.id.progressPlaying)
        val navController = Navigation.findNavController(this, R.id.fragment_host)
        viewModel = ViewModelProvider(this).get(SearchedViewModel::class.java)

        lifecycle.addObserver(youTubePlayerView)

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
            }else{
                youTubePlayer.play()
            }
        }
        viewModel!!.isViewing!!.observe(this,
                Observer<Boolean> {
                    if (viewModel!!.isViewing!!.value!!) {
                        progressPlaying.progress = 0
                        titleSong.text = viewModel!!.playingModel!!.value!!.title!!
                        Glide.with(applicationContext).load(viewModel!!.playingModel!!.value!!.jacket).into(coverImage)
                        nameArtist.text = viewModel!!.playingModel!!.value!!.singer!!
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

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        val view = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_playlist, null)
        val recyclerPlaylist = view.findViewById<RecyclerView>(R.id.recyclerViewPlaylist)
        var addButton = view.findViewById<Button>(R.id.addPlaylist)

        Log.d("call", id)

        callPlayList(id)

        recyclerPlaylist.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerPlaylist.adapter = playListAdapter

        val alertDialogBuilder = AlertDialog.Builder(this)
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

        }


        alertDialogBuilder.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialogBuilder.show()
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
    private fun callPlayList(id: String?) {
        playList.clear()
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