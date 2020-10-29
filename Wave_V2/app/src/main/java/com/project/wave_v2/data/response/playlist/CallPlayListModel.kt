package com.project.wave_v2.data.response.playlist

data class CallPlayListModel(
    var list: ArrayList<ListInfo>,
    var genreName: String,
    var genreId: Int

)

data class ListInfo(
    var listId: Int,
    var userId: String,
    var listName: String,
    var mainGenre: String,
    var subGenre: String
)