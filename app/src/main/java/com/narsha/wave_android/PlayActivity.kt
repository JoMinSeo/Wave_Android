package com.narsha.wave_android


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val videoView: VideoView = findViewById<View>(R.id.videoView) as VideoView
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        // Set video link (mp4 format )
        // Set video link (mp4 format )
        val video: Uri = Uri.parse("rtsp://v8.cache1.c.youtube.com/CiILENy73wIaGQnxa4t5p6BVTxMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp")
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(video)
        videoView.start()
    }
}