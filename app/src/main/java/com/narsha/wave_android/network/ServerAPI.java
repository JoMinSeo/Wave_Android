package com.narsha.wave_android.network;

import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.data.request.signup.UserSignUp;
import com.narsha.wave_android.data.response.music.List_db;
import com.narsha.wave_android.data.response.music.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("login.api")
    Call<Result> login(@Body User user);

    @POST("register.api")
    Call<Result> signUp(@Body UserSignUp user);

    @GET("taste1.api")
    Call<List<SelectGenre>> getGenre1();

    @GET("list.api")
    Call<List<Song>> getList();
}
