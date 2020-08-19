package com.narsha.wave_android.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.narsha.wave_android.data.User;

public class MainViewModel extends ViewModel {
    public MutableLiveData<User> user = new MutableLiveData<>();

}
