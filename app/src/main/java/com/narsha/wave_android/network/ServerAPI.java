package com.narsha.wave_android.network;

import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("login.api")
    Call<Result> login(@Body User user);

    @POST("signup")
    Call<Result> signUp(@Body User user);
}
