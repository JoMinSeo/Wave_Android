package com.project.wave_v2.data.response.playlist

data class PlayListModel(
    var listId : Int,
    var userId : String,
    var listName : String,
    var song : ArrayList<SongInfo>,
    var mainGenre : String,
    var subGenre : String
)

data class SongInfo(
    var songId : Int,
    var title : String,
    var artistId : Int,
    var artistName : String,
    var mainGenreId : Int,
    var mainGenreName : String,
    var subGenreName : String,
    var subGenreId : Int,
    var albumId : Int,
    var albumName : String,
    var lyric : String,
    var songUrl : String,
    var gender : String,
    var age : Int,
    var writer : String,
    var jacket : String
)

