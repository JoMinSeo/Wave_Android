package com.project.wave_v2.network

import com.project.wave_v2.data.request.LoginBody
import com.project.wave_v2.data.request.PlaySongBody
import com.project.wave_v2.data.request.RegisterBody
import com.project.wave_v2.data.request.SearchBody
import com.project.wave_v2.data.request.like.LikeInfoMainBody
import com.project.wave_v2.data.request.like.LikeInfoSubBody
import com.project.wave_v2.data.request.playlist.*
import com.project.wave_v2.data.response.*
import com.project.wave_v2.data.response.like.LikeFeelModel
import com.project.wave_v2.data.response.like.LikeGenreModel
import com.project.wave_v2.data.response.playlist.CallPlayListModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.data.response.playlist.PlayListModel
import com.project.wave_v2.data.response.playlist.SongInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @POST("login.api")
    fun login(@Body login: LoginBody): Call<ResultModel>

    @POST("register.api")
    fun register(@Body register: RegisterBody): Call<ResultModel>

    @GET("taste1.api")
    fun likeGenre(): Call<List<LikeGenreModel?>?>

    @POST("taste2.api")
    fun likeFeel(@Body mainGenreId: Int): Call<List<LikeFeelModel?>?>

    @GET("list.api")
    fun getList(@Body callPlayList: CallPlayListBody): Call<List<CallPlayListModel>>

    @GET("listInfo.api")
    fun getSongList(@Body playList : PlayListBody): Call<List<PlayListModel?>?>

    @GET("songs.api")
    fun getSong(@Body song : PlaySongBody): Call<SongInfo?>

    @GET("search.api")
    fun getSearchInfo(@Body search : SearchBody): Call<SearchModel?>

    @POST("registerSongs1.api")
    fun likeInfo1(@Body like : LikeInfoMainBody): Call<ResultModel>

    @POST("registerSongs2.api")
    fun likeInfo2(@Body like : LikeInfoSubBody): Call<ResultModel>

    @POST("createPlaylist.api")
    fun createPlayList(@Body playList : CreatePlayListBody): Call<ResultModel>

    @POST("addPlaylistSong.api")
    fun addPlayListSong(@Body playlistSong : PlayListSongBody): Call<ResultModel>

    @DELETE("deletePlaylistSong.api")
    fun delPlayListSong(@Body playListSong : PlayListSongBody): Call<ResultModel>

    @DELETE("deletePlaylist.api")
    fun delPlayList(@Body playList : PlayListBody): Call<ResultModel>

    @POST("addMylist.api")
    fun addMyList(@Body myList : MyPlayListBody): Call<ResultModel>

    @DELETE("deleteMylist.api")
    fun delMyList(@Body myList : MyPlayListBody): Call<ResultModel>

    @GET("mylist.api")
    fun myList(@Body userId : String): Call<MyPlayListModel?>


}