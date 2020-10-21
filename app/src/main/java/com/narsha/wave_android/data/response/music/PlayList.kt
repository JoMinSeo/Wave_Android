package com.narsha.wave_android.data.response.music

data class PlayList(
        var listId: String? = null,
        var userId: String? = null,
        var date: String ?= null,
        var listName: String ?= null,
        var song:List<Song>?=null,
        var mainGenre:String?=null,
        var subGenre:String?=null
)