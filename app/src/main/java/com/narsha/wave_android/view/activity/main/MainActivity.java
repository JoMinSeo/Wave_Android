package com.narsha.wave_android.view.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.narsha.wave_android.R;
import com.narsha.wave_android.view.activity.login.LoginActivity;
import com.narsha.wave_android.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    Boolean isSignin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if(!isSignin){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}