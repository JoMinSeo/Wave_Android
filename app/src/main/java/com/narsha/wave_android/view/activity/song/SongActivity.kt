package com.narsha.wave_android.view.activity.song

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.narsha.wave_android.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class SongActivity : AppCompatActivity() {
    val arrayPlayDummy = listOf<String>("wKyMIrBClYw", "jO2viLEW-1A", "HCc1UvTQ1Hg")
    var indexNowPlaying : Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
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
                    indexNowPlaying ++;
                    dataPlaying(youTubePlayer, indexNowPlaying)
                }else if(state == PlayerConstants.PlayerState.PLAYING){
                    Log.d("DONE","STARTING")
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
                val videoId = arrayPlayDummy[indexNowPlaying]
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })
        youTubePlayerView.addYouTubePlayerListener(listener)
    }

    fun dataPlaying(youTubePlayer : YouTubePlayer, index : Int) {
        if(index < arrayPlayDummy.size){
            val videoId = arrayPlayDummy[index]
            youTubePlayer.loadVideo(videoId, 0F)
        }
    }
}