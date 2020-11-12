package com.project.wave_v2.data.response.playlist

data class MyPlayListModel(
    var listId: Int,
    var songCount: Int,
    var jacket: String,
    var listName: String,
    var check : Boolean? = false
)