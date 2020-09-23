package com.narsha.wave_android.data.response.music

data class List_db(var playlistid : String?=null, var userid : String?=null,
                   var date : String?=null, var title : String?=null,var jacket : String?= null, var songs : List<Song>? =null,
                    var maingenre : String?=null, var subgenre : String?=null)