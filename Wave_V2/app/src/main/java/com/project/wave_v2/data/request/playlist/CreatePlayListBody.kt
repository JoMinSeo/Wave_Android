package com.project.wave_v2.data.request.playlist

//플레이리스트 생성
data class CreatePlayListBody(
    var userId: String?,
    var listName: String?,
    var mainGenreId: Int?,
    var subGenreId: Int?

)