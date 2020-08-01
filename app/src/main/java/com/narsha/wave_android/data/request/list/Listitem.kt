package com.narsha.wave_android.data.request.list

import com.narsha.wave_android.data.response.playlist.playList

data class Listitem(var genreId : String, var genreName : String? = null, var subGenreId : String? = null, var subGenreName : String? = null, var playLists : List<playList> ? = null)