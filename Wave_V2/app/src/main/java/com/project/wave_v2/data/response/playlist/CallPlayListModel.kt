package com.project.wave_v2.data.response.playlist

data class CallPlayListModel(
    var list: ArrayList<ListInfo>,
    var genreName: String? = null,
    var genreId: Int

)

data class ListInfo(
    var listId: Int,
    var userId: String,
    var listName: String,
    var jacket : String,
    var mainGenre: String,
    var subGenre: String
)