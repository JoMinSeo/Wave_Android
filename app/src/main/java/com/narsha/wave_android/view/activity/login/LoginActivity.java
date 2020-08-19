package com.narsha.wave_android.view.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.viewmodel.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String KEY_UsER = "user_info";
    EditText idEt, passEt;
    Button loginButton;
    MainViewModel mainViewModel;
    Call<Result> request;
    String getEdit1;
    String getEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEt = findViewById(R.id.et_id);
        passEt = findViewById(R.id.et_pass);
        loginButton = findViewById(R.id.btn_login);

        setListener();
    }

    public void setListener(){
        loginButton.setOnClickListener(v -> {
            getEdit1 = idEt.getText().toString();
            getEdit2 = passEt.getText().toString();
            Log.i("test", getEdit1 + " " + getEdit2);

            if(getEdit1.isEmpty()){
                Toast.makeText(getApplicationContext(), "아이디를 입력하여 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getEdit2.isEmpty()){
                Toast.makeText(getApplicationContext(), "비밀번호를 입력하여 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(getEdit1, getEdit2);
            request = Server.getInstance().getApi().login(user);
            request.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Log.i("test", response.code()+"");
                    if(response.code()==200){
                        Result result = response.body();
                        Log.i("test", result.getResult());
                        // shared preference에 필요한 정보 저장하고
                        // finish...
                        SharedPreferences sharedPreferences = getSharedPreferences(KEY_UsER, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("userId", getEdit1);
                        editor.putString("userPassword",getEdit2);

                        editor.apply();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(request!=null) request.cancel();
    }
}