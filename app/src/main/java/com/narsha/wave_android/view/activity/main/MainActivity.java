package com.narsha.wave_android.view.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.fragment_host);
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_playlist, R.id.navigation_search,R.id.navigation_profile)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(navView, navController);
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