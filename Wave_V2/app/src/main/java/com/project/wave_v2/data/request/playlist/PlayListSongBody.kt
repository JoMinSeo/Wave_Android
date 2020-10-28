package com.project.wave_v2.data.request.playlist

//플레이리스트 노래 추가, 삭제
data class PlayListSongBody(
    var listId: Int,
    var songId: Int
)