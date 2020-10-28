package com.project.wave_v2.data.response

data class SearchModel(
    var artist : List<ArtistInfo?>?,
    var album : List<AlbumInfo?>?,
    var song : List<SearchSongInfo?>?
)

data class ArtistInfo(
    var aritstId : Int?,
    var artistName : String?
)

data class AlbumInfo(
    var albumId: Int?,
    var albumName: String?,
    var jacket: String?,
    var aritstId: Int?,
    var artistName: String?
)

data class SearchSongInfo(
    var songId : Int?,
    var title : String?,
    var artistId : Int?,
    var artistName : String?,
    var mainGenreId : Int?,
    var subGenreId : Int?,
    var albumId : Int?,
    var songUrl : String?,
    var age : Int?,
    var writer : String?
)