package com.project.wave_v2.view.fragment.searched.onclick

import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.data.response.search.Song

interface OnItemClick {
    fun OnItemClick(song: Song)
    fun PlayModelClick(song : SongInfo)
}
