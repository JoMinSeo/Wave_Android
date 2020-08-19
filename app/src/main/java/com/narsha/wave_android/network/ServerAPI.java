package com.narsha.wave_android.network;

import com.narsha.wave_android.data.request.login.Login;
import com.narsha.wave_android.data.request.signup.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("login")
    Call<Login> user(@Body Login login);

    @POST("signUp")
    Call<SignUp> user(@Body SignUp signUp);
}
