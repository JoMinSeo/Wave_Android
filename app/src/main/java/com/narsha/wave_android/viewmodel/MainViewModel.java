package com.narsha.wave_android.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.narsha.wave_android.data.User;
import com.narsha.wave_android.data.response.music.PlayList;

import java.util.List;

public class MainViewModel extends ViewModel {
    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<PlayList> playList = new MutableLiveData<>();
}
