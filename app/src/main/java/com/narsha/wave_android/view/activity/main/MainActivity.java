package com.narsha.wave_android.view.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.view.activity.login.LoginActivity;
import com.narsha.wave_android.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_USER = "user_info";
    String userId;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_USER, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        if(userId == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }else{
            User user = new User();
            user.setUserId(userId);
            mainViewModel.user.setValue(user);
        }
    }
}