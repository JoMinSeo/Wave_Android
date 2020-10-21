package com.narsha.wave_android.data.response.music

data class PlayList(
        var listId: String? = null,
        var songCount: Int? = null,
        var listName: String? = null,
        var jacket: String? = null,
        var maingenre:String?=null,
        var song:List<Song>?=null
)