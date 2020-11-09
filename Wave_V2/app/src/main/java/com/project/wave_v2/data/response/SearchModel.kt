package com.project.wave_v2.data.response

open class SearchObject(open var viewType: Int?)

data class SearchModel(
    var artist : List<ArtistInfo?>?,
    var album : List<AlbumInfo?>?,
    var song : List<SearchSongInfo?>?,
    var category: List<Category>?
)

data class ArtistInfo(
    var aritstId : Int?,
    var artistName : String?,
    override var viewType:Int?
):SearchObject(viewType)

data class AlbumInfo(
    var albumId: Int?,
    var albumName: String?,
    var jacket: String?,
    var aritstId: Int?,
    var artistName: String?,
    override var viewType:Int?
):SearchObject(viewType)

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
    var writer : String?,
    override var viewType:Int?
):SearchObject(viewType)

data class Category(
    var title : String?,
    override var viewType:Int?
):SearchObject(viewType)