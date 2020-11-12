package com.project.wave_v2.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.views.YouTubePlayerSeekBarListener
import com.project.wave_v2.R
import com.project.wave_v2.view.viewmodel.SearchedViewModel


class SongActivity : AppCompatActivity() {
    var indexNowPlaying : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        var intent = intent

        val youtubePlayerSeekBar : YouTubePlayerSeekBar = findViewById(R.id.youtube_player_seekbar)
        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
        val song : ImageView = findViewById(R.id.song_img)


        lifecycle.addObserver(youTubePlayerView)

        Log.d("jacket_log",intent.getStringExtra("jacket")!!)
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
                    Log.d("DONE","STARTING")
                    youTubePlayer.addListener(youtubePlayerSeekBar)
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
                Glide.with(applicationContext)
                    .load(intent.getStringExtra("jacket")!!)
                    .into(song)
                Log.d("link", intent.getStringExtra("link")!!)
                youTubePlayer.loadVideo(intent.getStringExtra("link")!!, 0F) // 여기에 비디오 아이디 입력.
                Log.d("?", youtubePlayerSeekBar.toString())
                youTubePlayer.addListener(youtubePlayerSeekBar)
            }
        })
        youTubePlayerView.addYouTubePlayerListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}