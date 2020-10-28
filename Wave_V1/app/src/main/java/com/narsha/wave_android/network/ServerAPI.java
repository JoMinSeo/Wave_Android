package com.narsha.wave_android.network;

import com.narsha.wave_android.data.RequestUser;
import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.SongList;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.data.request.genre.Genre;
import com.narsha.wave_android.data.request.genre.SubGenre;
import com.narsha.wave_android.data.request.list.RequestPlayList;
import com.narsha.wave_android.data.request.signup.UserSignUp;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.data.response.music.RecommendList;
import com.narsha.wave_android.data.response.music.Song;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerAPI {
    @POST("login.api")
    Call<Result> login(@Body User user);

    @POST("register.api")
    Call<Result> signUp(@Body UserSignUp user);

    @GET("taste1.api")
    Call<List<Genre>> getGenre1();

    @POST("taste2.api")
    Call<List<SubGenre>> getGenre2(@Body int mainGenreId);

    @GET("list.api")
    Call<List<RecommendList>> getList();

    @GET("listInfo.api")
    Call<ArrayList<SongList>> getSongList();

    @POST("mylist.api")
    Call<List<PlayList>> getMyPlayList(@Body User user);

    @POST("listInfo.api")
    Call<PlayList> getListInfo(@Body PlayList playList);

    @GET("mylist.api")
    Call<List<PlayList>> getMyPlayList(@Body RequestUser user);

    @POST("createPlaylist.api")
    Call<Result> addPlayList(@Body PlayList playList);
}
