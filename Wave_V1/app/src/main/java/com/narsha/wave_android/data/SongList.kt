package com.narsha.wave_android.data

data class SongList(
        var playlistid : Int,
        var userid : String,
        var date : String,
        var title : String,
        var songs : ArrayList<Songs>,
        var maingenre : String,
        var subgenre : String
)

data class Songs(
        var songId : Int,
        var title : String,
        var artistId : Int,
        var maingenreId : Int,
        var subgenreId : Int,
        var albumId : Int,
        var songName : String,
        var lyric : String,
        var songUrl : String,
        var gender : String,
        var age : Int,
        var writer : String,
        var jacket : String
)