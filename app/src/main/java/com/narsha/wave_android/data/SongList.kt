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
        var songid : Int,
        var title : String,
        var artistid : Int,
        var maingenreid : Int,
        var subgenreid : Int,
        var albumid : Int,
        var songname : String,
        var lyric : String,
        var songurl : String,
        var gender : String,
        var age : Int,
        var writer : String
)