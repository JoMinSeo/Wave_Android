package com.narsha.wave_android.data.response.music

data class Song(var songid : Int?= null, var title : String?= null,
                var artistid : Int?= null, var maingenreid : Int?=null, var subgenreid : Int?=null, var albumid : Int?=null, var songname :String?=null,
                var songurl : String?=null, var age : Int?=null, var writer : String?=null, var jacket : String?=null)