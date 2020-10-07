package com.narsha.wave_android.view.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.Result;
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.activity.main.MainActivity;
import com.narsha.wave_android.view.activity.signup.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String KEY_USER = "user_info";

    EditText idEt, passEt;
    TextView noId;
    Button loginButton;

    Call<Result> request;
    String idEdit;
    String pwEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindId();
        setListener();
    }

    public void bindId(){
        idEt = findViewById(R.id.et_id);
        passEt = findViewById(R.id.et_pass);
        loginButton = findViewById(R.id.btn_login);
        noId = findViewById(R.id.noId_Tv);
    }

    public void setListener() {
        noId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(v -> {
            idEdit = idEt.getText().toString();
            pwEdit = passEt.getText().toString();
            Log.i("test", idEdit + " " + pwEdit);

            if (idEdit.isEmpty() || pwEdit.isEmpty()) {
                Toast.makeText(getApplicationContext(), "제대로 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                login();
            }


        });
    }

    public void login(){
        User user = new User(idEdit, pwEdit);

        request = Server.getInstance().getApi().login(user);
        request.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.i("test", response.code() + "");
                if (response.code() == 200) {
                    Result result = response.body();
                    if (result.getResult().equals("ok")) {
                        Log.i("test", result.getResult());

                        SharedPreferences sharedPreferences = getSharedPreferences(KEY_USER, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("userId", idEdit);
                        editor.putString("userPassword", pwEdit);

                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (request != null) request.cancel();
    }
}