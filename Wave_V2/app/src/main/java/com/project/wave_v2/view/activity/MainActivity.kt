package com.project.wave_v2.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.project.wave_v2.R
import com.project.wave_v2.view.viewmodel.SearchedViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val KEY_USER = "user_info"
    var viewModel : SearchedViewModel ?= null
    var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart : Button = findViewById<Button>(R.id.playing)
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
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
                    Log.d("DONE","ENDED")
                }else if(state == PlayerConstants.PlayerState.PLAYING){
                    btnStart.background = getDrawable(R.drawable.ic_baseline_pause_24)
                    isPlaying = true
                }else if(state == PlayerConstants.PlayerState.PAUSED){
                    btnStart.background = getDrawable(R.drawable.ic_baseline_play_arrow_24)
                    isPlaying = false
                }
            }
            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
            }

            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
            }

        }

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
               observe(youTubePlayer, btnStart)
            }
        })
        youTubePlayerView.addYouTubePlayerListener(listener)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
    fun observe(youTubePlayer: YouTubePlayer, btnStart : Button){
        val titleSong : TextView = findViewById<TextView>(R.id.songTitle)
        val nameArtist : TextView = findViewById<TextView>(R.id.artistName)

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
                    titleSong.text = viewModel!!.playingModel!!.value!!.title!!
                    nameArtist.text = viewModel!!.playingModel!!.value!!.singer!!
                    youTubePlayer.loadVideo(viewModel!!.playingModel!!.value!!.link!!, 0F)
                }
            })
        }

}