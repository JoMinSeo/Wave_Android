package com.narsha.wave_android.data.request.signup

import com.narsha.wave_android.data.request.genre.Genre

data class SelectGenre(
        var maingenreid: Int? = null,
        var mainGenreName : String?=null,
)