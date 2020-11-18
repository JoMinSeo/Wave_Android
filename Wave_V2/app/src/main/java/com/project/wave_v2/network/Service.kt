package com.project.wave_v2.network

import com.project.wave_v2.data.request.LoginBody
import com.project.wave_v2.data.request.PlaySongBody
import com.project.wave_v2.data.request.RegisterBody
import com.project.wave_v2.data.request.SearchBody
import com.project.wave_v2.data.request.like.LikeFeelBody
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
import com.project.wave_v2.data.response.search.SearchModel
import com.project.wave_v2.data.response.search.SearchResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @POST("login.api")
    fun login(@Body login: LoginBody): Call<ResultModel>

    @POST("register.api")
    fun register(@Body register: RegisterBody): Call<ResultModel>

    @POST("taste1.api")
    fun likeGenre(): Call<List<LikeGenreModel>>

    @POST("taste2.api")
    fun likeFeel(@Body likeFeelBody: LikeFeelBody): Call<List<LikeFeelModel>>

    @POST("list.api")
    fun getList(@Body callPlayList: CallPlayListBody): Call<List<CallPlayListModel>>

    @POST("listInfo.api")
    fun getSongList(@Body playList : PlayListBody): Call<PlayListModel>

    @POST("songs.api")
    fun getSong(@Body song : PlaySongBody): Call<SongInfo?>

    @POST("search.api")
    fun getSearchInfo(@Body search : SearchBody): Call<SearchResult?>

    @GET("searchAll.api")
    fun getAll() : Call<SearchResult>

    @POST("registerSongs1.api")
    fun likeInfo1(@Body like : LikeInfoMainBody): Call<ResultModel>

    @POST("registerSongs2.api")
    fun likeInfo2(@Body like : LikeInfoSubBody): Call<ResultModel>

    @POST("createPlaylist.api")
    fun createPlayList(@Body playList : CreatePlayListBody): Call<ResultModel>

    @POST("addPlaylistSong.api")
    fun addPlayListSong(@Body playlistSong : PlayListSongBody): Call<ResultModel>

    @POST("deletePlaylistSong.api")
    fun delPlayListSong(@Body playListSong : PlayListSongBody): Call<ResultModel>

    @POST("deletePlaylist.api")
    fun delPlayList(@Body playList : PlayListDeleteBody): Call<ResultModel>

    @POST("addMylist.api")
    fun addMyList(@Body myList : MyPlayListBody): Call<ResultModel>

    @POST("deleteMylist.api")
    fun delMyList(@Body myList : MyPlayListBody): Call<ResultModel>

    @POST("mylist.api")
    fun myList(@Body userId : CallPlayListBody): Call<List<MyPlayListModel>>


}