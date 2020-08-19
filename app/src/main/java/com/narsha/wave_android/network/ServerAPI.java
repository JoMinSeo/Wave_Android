package com.narsha.wave_android.network;

import com.narsha.wave_android.data.request.signup.SignUp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServerAPI {
    @GET("users/{id}")
    Call<SignUp> user(@Path("userId") String userId, @Path("password") String password);
}
