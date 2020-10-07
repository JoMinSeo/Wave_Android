package com.narsha.wave_android.data.request.list

import com.narsha.wave_android.data.response.music.PlayList

data class Listitem(
        var genreId: String,
        var genreName: String? = null,
        var subGenreId: String? = null,
        var subGenreName: String? = null,
        var playLists: List<PlayList>? = null)