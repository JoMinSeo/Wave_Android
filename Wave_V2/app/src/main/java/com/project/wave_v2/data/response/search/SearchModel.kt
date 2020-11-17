package com.project.wave_v2.data.response.search

import androidx.room.Entity
import androidx.room.PrimaryKey

open class SearchObject(open var viewType: Int?)

data class SearchModel(
    var artist : List<ArtistInfo?>?,
    var album : List<AlbumInfo?>?,
    var song : List<SearchSongInfo?>?,
    var category: List<Category>?
)

data class SearchResult(
    var artist : List<Artist>?,
    var album : List<Album>?,
    var song : List<Song>?
)

data class ArtistInfo(
    var artist : Artist?,
    override var viewType:Int?=0
):SearchObject(viewType)

data class AlbumInfo(
    var album: Album?,
    override var viewType:Int?=0
):SearchObject(viewType)
data class SearchSongInfo(
    var song : Song?,
    override var viewType:Int?=0
):SearchObject(viewType)

data class Category(
    var title : String?,
    override var viewType:Int?=0
):SearchObject(viewType)

@Entity
data class Song(
    @PrimaryKey var songId : Int?,
    var title : String?,
    var artistId : Int?,
    var artistName : String?,
    var mainGenreId : Int?,
    var subGenreId : Int?,
    var albumId : Int?,
    var songUrl : String?,
    var age : Int?,
    var writer : String?,
    var jacket:String?,
    var isPlaying : Boolean = false
)

data class Album(
    var albumId: Int?,
    var albumName: String?,
    var jacket: String?,
    var aritstId: Int?,
    var artistName: String?,
)

data class Artist(
    var aritstId : Int?,
    var artistName : String?,
)

data class Cover(
        var albumId: Int?,
        var jacket: String?
)
